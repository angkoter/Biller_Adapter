package com.jatismobile.plugin.iso8583.interfaces;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import org.codehaus.jackson.map.ObjectMapper;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

import com.jatismobile.plugin.iso8583.DBConfiguration;
import com.jatismobile.plugin.iso8583.jatelindo.MapperWithChecksumhash;
//import com.jatismobile.plugin.iso8583.implement.InquiryResponseMapper;
//import com.jatismobile.plugin.iso8583.implement.MapperWithChecksumhash;
import com.jatismobile.plugin.iso8583.jatelindo.MapperWithoutChecksumhash;
//import com.jatismobile.plugin.iso8583.implement.MapperWithoutChecksumhash;
import com.jatismobile.plugin.iso8583.utils.ISOHeader;

public abstract class AbstractISOMessageHandler {

	private Socket con;
	private DataOutputStream out;
	private Object address;
	private int port;
//	private InquiryResponseMapper responseMapper;
	private GenericPackager packager;
	public ObjectMapper objectMapper;
	private String requestTemplate;
	private DBConfiguration dbConfig;

	private String responseTemplate;
	private MapperWithoutChecksumhash requestMapperWithoutChecksumhash;
	private MapperWithChecksumhash requestMapperWithChecksumhash;

	public AbstractISOMessageHandler (String address, int port, GenericPackager packager,
										String requestTemplate, String responseTemplate,
										DBConfiguration dbConfig) throws
				UnknownHostException, IOException, ISOException {
		this.address = address;
		this.port = port;
//		this.setCon(new Socket(address, port));
		
		this.packager = packager;
		this.requestTemplate = requestTemplate;
		this.responseTemplate = responseTemplate;
//		this.setResponseMapper(InquiryResponseMapper.getMapper());
		this.objectMapper = new ObjectMapper();
//		this.requestMapperWithoutChecksumhash = new MapperWithoutChecksumhash(this);
//		this.requestMapperWithChecksumhash = new MapperWithChecksumhash(this);
		this.setDbConfig(dbConfig);
	}
	
	public AbstractISOMessageHandler (DBConfiguration dbConfig) {
		this.objectMapper = new ObjectMapper();
		this.setDbConfig(dbConfig);
	}
	
	//receiving message from Core
	public static void main (String[] args) throws Exception {
		/*
		//=========Below is the Jatelindo main process Hitter API=========\\
		
		// mock data for testing
		GenericPackager packager = new GenericPackager("fields.xml");
		HashMap<String, Object> generalRequest = new HashMap<String, Object>();
		generalRequest.put("INQUIRY_ID", "1234567654321"+STANStringGenerator.getSTANString(6));
		generalRequest.put("PRODUCT_CODE", "134003");
		generalRequest.put("SUBSCRIBER_NUMBER", "888880108910747101");
		generalRequest.put("JATIS_BILLER_ID", "5");
		generalRequest.put("VENDOR_BILLER_ID", "7");
		String template = "{\"0\":\"0200\",\r\n\"2\":\"PRODUCT_CODE\",\r\n\"3\":\"380000\",\r\n\"7\":\"TRANSMISSION_DATE\",\r\n\"11\":\"STAN\",\r\n\"12\":\"LOCAL_TIME\",\r\n\"13\":\"LOCAL_DATE\",\r\n\"15\":\"SETTLEMENT_DATE\",\r\n\"18\":\"6012\",\r\n\"32\":\"008\",\r\n\"37\":\"RANDOM_REFERENCE_NUMBER\",\r\n\"41\":\"DEVJATIS\",\r\n\"42\":\"200900100800000\",\r\n\"48\":\"SUBSCRIBER_NUMBER\",\r\n\"49\":\"360\"}";
		
		ObjectMapper om = new ObjectMapper();
		String requestString = om.writeValueAsString(generalRequest);
		
		//configure JatelindoISOMessageHandler
		DBConfiguration dbConfig = DBConfiguration.getConfig("localhost:3306/", "switching", "root", "");
		new JatelindoISOMessageHandler (
				"localhost", 1423, packager,
				template, template, dbConfig);
		
		Socket con = jatelindo.getCon();
		con.setKeepAlive(true);
		con.setSoTimeout(30000);

		//mapping general request and building specified request (iso message)
		String STAN = STANStringGenerator.getSTANString(6);
		String mappedRequest = jatelindo.requestMapper.getMappedMessage(requestString, ISOMsg.OUTGOING, "0200", STAN);
		
		//sending message to server
		jatelindo.sendMessage(mappedRequest,con.getOutputStream());
		
		//receiving response from server
		String isoString = jatelindo.receive(con.getInputStream());
		
		//iso message unpacker
		ISOMsg response = StringToISOMessage.getISOMessage(isoString, jatelindo.getPackager());
		
		//mapping general response from specfied response
		HashMap<String, Object> generalizedResponse = jatelindo.responseMapper.getMappedResponse(response);
		generalizedResponse.put("SUBSCRIBER_NUMBER", generalRequest.get("SUBSCRIBER_NUMBER"));
		generalizedResponse.put("INQUIRY_ID", generalRequest.get("INQUIRY_ID"));
		
		String generalResponse = jatelindo.objectMapper.writeValueAsString(generalizedResponse);
		
		//inserting data to database
		boolean isDataInserted = ISOResponseDataInserter.insert(generalizedResponse, jatelindo.dbConfig, "inquiry", isoString, STAN);
		System.out.println("General Response : " + generalResponse);
		System.out.println("Data Inserted : " + isDataInserted);
		*/
	}

