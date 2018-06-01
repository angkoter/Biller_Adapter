//package com.jatismobile.plugin.iso8583.examples;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.net.Socket;
//import java.util.HashMap;
//
//import org.codehaus.jackson.map.ObjectMapper;
//import org.jpos.iso.ISOMsg;
//import org.jpos.iso.packager.GenericPackager;
//
//import com.jatismobile.plugin.iso8583.DBConfiguration;
//import com.jatismobile.plugin.iso8583.implement.InquiryResponseMapper;
//import com.jatismobile.plugin.iso8583.implement.MapperWithoutChecksumhash;
//import com.jatismobile.plugin.iso8583.interfaces.AbstractISOMessageHandler;
//import com.jatismobile.plugin.iso8583.utils.ISOResponseDataInserter;
//import com.jatismobile.plugin.iso8583.utils.PrintStringToFile;
//import com.jatismobile.plugin.iso8583.utils.STANStringGenerator;
//import com.jatismobile.plugin.iso8583.utils.StringToISOMessage;
//
//public class JatelindoISOMessageHandler extends AbstractISOMessageHandler{
//
//	private InquiryResponseMapper responseMapper;
//
//	public JatelindoISOMessageHandler(DBConfiguration dbConfig) {
//		super(dbConfig);
//		responseMapper = InquiryResponseMapper.getMapper();
//	}
//
//	public static void main (String[] args) throws Exception {
//		
//		//=========Below is the Jatelindo main process Hitter API=========\\
//
//		// mock data for testing
//		GenericPackager packager = new GenericPackager("fields.xml");
////		String STAN = STANStringGenerator.getSTANString(6);
//		String STAN = args[1];
//		HashMap<String, Object> generalRequest = new HashMap<String, Object>();
//		generalRequest.put("TRX_ID", "1234567"+STAN);
//		generalRequest.put("PRODUCT_CODE", "134003");
//		generalRequest.put("SUBSCRIBER_NUMBER", args[0]);
//		generalRequest.put("JATIS_BILLER_ID", "5");
//		generalRequest.put("VENDOR_BILLER_ID", "7");
//		String template = "{\"0\":\"0200\",\r\n\"2\":\"PRODUCT_CODE\",\r\n\"3\":\"380000\",\r\n\"7\":\"TRANSMISSION_DATE\",\r\n\"11\":\"STAN\",\r\n\"12\":\"LOCAL_TIME\",\r\n\"13\":\"LOCAL_DATE\",\r\n\"15\":\"SETTLEMENT_DATE\",\r\n\"18\":\"6012\",\r\n\"32\":\"008\",\r\n\"37\":\"RANDOM_REFERENCE_NUMBER\",\r\n\"41\":\"DEVJATIS\",\r\n\"42\":\"200900100800000\",\r\n\"48\":\"SUBSCRIBER_NUMBER\",\r\n\"49\":\"360\"}";
//
//		ObjectMapper om = new ObjectMapper();
//		String requestString = om.writeValueAsString(generalRequest);
//		
//		//configure JatelindoISOMessageHandler
////		DBConfiguration dbConfig = DBConfiguration.getConfig("localhost:3306/", "switching", "root", "");
//		JatelindoISOMessageHandler jatelindo = new JatelindoISOMessageHandler(
//				null);
//		jatelindo.objectMapper = new ObjectMapper();
//		
//		String requestTemplate = template;
//		jatelindo.setRequestTemplate(requestTemplate);
//		jatelindo.setGenericPackager(packager);
//		
//		Socket socket = null;
//		Socket con = null;
//		socket = new Socket("localhost", 1423);
////		socket = new Socket("202.152.22.118", 1423);
//		jatelindo.setCon(socket);
//		con = jatelindo.getCon();
//		con.setKeepAlive(true);
//		con.setSoTimeout(60000);
//
//		//mapping general request and building specified request (iso message)
//		ISOMsg mappedRequest = MapperWithoutChecksumhash.getMappedMessage(requestString, ISOMsg.OUTGOING, "0200", STAN,
//				jatelindo);
//		String tempToString = "general request : " + requestString +"\n\n";
//		tempToString = tempToString + "request : " + new String(mappedRequest.pack())+"\n\n";
//		//sending message to server
//		jatelindo.sendMessage(mappedRequest,con.getOutputStream());
//		
//		//receiving response from server
//		String isoString = jatelindo.receive(con.getInputStream());
//		tempToString = tempToString + "response : " + isoString + "\n\n";
//		//iso message unpacker
//		ISOMsg response = StringToISOMessage.getISOMessage(isoString, jatelindo.getPackager());
//		
//		//mapping general response from specfied response
//		HashMap<String, Object> generalizedResponse = jatelindo.responseMapper.getMappedResponse(response);
//		generalizedResponse.put("SUBSCRIBER_NUMBER", generalRequest.get("SUBSCRIBER_NUMBER"));
//		generalizedResponse.put("INQUIRY_ID", STAN);
//
//		String generalResponse = jatelindo.objectMapper.writeValueAsString(generalizedResponse);
//		tempToString = tempToString + "General Response : " + generalResponse + "\n\n";
//		
//		String str486061 = "Field 48 : " + response.getString(48) + "\n";
//		str486061 = str486061 + "Field 60 : " + response.getString(60) + "\n";
//		str486061 = str486061 + "Field 61 : " + response.getString(61) + "\n";
//		PrintStringToFile.print(tempToString+str486061);
//
//		//inserting data to database
////		boolean isDataInserted = ISOResponseDataInserter.insert(generalizedResponse, jatelindo.getDbConfig(), "inquiry", isoString, STAN);
//		System.out.println("General Response : " + generalResponse);
//	}
//	
//	public void setResponseMapper(InquiryResponseMapper responseMapper) {
//		this.responseMapper = responseMapper;
//	}
//	
//	public InquiryResponseMapper getResponseMapper() {
//		return responseMapper;
//	}
//}
