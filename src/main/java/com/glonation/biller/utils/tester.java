package com.glonation.biller.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class tester {
	public static void main(String[] args) {
		// xmltojson
		// MappingResponse mappingResponse = new MappingResponse();
		// String responseTemplate =
		// "<response><rescode>4</rescode><hp>081311112222</hp><vtype>S20</vtype><server_trxid>623814</server_trxid><partner_trxid>im383000120100203002</partner_trxid><resmessage>Status
		// transaksi im383000120100203002, s20081311112222 Harga:5300 tanggal
		// 2010-02-03 20:34:14adalah Sukses Vsn 006300656124164749
		// Saldo:464631100-20000=464611100</resmessage><sn>006300656124164749</sn><balance>464611100</balance><price>5300</price></response>";
		// String response = "<?xml version=\"1.0\"?>\r\n" +
		// "<response><xrescode>0</xrescode><rescode>4</rescode><hp>081219465769</hp><vtype>SJS25</vtype><server_trxid>2039</server_trxid><partner_trxid>TEST201805118983060518020002</partner_trxid><scrmessage>SUKSES</scrmessage><harga>25000</harga><balance>98820000</balance><sn>31002039</sn><resmessage>Status
		// transaksi TEST201805118983060518020002, SJS25 081219465769
		// Harga:25000 tanggal 2018-05-11 15:41:28 adalah Sukses Vsn 31002039
		// Saldo:98845000-25000=98820000</resmessage></response>";
		// String responseError = "<?xml version=\"1.0\"?>\r\n"
		// + "<response><rescode>10</rescode><scrmessage>SIGNATURE
		// ERR</scrmessage><resmessage/><datetime>20180511110306</datetime></response>";
		// String responseParam =
		// "rescode=PURCHASE_STATUS|server_trxid=REFERENCE_NUMBER|scrmessage=PURCHASE_STATUS_DESC";
		// System.out.println(mappingResponse.xmlToJson(response,
		// responseParam));
//12019
//123456
		// alfnum id
//		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//		String fullalphabet = alphabet + alphabet.toLowerCase() + "123456789";
//		Random random = new Random();
//		char code = fullalphabet.charAt(random.nextInt(9));
//		System.out.println(Character.toString(code).toLowerCase());
//		System.out.println(new StringBuilder(new String("MERDEKA")).insert(3, "L").toString());
//		IDGenerator generator = new IDGenerator();
//		System.out.println("1 "+generator.generateIdAlfNum("1", "sajsd", 20));
//		System.out.println("2 "+generator.generateIdAlfNum("1", "sajsd", 20));
//		System.out.println("3 "+generator.generateIdAlfNum("1", "sajsd", 20));
//		System.out.println("4 "+generator.generateIdAlfNum("1", "sajsd", 20));
//		System.out.println("5 "+generator.generateIdAlfNum("1", "sajsd", 20));
		
	//xmlRPC
		MappingRequest mappingRequest = new MappingRequest();
		PurchaseMappingResponse mappingResponse = new PurchaseMappingResponse();
		String requestTemplate ="<?xml version = \"1.0\" encoding = \"iso-8859-1\" ?><methodCall><methodName>purchaseRequest</methodName><params><param><value><struct><member><name>AMOUNT</name><value><int>0</int></value></member><member><name>REQUESTNUMBER</name><value><string>TRX_ID</string></value></member><member><name>DATETIME</name><value><string>TRX_DATE</string></value></member><member><name>PARTNERID</name><value><string>12017</string></value></member><member><name>PRODUCTCODE</name><value><string>PRODUCT_CODE</string></value></member><member><name>SUBSCRIBERID</name><value><string>SUBSCRIBER_NUMBER</string></value></member><member><name>PIN</name><value><string>CHECKSUM</string></value></member></struct></value></param></params></methodCall>";
		String coreParam= "{\"TRX_ID\":\"9021274348392067667130418182816\",\"PRODUCT_CODE\":\"TL5\",\"SUBSCRIBER_NUMBER\":\"6285314055769\",\"TOTAL_TRX_AMOUNT\":5725,\"TRX_AMOUNT\":5725,\"ADMIN_FEE\":0,\"INQUIRY_ID\":\"\",\"JATIS_BILLER_ID\":\"1\",\"VENDOR_BILLER_ID\":\"\",\"BILL_ID\":\"0\",\"MOBILE_NO\":\"\",\"PERIOD\":\"\",\"INQUIRY_DETAIL_RESULT\":\"\",\"TRX_DATE\":\"20180413182859\"}";
		String requestParam= "SUBSCRIBERID=SUBSCRIBER_NUMBER|PRODUCTCODE=PRODUCT_CODE|DATETIME=TRX_DATE|REQUESTNUMBER=TRX_ID|PIN=CHECKSUM";
		String responseBiller ="<?xml version=\"1.0\" encoding=\"utf-8\"?><methodResponse><params><param><value><struct><member><name>RESPONSECODE</name><value>15</value></member><member><name>PARTNERID</name><value>12019</value></member><member><name>DATETIME</name><value>20180601205859</value></member><member><name>PIN</name><value>8a7465c21303dfd969f0b5d5c1cab2d06df7e55a</value></member><member><name>AMOUNT</name><value>0000000000000000</value></member><member><name>SUBSCRIBERID</name><value>087784530888</value></member><member><name>REQUESTNUMBER</name><value>580132210664</value></member><member><name>PRODUCTCODE</name><value>XC10</value></member><member><name>RESPONSEMESSAGE</name><value>Saldo partner tidak mencukupi!</value></member></struct></value></param></params></methodResponse>";
		String responseParam ="RESPONSECODE=PURCHASE_STATUS|RESPONSEMESSAGE=PURCHASE_STATUS_DESC|REQUESTNUMBER=REFERENCE_NUMBER|DATETIME=PURCHASE_TIME";
		System.out.println(mappingRequest.mappingXmlRPCWithChecksum(coreParam, requestTemplate, requestParam, "llalala"));
		System.out.println(mappingResponse.xmlRPCToJson(responseBiller, responseParam).toString());
		
		
	//just iseng
//		String x ="cashierid=";
//		String[] sx = x.split("=");
//		if(sx.length>1){
//			System.out.println(sx[1]);			
//		}else{
//			System.out.println("eweuh");
//
//		}
	}
}
