//package com.jatismobile.plugin.iso8583.implement;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//
//import org.codehaus.jackson.JsonParseException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.jpos.iso.ISOException;
//import org.jpos.iso.ISOMsg;
//
//import com.jatismobile.plugin.iso8583.interfaces.AbstractISOMessageHandler;
//import com.jatismobile.plugin.iso8583.utils.DateIncrementer;
//import com.jatismobile.plugin.iso8583.utils.ReferenceNumberGenerator;
//
//public class MapperWithChecksumhash {
//
////	public MapperWithChecksumhash(AbstractISOMessageHandler handler) {
////		this.objectMapper = new ObjectMapper();
////		this.packager = handler.getPackager();
////		this.requestTemplate = handler.getRequestTemplate();
////	}
//	
//	@SuppressWarnings("unchecked")
//	public static ISOMsg getMappedMessage(String generalRequest,int direction, String MTI, String STAN, AbstractISOMessageHandler handler, String checksumhash)
//			throws JsonParseException, JsonMappingException, IOException, ISOException {
//		ISOMsg output = new ISOMsg();
//		output.setPackager(handler.getPackager());
//		HashMap<String, Object> requestMap = handler.getObjectMapper().readValue(generalRequest, HashMap.class);
//		HashMap<String, Object> requestTemplate = handler.getObjectMapper().readValue(handler.getRequestTemplate(), HashMap.class);
//
//		output.setMTI(MTI);
//		for (HashMap.Entry<String, Object> entry : requestTemplate.entrySet()) {
////			System.out.println("requestTemplate key : " + entry.getKey() + ", value : " + entry.getValue());
//			if (requestMap.containsKey(entry.getValue().toString())) {
//				output.set(entry.getKey(),requestMap.get(entry.getValue().toString()).toString());
//			}
//			else if (entry.getValue().equals("STAN")) {
//				output.set(11,STAN);
//			}
//			else if (entry.getValue().equals("RANDOM_REFERENCE_NUMBER")) {
//				output.set(37,ReferenceNumberGenerator.getReferenceNumber(12));
//			}
//			else {
//				output.set(entry.getKey(),entry.getValue().toString());
//			}
//		}
//		
//		if (requestMap.get("PERIOD").toString().length() > 0) {
//			output.set(48, requestMap.get("SUBSCRIBER_NUMBER").toString() + requestMap.get("PERIOD").toString());
//		}
//		
//		Date date = new Date();
//		output.set(7,new SimpleDateFormat("MMddHHmmss").format(date));
//		output.set(12,new SimpleDateFormat("HHmmss").format(date));
//		output.set(13,new SimpleDateFormat("MMdd").format(date));
//		output.set(15,DateIncrementer.addOneDay(new SimpleDateFormat("yyyyMMdd").format(date)));
//		output.setDirection(direction);
//		output.setPackager(handler.getPackager());
//		return output;
////		return new String(output.pack());
//	}
//	
//}