	public Socket getCon() {
		return con;
	}

	public void setCon(Socket con) {
		this.con = con;
	}

	public String receive (InputStream input) throws IOException {
		DataInputStream in = new DataInputStream(input);
        int bytesRead = 0;

        byte[] messageByte = new byte[10240];
		messageByte[0] = in.readByte();
        messageByte[1] = in.readByte();
        ByteBuffer byteBuffer = ByteBuffer.wrap(messageByte, 0, 2);

        int bytesToRead = byteBuffer.getShort();
//        System.out.println("About to read " + bytesToRead + " octets");
        boolean end = false;
		String messageString = "";
		while(!end)
        {
            bytesRead = in.read(messageByte);
            messageString += new String(messageByte, 0, bytesRead);
            if (messageString.length() == bytesToRead )
            {
                end = true;
            }
        }
		in.close();
//        System.out.println("MESSAGE: " + messageString);
		return messageString;
	}

	public void sendMessage(ISOMsg msg, OutputStream out) throws ISOException
	{
		try{
			byte[] ss = ISOHeader.getTwoCharactersHeader(msg);
//			byte[] ss = "�".getBytes();
			byte[] mm = ISOHeader.join(ss, msg.pack());
			byte[] nn = ISOHeader.join(mm, ISOHeader.getTrailer(3));
			out.write(nn, 0, nn.length);
			out.flush();
//			System.out.println("client>" + new String(nn));
//			PrintStringToFile.print(new String(nn));
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	public void setGenericPackager(GenericPackager packager) {
		
//		if (this.packager == null) {
//			this.packager = packager;
//		}
		this.packager = packager;
	}

	public GenericPackager getPackager() {
		return packager;
	}
	
	public void setRequestTemplate(String requestTemplate) {
		
//		if (requestTemplate == null) {
//			this.requestTemplate = requestTemplate;
//		}
//		else {
//			System.out.println("weaweawefawfaef : " + requestTemplate);
//		}
		
		this.requestTemplate = requestTemplate;
	}

	public String getRequestTemplate() {
		return requestTemplate;
	}
	public DataOutputStream getOut() {
		return out;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Object getAddress() {
		return address;
	}
	
	public void setPort (int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
	
	public void setResponseTemplate(String responseTemplate) {
		
//		if (responseTemplate == null) {
//			this.responseTemplate = responseTemplate;
//		}
		this.responseTemplate = responseTemplate;
	}
	
	public String getResponseTemplate() {
		return responseTemplate;
	}

	public DBConfiguration getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(DBConfiguration dbConfig) {
		this.dbConfig = dbConfig;
	}

//	public InquiryResponseMapper getResponseMapper() {
//		return responseMapper;
//	}

//	public void setResponseMapper(InquiryResponseMapper responseMapper) {
//		this.responseMapper = responseMapper;
//	}
//
	public MapperWithoutChecksumhash getRequestMapperWithoutChecksumhash() {
		return requestMapperWithoutChecksumhash;
	}

	public void setRequestMapperWithoutChecksumhash(MapperWithoutChecksumhash requestMapperWithoutChecksumhash) {
		this.requestMapperWithoutChecksumhash = requestMapperWithoutChecksumhash;
	}

	public MapperWithChecksumhash getRequestMapperWithChecksumhash() {
		return requestMapperWithChecksumhash;
	}

	public void setRequestMapperWithChecksumhash(MapperWithChecksumhash requestMapperWithChecksumhash) {
		this.requestMapperWithChecksumhash = requestMapperWithChecksumhash;
	}
}
