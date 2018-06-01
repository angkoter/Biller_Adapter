package com.glonation.biller.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.glonation.biller.actors.purchase.PurchaseSarindo;

public class MappingRequest {
	private ObjectMapper m = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(PurchaseSarindo.class);
	private String TAG = "[" + this.getClass().getSimpleName() + "] ";

	public HashMap<String, String> getRequestParam(String requestParam) {
		HashMap<String, String> mapRequestParam = new HashMap<>();
		String splitRequestParam[] = requestParam.split("\\|");
		for (String string : splitRequestParam) {
			String tempParam[] = string.split("=");
			mapRequestParam.put(tempParam[0], tempParam[1]);
		}
		return mapRequestParam;
	}

	private Document createDOMXml(String xml) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		Document doc = builder.parse(is);
		doc.getDocumentElement().normalize();
		return doc;
	}

	@SuppressWarnings("unchecked")
	public String mappingGet(String coreParam, String requestTemplate, String requestParam,
			HashMap<String, Object> complementMap) {
		/*
		 * for Get Request ex : {"name":"angkot","add":"jalan raya",etc} =>
		 * name=angkot&add=jalan%20draya&ect
		 */
		try {
			HashMap<String, String> mapRequestParam = getRequestParam(requestParam);
			HashMap<String, String> mapRequestTemplate = new HashMap<>();
			HashMap<String, Object> mapCoreParam = m.readValue(coreParam, HashMap.class);
			String requestBiller = "";

			// set requestTemplate to map
			String[] splitTemplate = requestTemplate.split("&");
			for (String string : splitTemplate) {
				String tempTemplate[] = string.split("=");
				if(tempTemplate.length>1){
					mapRequestTemplate.put(tempTemplate[0], tempTemplate[1]);	
				}else{
					mapRequestTemplate.put(tempTemplate[0], "");
				}
				
			}
			// mapping requestParam to requesttemplate
			for (HashMap.Entry<String, String> subEntry : mapRequestParam.entrySet()) {
				if (mapRequestTemplate.containsKey(subEntry.getKey())) {
					if (subEntry.getValue().equals("TRX_DATE")) {
						HashMap<String, Object> addData1Map = null;
						addData1Map = m.readValue(complementMap.get("AdditionalData1").toString(), HashMap.class);
						HashMap<String, Object> toDate = (HashMap<String, Object>) addData1Map.get("ToDate");
						String format = toDate.get("format").toString();
						String trxDate = DateGenerator.getDate(mapCoreParam.get(subEntry.getValue()).toString(),
								format);
						mapRequestTemplate.put(subEntry.getKey(), trxDate);
					} else {
						mapRequestTemplate.put(subEntry.getKey(), mapCoreParam.get(subEntry.getValue()).toString());
					}

				}
			}
			// convert to final request
			for (HashMap.Entry<String, String> subEntry : mapRequestTemplate.entrySet()) {
				requestBiller = requestBiller + subEntry.getKey() + "="
						+ URLEncoder.encode(subEntry.getValue(), "UTF-8") + "&";
			}

			return requestBiller.substring(0, requestBiller.length() - 1);
		} catch (IOException e) {
			logger.error(TAG + "Mapping Request Get. Info : " + e.getMessage());
			return "";
		}

	}

	@SuppressWarnings("unchecked")
	public String mappingXmlRPC(String coreParam, HashMap<String, Object> complementMap,HashMap<String, Object> coreParamMap) {
		String requestBiller = "";
		try {
			HashMap<String, String> mapRequestParam = getRequestParam(complementMap.get("RequestParam").toString());
			Document requestDom = createDOMXml(complementMap.get("RequestTemplate").toString());
			NodeList nlTemplate = requestDom.getDocumentElement().getChildNodes();
			getMappingItem(nlTemplate, coreParamMap, mapRequestParam, complementMap);
			requestBiller = domTostring(requestDom);
		} catch (IOException e) {
			logger.error(TAG + "Mapping Request XMLRPC. Info : " + e.getMessage());
		} catch (SAXException e) {
			logger.error(TAG + "Mapping Request XMLRPC. Info : " + e.getMessage());
		} catch (ParserConfigurationException e) {
			logger.error(TAG + "Mapping Request XMLRPC. Info : " + e.getMessage());
		}

		return requestBiller;
	}

	@SuppressWarnings("unchecked")
	public String mappingXmlRPCWithChecksum(String coreParam, String requestTemplate, String requestParam,
			String checksum) {
		String requestBiller = "";
		try {
			HashMap<String, String> mapRequestParam = getRequestParam(requestParam);
			HashMap<String, Object> mapCoreParam = m.readValue(coreParam, HashMap.class);

			Document requestDom = createDOMXml(requestTemplate);
			NodeList nlTemplate = requestDom.getDocumentElement().getChildNodes();
			getMappingItemWithChecksum(nlTemplate, mapRequestParam, checksum);
			requestBiller = domTostring(requestDom);
		} catch (IOException e) {
			logger.error(TAG + "Mapping Request XMLRPC. Info : " + e.getMessage());
		} catch (SAXException e) {
			logger.error(TAG + "Mapping Request XMLRPC. Info : " + e.getMessage());
		} catch (ParserConfigurationException e) {
			logger.error(TAG + "Mapping Request XMLRPC. Info : " + e.getMessage());
		}

		return requestBiller;
	}

	private void getMappingItem(NodeList nlTemplate, Map<String, Object> mapCoreParam,
			HashMap<String, String> mapRequestParam, HashMap<String, Object> complementMap)
			throws DOMException, UnsupportedEncodingException {
		for (int count = 0; count < nlTemplate.getLength(); count++) {
			Node template = nlTemplate.item(count);
			if (template.getNodeType() == Node.ELEMENT_NODE) {
				if (template.getChildNodes().item(0).getNodeType() == Node.TEXT_NODE
						&& mapRequestParam.keySet().contains(template.getChildNodes().item(0).getTextContent())) {
					Node nodeValue = template.getNextSibling();
					if (!mapRequestParam.get(template.getChildNodes().item(0).getTextContent()).equals("CHECKSUM")) {

						if (mapRequestParam.get(template.getChildNodes().item(0).getTextContent()).equals("TRX_DATE")) {
							try {
								logger.debug("fuckkkeeeeerrrrrrrrrrrrrrrr");
								HashMap<String, Object> addData1Map = m
										.readValue(complementMap.get("AdditionalData1").toString(), HashMap.class);
								HashMap<String, Object> toDate = (HashMap<String, Object>) addData1Map.get("ToDate");
								String format = toDate.get("format").toString();
								String trxDate = DateGenerator
										.getDate(mapCoreParam
												.get(mapRequestParam
														.get(template.getChildNodes().item(0).getTextContent()))
												.toString(), format);
								changeValue(nodeValue.getChildNodes(), trxDate);
							} catch (JsonParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							changeValue(nodeValue.getChildNodes(),
									mapCoreParam
											.get(mapRequestParam.get(template.getChildNodes().item(0).getTextContent()))
											.toString());
						}

					}

				} else {
					getMappingItem(template.getChildNodes(), mapCoreParam, mapRequestParam, complementMap);
				}
			}
		}
	}

	private void getMappingItemWithChecksum(NodeList nlTemplate, HashMap<String, String> mapRequestParam,
			String checksum) throws DOMException, UnsupportedEncodingException {
		for (int count = 0; count < nlTemplate.getLength(); count++) {
			Node template = nlTemplate.item(count);
			System.out.println(template.getTextContent());
			if (template.getNodeType() == Node.ELEMENT_NODE) {
				if (template.getChildNodes().item(0).getNodeType() == Node.TEXT_NODE
						&& mapRequestParam.keySet().contains(template.getChildNodes().item(0).getTextContent())) {
					Node nodeValue = template.getNextSibling();
					if (mapRequestParam.get(template.getChildNodes().item(0).getTextContent()).equals("CHECKSUM")) {
						changeValue(nodeValue.getChildNodes(), checksum);
					}

				} else {
					getMappingItemWithChecksum(template.getChildNodes(), mapRequestParam, checksum);
				}
			}
		}
	}

	public void changeValue(NodeList nlValue, String value) {
		// change value from same level
		for (int count = 0; count < nlValue.getLength(); count++) {
			Node template = nlValue.item(count);
			if (template.getNodeType() == Node.ELEMENT_NODE) {
				if (template.hasChildNodes()) {
					changeValue(template.getChildNodes(), value);
				}
			} else if (template.getNodeType() == Node.TEXT_NODE) {
				template.setTextContent(value);
			}
		}
	}

	public String domTostring(Document domXml) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			StringWriter writer = new StringWriter();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(domXml);
			StreamResult result4 = new StreamResult(writer);
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(source, result4);
			String stringXml = writer.toString();
			writer.close();
			return stringXml;
		} catch (TransformerException | IOException e) {
			logger.error(TAG + "Mapping Request XMLRPC parsing xml to string. Info : " + e.getMessage());
			return "";
		}

	}

	@SuppressWarnings("unchecked")
	public String mappingJson(String coreParam, HashMap<String, Object> complementMap){
		String requestBiller = "";
		try {
			HashMap<String, String> mapRequestParam = getRequestParam(complementMap.get("RequestParam").toString());
			HashMap<String, Object> mapCoreParam = m.readValue(coreParam, HashMap.class);
			HashMap<String, String> mapRequestTemplate = m.readValue(complementMap.get("RequestTemplate").toString(), LinkedHashMap.class);			
			for (Map.Entry<String, String> subEntry : mapRequestParam.entrySet()) {
				if(mapRequestTemplate.containsKey(subEntry.getKey())){
					if(mapCoreParam.containsKey(subEntry.getValue())){
						mapRequestTemplate.put(subEntry.getKey(), mapCoreParam.get(subEntry.getValue()).toString());
					}else{
						mapRequestTemplate.put(subEntry.getKey(), subEntry.getValue());	
					}
					
				}
			}
			requestBiller =m.writeValueAsString(mapRequestTemplate);
		} catch (IOException e) {
			logger.error(TAG + "Mapping Request XMLRPC. Info : " + e.getMessage());
			requestBiller ="";
		} 
		return requestBiller;
	}
}
