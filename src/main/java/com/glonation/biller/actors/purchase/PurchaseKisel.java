package com.glonation.biller.actors.purchase;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.glonation.biller.AdapterSettings;
import com.glonation.biller.messages.GeneralResponse;
import com.glonation.biller.utils.ChecksumGen;
import com.glonation.biller.utils.DateGenerator;
import com.glonation.biller.utils.Hitter;
import com.glonation.biller.utils.IDGenerator;
import com.glonation.biller.utils.MappingRequest;
import com.glonation.biller.utils.PurchaseMappingResponse;
import com.glonation.biller.utils.RequestParameter;
import com.glonation.billing.hitter.BillingAPIHitter;
import com.glonation.mapper.Mapper;

import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.camel.Camel;
import akka.camel.CamelExtension;
import akka.camel.CamelMessage;

public class PurchaseKisel extends UntypedActor {
	private static final Logger logger = LoggerFactory.getLogger(PurchaseKisel.class);
	private String workerId = getSelf().path().name();
	private String TAG = "[" + this.getClass().getSimpleName() + "-" + workerId + "] ";
	private Map<String, Map<String, Map<String, Object>>> abc;
	private ActorSystem system;
	private ObjectMapper m = new ObjectMapper();;
	private String exMessage;
	private BillingAPIHitter billingAPIHitter = new BillingAPIHitter();
	private Hitter hitter = new Hitter();;
	private AdapterSettings as;

