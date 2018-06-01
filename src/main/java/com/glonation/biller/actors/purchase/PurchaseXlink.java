package com.glonation.biller.actors.purchase;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.glonation.biller.AdapterSettings;
import com.glonation.biller.utils.ChecksumGen;
import com.glonation.biller.utils.Hitter;
import com.glonation.biller.utils.IDGenerator;
import com.glonation.biller.utils.MappingRequest;
import com.glonation.biller.utils.PurchaseMappingResponse;
import com.glonation.billing.hitter.BillingAPIHitter;

import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.camel.Camel;
import akka.camel.CamelExtension;
import akka.camel.CamelMessage;

public class PurchaseXlink extends UntypedActor {
	private static final Logger logger = LoggerFactory.getLogger(PurchaseSarindo.class);
	private String workerId = getSelf().path().name();
	private String TAG = "[" + this.getClass().getSimpleName() + "-" + workerId + "] ";
	private Map<String, Map<String, Map<String, Object>>> abc;
	private ActorSystem system;
	private ObjectMapper m =new ObjectMapper();;
	private String exMessage;
	private BillingAPIHitter billingAPIHitter= new BillingAPIHitter();
	private MappingRequest mappingRequest;
	private PurchaseMappingResponse mappingResponse;
	private Hitter hitter;
	private AdapterSettings as;

	public PurchaseXlink(Map<String, Map<String, Map<String, Object>>> abc, ActorSystem system, AdapterSettings as) {
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
				mappingResponse = new PurchaseMappingResponse();
				hitter = new Hitter();
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
				String stringRequestBiller = mappingRequest.mappingXmlRPC(body, complementMap,coreParamMap);
				logger.debug(TAG + "Request to Biller before checksum: " + stringRequestBiller);
				// set special case parameters ex: Checksum, trxid
				if (complementMap.get("ChecksumComponent").toString().length() > 0) {
					if (complementMap.get("ChecksumComponent").toString().equals("XLINKCHECKSUM")) {
						String modififyChecksum = complementMap.get("AdditionalData3").toString();
						LinkedHashMap<String, Object> checksumComponent = m.readValue(modififyChecksum,
								LinkedHashMap.class);
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
						DocumentBuilder builder = factory.newDocumentBuilder();
						// set string response XML to dom
						InputSource isResquest = new InputSource(new StringReader(stringRequestBiller));
						Document docRequest = builder.parse(isResquest);
						docRequest.getDocumentElement().normalize();
						String checksum = getChecksum(docRequest.getChildNodes(), checksumComponent);
						logger.debug(TAG + "checksum : " + checksum);
						stringRequestBiller = mappingRequest.mappingXmlRPCWithChecksum(body, stringRequestBiller,
								complementMap.get("RequestParam").toString(), checksum);
					}
				}

				// set checksum end
				logger.debug(TAG + "Request to Biller after checksum: " + stringRequestBiller);
				// mapping request template end

				// hit to biller
				String stringResponseBiller = hitter.hitBillerPOST(complementMap.get("Url").toString(),
						stringRequestBiller,"apllication/xml");
				logger.debug(TAG + "Response from Biller : " + stringResponseBiller);
				// hit to biller end

				// mapping response template
				String stringMappingResponse = mappingResponse.xmlRPCToJson(stringResponseBiller,
						complementMap.get("ResponseParam").toString());
				// set bill_info
				// set bill info end
				// mapping response template end
				HashMap<String, Object> mapResponseCoreParam = m.readValue(stringMappingResponse, HashMap.class);
				if (mapResponseCoreParam.get("REFERENCE_NUMBER") != null) {
					if (mapResponseCoreParam.get("REFERENCE_NUMBER").equals(billerTrxId)) {
						mapResponseCoreParam.put("REFERENCE_NUMBER", reqTrxId);
					}
				} else {
					mapResponseCoreParam.put("REFERENCE_NUMBER", reqTrxId);
				}
				stringMappingResponse = m.writeValueAsString(mapResponseCoreParam);
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

	@SuppressWarnings("unchecked")
	public String getChecksum(NodeList nlTemplate, LinkedHashMap<String, Object> component) {
		String realComponentChecksum = "";
		ChecksumGen checksumGen = new ChecksumGen();
		ObjectMapper mapper = new ObjectMapper();
		LinkedHashMap<String, Object> sll = new LinkedHashMap<>();
		String componentChecksum = null;
		for (Map.Entry<String, Object> subEntry : component.entrySet()) {
			try {
				String detail = mapper.writeValueAsString(subEntry.getValue());
				sll = mapper.readValue(detail, LinkedHashMap.class);
				for (Map.Entry<String, Object> detailCom : sll.entrySet()) {
					String listComponent[] = null;
					if (detailCom.getKey().equalsIgnoreCase("component")) {
						listComponent = detailCom.getValue().toString().split("\\|");
						String cInput = "";
						for (String string : listComponent) {
							String componentValue = getChecksumComponent(nlTemplate, string);
							if (!componentValue.equals(string)) {
								string = componentValue;
							}
							cInput += string;
						}
						detailCom.setValue(cInput);
					}
				}
				componentChecksum = checksumGen.getChecksum(sll.get("component").toString(),
						sll.get("type").toString());
				realComponentChecksum += componentChecksum;
			} catch (IOException e) {
				logger.debug("error : " + e.getStackTrace());
			}

		}
		return checksumGen.getChecksum(realComponentChecksum, "SHA1");
	}

	public String getChecksumComponent(NodeList nlTemplate, String component) {
		String value = component;
		for (int count = 0; count < nlTemplate.getLength(); count++) {
			Node template = nlTemplate.item(count);
			if (template.getNodeType() == Node.ELEMENT_NODE) {
				if (template.getTextContent().equals(component)) {
					value = template.getNextSibling().getTextContent();
				} else {
					value = getChecksumComponent(template.getChildNodes(), component);
				}
			}
			if (!value.equals(component)) {
				break;
			}
		}
		return value;
	}

	public void setChecksum(NodeList nlTemplate, String component) {

	}

}
