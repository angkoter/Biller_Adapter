package com.glonation.biller.actors.advice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glonation.biller.AdapterSettings;
import com.glonation.biller.actors.purchase.PurchaseSarindo;
import com.glonation.biller.messages.GeneralResponse;
import com.glonation.biller.utils.AdviceMappingResponse;
import com.glonation.biller.utils.ChecksumGen;
import com.glonation.biller.utils.Hitter;
import com.glonation.biller.utils.IDGenerator;
import com.glonation.biller.utils.MappingRequest;
import com.glonation.biller.utils.PurchaseMappingResponse;
import com.glonation.biller.utils.RequestParameter;
import com.glonation.billing.hitter.BillingAPIHitter;

import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.camel.Camel;
import akka.camel.CamelExtension;
import akka.camel.CamelMessage;

public class AdviceSarindo extends UntypedActor {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseSarindo.class);
	private String workerId = getSelf().path().name();
	private String TAG = "[" + this.getClass().getSimpleName() + "-" + workerId + "] ";
	private Map<String, Map<String, Map<String, Object>>> abc;
	private ActorSystem system;
	private ObjectMapper m=new ObjectMapper();
	private String exMessage;
	private BillingAPIHitter billingAPIHitter=new BillingAPIHitter();;
	private MappingRequest mappingRequest;
	private AdviceMappingResponse mappingResponse;
	private Hitter hitter;
	private AdapterSettings as;
	
	public AdviceSarindo(Map<String, Map<String, Map<String, Object>>> abc, ActorSystem system, AdapterSettings as) {
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
			HashMap<String, Object> coreParamMap = null;
			HashMap<String, Object> complementMap = null;
			String reqTrxid = "";
			try {
				mappingRequest = new MappingRequest();
				mappingResponse = new AdviceMappingResponse();
				hitter = new Hitter();
				coreParamMap = m.readValue(body, HashMap.class);
				String billerId = coreParamMap.get("SWITCHING_BILLER_ID").toString();
				complementMap = (HashMap<String, Object>) abc.get(billerId).get("advice");
				String reqTrxId = coreParamMap.get("TRX_ID").toString();
				IDGenerator idGenerator = new IDGenerator();
				String billerTrxId = idGenerator.getBillerTrxId(reqTrxId, as);
				if (billerTrxId.equals("") || billerTrxId == null) {
				} else {
					coreParamMap.put("TRX_ID", billerTrxId);
				}

				logger.debug(TAG + "BillerId : " + billerId + ", CORE TRX_ID : " + reqTrxId + ", Biller TRX_ID : "
						+ billerTrxId);

				// mapping request template
				String stringRequestBiller = mappingRequest.mappingGet(body,
						complementMap.get("RequestTemplate").toString(), complementMap.get("RequestParam").toString(),complementMap);
				logger.debug(TAG + "Request to Biller 1: " + stringRequestBiller);
				//set checksum
				if (complementMap.get("ChecksumComponent").toString().length() > 0) {
					stringRequestBiller =setChecksum(stringRequestBiller, complementMap);
				}
				//set checksum end
				logger.debug(TAG + "Request to Biller 2: " + stringRequestBiller);
				// mapping request template end

				// hit to biller
				String stringResponseBiller = hitter.hitBillerGET(complementMap.get("Url").toString(),
						stringRequestBiller);
				logger.debug(TAG + "Response from Biller : " + stringResponseBiller);
				// hit to biller end

				// mapping response template
				String stringMappingResponse = mappingResponse.xmlToJson(stringResponseBiller,
						complementMap.get("ResponseParam").toString());
				HashMap<String, Object> mapResponseCoreParam = m.readValue(stringMappingResponse, HashMap.class);
				// set bill_info
				
				HashMap<String, Object> mapResponseToCore = m.readValue(stringMappingResponse, HashMap.class);
				try {
					HashMap<String, Object> paramsBillingAPI = new HashMap<>();
					paramsBillingAPI.put(RequestParameter.ADMIN_FEE, coreParamMap.get(RequestParameter.ADMIN_FEE));
					paramsBillingAPI.put(RequestParameter.BILLER_ID, billerId);
					paramsBillingAPI.put(RequestParameter.REQUEST_TYPE, "2");
					paramsBillingAPI.put(RequestParameter.PRODUCT_CODE, coreParamMap.get("VENDOR_BILLER_ID"));
					paramsBillingAPI.put(RequestParameter.PURCHASE_STATUS, mapResponseToCore.get(RequestParameter.PURCHASE_STATUS).toString());
					paramsBillingAPI.put(RequestParameter.TOTAL_TRX_AMOUNT, coreParamMap.get("TOTAL_AMOUNT"));
					paramsBillingAPI.put(RequestParameter.TRX_DETAIL_RESULT, stringResponseBiller);
					HashMap<String, Object> billInfo = null;
						billInfo = billingAPIHitter.getBillInfo(paramsBillingAPI );
						mapResponseToCore.put(GeneralResponse.Purchase.BILL_INFO, m.writeValueAsString(billInfo));
					} catch (Exception e) {

						logger.debug(TAG+"Sometthing bill info");
						logger.error(TAG+e.getMessage());
						mapResponseToCore.put(GeneralResponse.Purchase.BILL_INFO, "");
					}
				
				// set bill info end
				// mapping response template end
				
				
				if(mapResponseCoreParam.get("REFERENCE_NUMBER")!=null){
					if (mapResponseCoreParam.get("REFERENCE_NUMBER").equals(billerTrxId)) {
						mapResponseCoreParam.put("REFERENCE_NUMBER", reqTrxId);
					}	
				}else {
					mapResponseCoreParam.put("REFERENCE_NUMBER", reqTrxId);
				}
				stringMappingResponse=m.writeValueAsString(mapResponseCoreParam);
				logger.debug(TAG + "Response to Core : " + stringMappingResponse);
				
				
				
				logger.debug(TAG + "Response to Core : " + stringMappingResponse);
				// return to core

				Map<String, Object> headers = new HashMap<String, Object>();
				headers.put(Exchange.CONTENT_TYPE, "application/json");
				CamelMessage ress = new CamelMessage(stringMappingResponse, headers);
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
	
	String setChecksum(String stringRequestBiller,HashMap<String, Object> complementMap){
		ChecksumGen checksumGen = new ChecksumGen();
		
		HashMap<String, String> mapRequest = new HashMap<>();
		String[] splitTemplate = stringRequestBiller.split("&");
		
		for (String string : splitTemplate) {
			String tempTemplate[] = string.split("=");
			if(tempTemplate.length>1){
				mapRequest.put(tempTemplate[0], tempTemplate[1]);	
			}else{
				mapRequest.put(tempTemplate[0], "");
			}
		}
		
		String temp1=complementMap.get("SharedKey").toString()+mapRequest.get("server_trxid");
		logger.debug(TAG + "first checksum component : " +temp1+" result MD5 : "+ checksumGen.getChecksum(temp1, "MD5"));
		String temp2=mapRequest.get("channelid")+mapRequest.get("storeid")+mapRequest.get("posid")+mapRequest.get("trxtime");
		logger.debug(TAG + "second checksum component : " +temp2+" result MD5 : " +checksumGen.getChecksum(temp2, "MD5"));
		String checksum = checksumGen.getChecksum(checksumGen.getChecksum(temp1, "MD5")+checksumGen.getChecksum(temp2, "MD5"), "MD5");
		logger.debug(TAG + "final checksum component : " +checksumGen.getChecksum(temp1, "MD5")+checksumGen.getChecksum(temp2, "MD5")+" result MD5 : " +checksum);
		
		mapRequest.put("password", checksum);
		
		String requestBiller="";
		for (HashMap.Entry<String, String> subEntry : mapRequest.entrySet()) {
			try {
				requestBiller = requestBiller+subEntry.getKey() + "=" + URLEncoder.encode(subEntry.getValue(), "UTF-8") + "&";
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return requestBiller.substring(0, requestBiller.length() - 1);

			
	}

}
