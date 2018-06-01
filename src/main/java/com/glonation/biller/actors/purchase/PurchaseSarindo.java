package com.glonation.biller.actors.purchase;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.camel.Camel;
import akka.camel.CamelExtension;
import akka.camel.CamelMessage;

public class PurchaseSarindo extends UntypedActor {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseSarindo.class);
	private String workerId = getSelf().path().name();
	private String TAG = "[" + this.getClass().getSimpleName() + "-" + workerId + "] ";
	private Map<String, Map<String, Map<String, Object>>> abc;
	private ActorSystem system;
	private AdapterSettings as;
	private ObjectMapper m= new ObjectMapper();
	private BillingAPIHitter billingAPIHitter= new BillingAPIHitter();
	private MappingRequest mappingRequest =new MappingRequest();
	private Hitter hitter;
	private PurchaseMappingResponse mappingResponse = new PurchaseMappingResponse();
	

	public PurchaseSarindo(Map<String, Map<String, Map<String, Object>>> abc, ActorSystem system, AdapterSettings as) {
		this.as =as;
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
				hitter = new Hitter();
				coreParamMap = m.readValue(body, HashMap.class);
				String billerId = coreParamMap.get("SWITCHING_BILLER_ID").toString();
				complementMap = (HashMap<String, Object>) abc.get(billerId).get("purchase");
				String reqTrxId = coreParamMap.get("TRX_ID").toString();
				IDGenerator idGenerator = new IDGenerator();
				String billerTrxId = idGenerator.generateIdAlfNum(billerId, reqTrxId, 20);
				if (billerTrxId.equals("") || billerTrxId == null) {
				} else {
					idGenerator.saveBillerTrxId(reqTrxId, billerTrxId, as);
					coreParamMap.put("TRX_ID", billerTrxId);
				}
				logger.debug(TAG + "BillerId : " + billerId + ", CORE TRX_ID : " + reqTrxId + ", Biller TRX_ID : "
						+ billerTrxId);

				// mapping request template
				String stringRequestBiller = mappingRequest.mappingGet(body,
						complementMap.get("RequestTemplate").toString(), complementMap.get("RequestParam").toString(),complementMap);
				logger.debug(TAG + "Request to Biller before checksum: " + stringRequestBiller);
				//set special case parameters ex: Checksum, trxid
				if (complementMap.get("ChecksumComponent").toString().length() > 0) {
					stringRequestBiller =setChecksum(stringRequestBiller, complementMap,billerTrxId);
				}
				//set checksum end
				logger.debug(TAG + "Request to Biller after checksum: " + stringRequestBiller);
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
					paramsBillingAPI.put(RequestParameter.PRODUCT_CODE, coreParamMap.get(RequestParameter.PRODUCT_CODE));
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
				
				//--------------------------------------------
				// set bill info end
				// mapping response template end
				if(mapResponseCoreParam.get("REFERENCE_NUMBER")!=null){
					if (mapResponseCoreParam.get("REFERENCE_NUMBER").equals(billerTrxId)) {
						mapResponseCoreParam.put("REFERENCE_NUMBER", reqTrxId);
					}	
				}else {
					mapResponseCoreParam.put("REFERENCE_NUMBER", reqTrxId);
				}
				stringMappingResponse=m.writeValueAsString(mapResponseToCore);
				logger.debug(TAG + "Response to Core : " + stringMappingResponse);
				// return to core

				Map<String, Object> headers = new HashMap<String, Object>();
				headers.put(Exchange.CONTENT_TYPE, "application/json");
				CamelMessage ress = new CamelMessage(stringMappingResponse, headers);
				getSender().tell(ress, getSelf());
				// return to core end

			} catch (Exception e1) {
				logger.error(TAG + "error : " + e1.getMessage() );
			}

		} else {
			logger.debug(TAG + "unknown message : " + message);
		}
	}
	
	String setChecksum(String stringRequestBiller,HashMap<String, Object> complementMap,String billerTrxId){
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
		if(mapRequest.containsKey("partner_trxid")){
			mapRequest.put("partner_trxid", billerTrxId);
		}
		String temp1=complementMap.get("SharedKey").toString()+mapRequest.get("partner_trxid");
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
