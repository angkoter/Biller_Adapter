//package com.glonation.biller.actors.purchase;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.security.KeyManagementException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//
//import org.apache.camel.CamelContext;
//import org.apache.camel.Exchange;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.glonation.biller.AdapterSettings;
//import com.glonation.biller.utils.Hitter;
//import com.glonation.biller.utils.MappingRequest;
//import com.glonation.biller.utils.PurchaseMappingResponse;
//import com.glonation.billing.hitter.BillingAPIHitter;
//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.Response;
//
//import akka.actor.ActorSystem;
//import akka.actor.UntypedActor;
//import akka.camel.Camel;
//import akka.camel.CamelExtension;
//import akka.camel.CamelMessage;
//
//public class PurchaseOrchestra extends UntypedActor {
//
//	private static final Logger logger = LoggerFactory.getLogger(PurchaseOrchestra.class);
//	private String workerId = getSelf().path().name();
//	private String TAG = "[" + this.getClass().getSimpleName() + "-" + workerId + "] ";
//	private Map<String, Map<String, Map<String, Object>>> abc;
//	private ActorSystem system;
//	private AdapterSettings as;
//	private ObjectMapper m = new ObjectMapper();
//	private BillingAPIHitter billingAPIHitter = new BillingAPIHitter();
//	private MappingRequest mappingRequest = new MappingRequest();
//	private PurchaseMappingResponse mappingResponse = new PurchaseMappingResponse();
//	private Hitter hitter = new Hitter();
//
//	public PurchaseOrchestra(Map<String, Map<String, Map<String, Object>>> abc, ActorSystem system,
//			AdapterSettings as) {
//		this.as = as;
//		this.abc = abc;
//		this.system = system;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public void onReceive(Object message) {
//		if (message instanceof CamelMessage) {
//			CamelMessage camelMessage = (CamelMessage) message;
//			Camel camel = CamelExtension.get(system);
//			CamelContext camelContext = camel.context();
//			String body = camelMessage.getBodyAs(String.class, camelContext);
//			// set HashMap for current request
//			HashMap<String, Object> ircMap = null;
//			HashMap<String, Object> complementMap = null;
//			String reqTrxId = "";
//
//			try {
//				ircMap = m.readValue(body, HashMap.class);
//				String billerId = ircMap.get("JATIS_BILLER_ID").toString();
//				complementMap = (HashMap<String, Object>) abc.get(billerId).get("purchase");
//				reqTrxId = ircMap.get("TRX_ID").toString();
//				logger.debug(TAG + "BillerId : " + billerId + ", TRX_ID : " + reqTrxId);
//
//				logger.debug("|Performance|BillerAdapter|PurchaseHitterGET|2|Purchase|ORDER_ID:" + "" + "|TRX_ID:"
//						+ reqTrxId);
//				String requestBiller = mappingRequest(complementMap, ircMap);
//
//				logger.debug(TAG + "Request to biller : " + requestBiller);
////				String responseFromBiller = hitBiller(complementMap.get("Url").toString(), requestBiller);
//
//				logger.debug("|Performance|BillerAdapter|PurchaseHitterGET|3|Purchase|ORDER_ID:" + "" + "|TRX_ID:"
//						+ reqTrxId);
//				logger.debug(TAG + "Response from biller : " + responseFromBiller);
////				String responseToCore = mappingResponse(responseFromBiller,
//						complementMap.get("ResponseTemplate").toString());
//				logger.debug("|Performance|BillerAdapter|PurchaseHitterGET|4|Purchase|ORDER_ID:" + "" + "|TRX_ID:"
//						+ reqTrxId);
//
//				// ------------- bill info-------------------------------
//				HashMap<String, Object> mapResponseToCore = m.readValue(responseToCore, HashMap.class);
//
//				// --------------------------------------------
//				// logger.debug(TAG + "mapResponseToCore : " +
//				// mapResponseToCore.toString());
//				logger.debug(TAG + "Response To Core : " + m.writeValueAsString(mapResponseToCore).toString());
//
//				Map<String, Object> headers = new HashMap<String, Object>();
//				headers.put(Exchange.CONTENT_TYPE, "application/json");
//				CamelMessage ress = new CamelMessage(m.writeValueAsString(mapResponseToCore), headers);
//				getSender().tell(ress, getSelf());
//
//			} catch (Exception e1) {
//				logger.error(TAG + "error : " + e1.getMessage());
//			}
//
//		} else {
//			logger.debug(TAG + "unknown message : " + message);
//		}
//	}
//
//}
