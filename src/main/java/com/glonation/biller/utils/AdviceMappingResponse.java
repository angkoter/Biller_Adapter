package com.glonation.biller.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.glonation.biller.messages.GeneralResponse;
import com.glonation.biller.messages.GeneralResponse.Purchase;

public class AdviceMappingResponse {
	private static final Logger logger = LoggerFactory.getLogger(AdviceMappingResponse.class);
	private ObjectMapper m = new ObjectMapper();
	private String TAG = "[" + this.getClass().getSimpleName() + "] ";
	LinkedHashMap<String, String> responseToCore = GeneralResponse.Purchase.getInitialResponse();
	
	public HashMap<String, String> getRequestParam(String responseParam) {
		HashMap<String, String> mapResponseParam = new HashMap<>();
		String splitResponseParam[] = responseParam.split("\\|");
		for (String string : splitResponseParam) {
			String tempParam[] = string.split("=");
			mapResponseParam.put(tempParam[0], tempParam[1]);
		}
		return mapResponseParam;
	}

	public String xmlToJson( String response, String responseParam) {
		/*
		 * for Get Request ex : <?xml version='1.0' encoding='utf-8'?><response><name>angkot</name><add>jalan
		 * raya</add></response> {"name":"angkot","add":"jalan raya",etc}
		 */
		HashMap<String, String> mapResponseParam = getRequestParam(responseParam);
		LinkedHashMap<String, String> responseToCore = GeneralResponse.Advice.getInitialResponse();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			// set string response XML to dom
			InputSource isResponse = new InputSource(new StringReader(response));
			Document docResponse = builder.parse(isResponse);
			docResponse.getDocumentElement().normalize();

			for (HashMap.Entry<String, String> subEntry : mapResponseParam.entrySet()) {
				NodeList temp = docResponse.getElementsByTagName(subEntry.getKey());
				if(temp.getLength()>0){
					responseToCore.put(subEntry.getValue(), temp.item(0).getTextContent());	
				}
				
			}

		} catch (ParserConfigurationException e) {
			logger.debug(TAG + e.getMessage());
		} catch (SAXException e) {
			logger.debug(TAG + e.getMessage());
		} catch (IOException e) {
			logger.debug(TAG + e.getMessage());
		}

		try {
			responseToCore.put("BILLER_REAL_RESPONSE", response);
			return m.writeValueAsString(responseToCore);
		} catch (JsonGenerationException e) {
			logger.debug(TAG + e.getMessage());
			return "";
		} catch (JsonMappingException e) {
			logger.debug(TAG + e.getMessage());
			return "";
		} catch (IOException e) {
			logger.debug(TAG + e.getMessage());
			return "";
		}

	}



	public String domToString(Document doc)
			throws SAXException, IOException, ParserConfigurationException, TransformerException {
		// write tostring
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.transform(source, result);

		return writer.toString();
	}
}