	public PurchaseKisel(Map<String, Map<String, Map<String, Object>>> abc, ActorSystem system, AdapterSettings as) {
		this.as = as;
		this.abc = abc;
		this.system = system;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onReceive(Object message) {
		if (message instanceof CamelMessage) {
			CamelMessage camelMessage = (CamelMessage) message;
			Camel camel = CamelExtension.get(system);
			CamelContext camelContext = camel.context();
			String body = camelMessage.getBodyAs(String.class, camelContext);
			// set HashMap for current request
			Mapper mapper = new Mapper();
			HashMap<String, Object> coreParamMap = null;
			HashMap<String, Object> complementMap = null;
			try {
				coreParamMap = m.readValue(body, HashMap.class);
				String billerId = coreParamMap.get("SWITCHING_BILLER_ID").toString();
				complementMap = (HashMap<String, Object>) abc.get(billerId).get("purchase");
				String reqTrxId = coreParamMap.get("TRX_ID").toString();
				IDGenerator idGenerator = new IDGenerator();
				String billerTrxId = idGenerator.generateId(billerId, reqTrxId, 12);
				if (billerTrxId.equals("") || billerTrxId == null) {
				} else {
					idGenerator.saveBillerTrxId(reqTrxId, billerTrxId, as);
					coreParamMap.put("TRX_ID", billerTrxId);
				}
				logger.debug(TAG + "BillerId : " + billerId + ", CORE TRX_ID : " + reqTrxId + ", Biller TRX_ID : "
						+ billerTrxId);

				// mapping request template
				LinkedHashMap<String, Object> mapRequestTemplate = mapper
						.XMLToMap(complementMap.get("RequestTemplate").toString(), "kisel");
				String splitRequestParam[] = complementMap.get("RequestParam").toString().split("\\|");
				for (String string : splitRequestParam) {
					String tempParam[] = string.split("=");
					if(tempParam[1].equalsIgnoreCase("TRX_DATE")){
						HashMap<String, Object> addData1Map = m.readValue(complementMap.get("AdditionalData1").toString(), HashMap.class);
						HashMap<String, Object> toDate = (HashMap<String, Object>) addData1Map.get("ToDate");
						String format = toDate.get("format").toString();
						String trxDate = DateGenerator.getDate(coreParamMap.get("TRX_DATE").toString(),
								format);
						mapRequestTemplate.put(tempParam[0], trxDate);
					}else if (coreParamMap.containsKey(tempParam[1])) {
						if(coreParamMap.containsKey(tempParam[1])){
							mapRequestTemplate.put(tempParam[0], coreParamMap.get(tempParam[1]).toString());	
						}
					}
				}
				
				
				String stringRequestBiller = mapper.MapToStringXML(mapRequestTemplate, "kisel");
				logger.debug(TAG + "Request to Biller :" + stringRequestBiller);

				// hit to biller
				String stringResponseBiller = hitter.hitBillerPOST(complementMap.get("Url").toString(),
						stringRequestBiller, "apllication/xml");
				logger.debug(TAG + "Response from Biller : " + stringResponseBiller);
				// hit to biller end

				// // mapping response template
				LinkedHashMap<String, Object> mapResponseFromBiller = mapper.XMLToMap(stringResponseBiller, "kisel");
				LinkedHashMap<String, String> responseToCore = GeneralResponse.Purchase.getInitialResponse();
				if(mapResponseFromBiller.containsKey("error")){
					String errorCode = mapResponseFromBiller.get("error").toString();
					String tempErrorCode[] = errorCode.split(":");
					responseToCore.put("PURCHASE_STATUS", "IP"+tempErrorCode[1]);
				}else{
					String splitResponseParam[] = complementMap.get("ResponseParam").toString().split("\\|");
					for (String string : splitResponseParam) {
						String tempParam[] = string.split("=");
						if (responseToCore.containsKey(tempParam[1])) {
							if(mapResponseFromBiller.containsKey(tempParam[0])){
								responseToCore.put(tempParam[1], mapResponseFromBiller.get(tempParam[0]).toString());	
							}
						}
					}
				}
				try {
					HashMap<String, Object> paramsBillingAPI = new HashMap<>();
					paramsBillingAPI.put(RequestParameter.ADMIN_FEE, coreParamMap.get(RequestParameter.ADMIN_FEE));
					paramsBillingAPI.put(RequestParameter.BILLER_ID, billerId);
					paramsBillingAPI.put(RequestParameter.REQUEST_TYPE, "2");
					paramsBillingAPI.put(RequestParameter.PRODUCT_CODE,
							coreParamMap.get(RequestParameter.PRODUCT_CODE));
					paramsBillingAPI.put(RequestParameter.PURCHASE_STATUS,
							mapResponseFromBiller.get(RequestParameter.PURCHASE_STATUS).toString());
					paramsBillingAPI.put(RequestParameter.TOTAL_TRX_AMOUNT, coreParamMap.get("TOTAL_TRX_AMOUNT"));
					paramsBillingAPI.put(RequestParameter.TRX_DETAIL_RESULT, stringResponseBiller);
					HashMap<String, Object> billInfo = null;
					billInfo = billingAPIHitter.getBillInfo(paramsBillingAPI);
					mapResponseFromBiller.put(GeneralResponse.Purchase.BILL_INFO, m.writeValueAsString(billInfo));
				} catch (Exception e) {
					logger.debug(TAG + "Sometthing bill info");
					logger.error(TAG + e.getMessage());
					mapResponseFromBiller.put(GeneralResponse.Purchase.BILL_INFO, "");
				}

				// return to core
				LinkedHashMap<String, Object> returnMap = new LinkedHashMap<>();
				returnMap.putAll(responseToCore);
				logger.debug(TAG + "Response to Core : " + mapper.MapToStringJson(returnMap));
				Map<String, Object> headers = new HashMap<String, Object>();
				headers.put(Exchange.CONTENT_TYPE, "application/json");
				CamelMessage ress = new CamelMessage(mapper.MapToStringJson(returnMap), headers);
				getSender().tell(ress, getSelf());
				// return to core end

			} catch (Exception e1) {
				exMessage = TAG + "error : " + e1.getMessage() + ", Trace : " + e1.getStackTrace()[0];
				if (exMessage.length() > 499) {
					exMessage = exMessage.substring(0, 499);
				}
				logger.error(exMessage);
			}

		} else {
			logger.debug(TAG + "unknown message : " + message);
		}
	}
}
