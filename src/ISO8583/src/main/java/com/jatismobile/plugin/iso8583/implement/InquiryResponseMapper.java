//package com.jatismobile.plugin.iso8583.implement;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//
//import org.codehaus.jackson.JsonGenerationException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.jpos.iso.ISOException;
//import org.jpos.iso.ISOMsg;
//
//import com.jatismobile.plugin.iso8583.jatelindo.Field48GeneralParser;
//
////import com.jatismobile.plugin.iso8583.utils.Field48GeneralParser;
//
//public class InquiryResponseMapper {
//
//	private static InquiryResponseMapper generalInquiryMapper;
//	private static ObjectMapper om;
//
//	public static final synchronized InquiryResponseMapper getMapper() {
//		if (generalInquiryMapper == null) {
//			generalInquiryMapper = new InquiryResponseMapper();
//			om = new ObjectMapper();
//		}
//		return generalInquiryMapper;
//	}
//
//	public HashMap<String, Object> getMappedResponse(ISOMsg response, String format)
//			throws ISOException, JsonGenerationException, JsonMappingException, IOException {
//		HashMap<String, Object> generalResponse = new LinkedHashMap<String, Object>();
//		if (response != null) {
//			if (response.getString(48) != null) {
//				if (response.getString(48).toString().length() > 56) {
//					String field48_60_61 = null;
//					if (response.getString(60) != null && response.getString(61) != null) {
//						field48_60_61 = response.getString(48).toString() + response.getString(60) + response.getString(61);
////						System.out.println("60 61 : " + response.getString(60) + response.getString(61));
//					}
//					else if (response.getString(60) != null && response.getString(61) == null) {
//						field48_60_61 = response.getString(48).toString() + response.getString(60);
//					}
//					else if (response.getString(60) == null && response.getString(61) != null) {
//						field48_60_61 = response.getString(48).toString() + response.getString(61);
//					}
//					else if (response.getString(60) == null && response.getString(61) == null) {
//						field48_60_61 = response.getString(48).toString();
//					}
//					HashMap<String, Object> field486061 = Field48GeneralParser.parse(format, om, field48_60_61);
//					HashMap<String, Object> realResponse = new LinkedHashMap<>();
//					realResponse.put("STAN", response.getString(11));
//					realResponse.put("Name_List", Field48GeneralParser.getFamilyNameList(field48_60_61, format));
//					realResponse.put("pesan_biller", response.getString(62));
//					realResponse.put("loket", response.getString(63));
//					realResponse.put("Data", field486061);
//	
//					generalResponse.put("INQUIRY_ID", response.getString(11));
//					generalResponse.put("INQUIRY_STATUS", response.getString(39));
//					generalResponse.put("INQUIRY_STATUS_DESC", null);
//					generalResponse.put("SUBSCRIBER_NUMBER", field486061.get("No_VA_Keluarga"));
//					generalResponse.put("SUBSCRIBER_NAME", Field48GeneralParser.getFamilyHeadName(field48_60_61));
//					generalResponse.put("ADMIN_FEE", field486061.get("Biaya_Admin"));
//					generalResponse.put("TRX_AMOUNT", field486061.get("Total_Premi"));
//					generalResponse.put("BILL_ID", null);
//					generalResponse.put("BILLER_REAL_RESPONSE", om.writeValueAsString(realResponse));
//				} else {
//					HashMap<String, Object> realResponse = new LinkedHashMap<>();
//					realResponse.put("STAN", response.getString(11));
//					realResponse.put("No_VA_Keluarga", response.getString(48).substring(0,response.getString(48).length()-2));
//					realResponse.put("Jumlah_Bulan", response.getString(48).substring(response.getString(48).length()-2,response.getString(48).length()));
//					realResponse.put("pesan_biller", response.getString(62));
//					realResponse.put("loket", response.getString(63));
//					
//					generalResponse.put("INQUIRY_ID", response.getString(11));
//					generalResponse.put("INQUIRY_STATUS", response.getString(39));
//					generalResponse.put("INQUIRY_STATUS_DESC", null);
//					generalResponse.put("SUBSCRIBER_NUMBER", response.getString(48));
//					generalResponse.put("SUBSCRIBER_NAME", null);
//					generalResponse.put("ADMIN_FEE", null);
//					generalResponse.put("TRX_AMOUNT", response.getString(4));
//					generalResponse.put("BILL_ID", null);
//					generalResponse.put("BILLER_REAL_RESPONSE", om.writeValueAsString(realResponse));
//				}
//			}
//			else {
//				HashMap<String, Object> realResponse = new LinkedHashMap<>();
//				realResponse.put("STAN", response.getString(11));
//				realResponse.put("pesan_biller", response.getString(62));
//				realResponse.put("loket", response.getString(63));
//				realResponse.put("Data", response.getString(48));
//				
//				generalResponse.put("INQUIRY_ID", response.getString(11));
//				generalResponse.put("INQUIRY_STATUS", response.getString(39));
//				generalResponse.put("INQUIRY_STATUS_DESC", null);
//				generalResponse.put("SUBSCRIBER_NUMBER", null);
//				generalResponse.put("SUBSCRIBER_NAME", null);
//				generalResponse.put("ADMIN_FEE", null);
//				generalResponse.put("TRX_AMOUNT", response.getString(4));
//				generalResponse.put("BILL_ID", null);
//				generalResponse.put("BILLER_REAL_RESPONSE", om.writeValueAsString(realResponse));
//			}
//		} else {
//			generalResponse.put("INQUIRY_ID", null);
//			generalResponse.put("INQUIRY_STATUS", null);
//			generalResponse.put("INQUIRY_STATUS_DESC", null);
//			generalResponse.put("SUBSCRIBER_NUMBER", null);
//			generalResponse.put("SUBSCRIBER_NAME", null);
//			generalResponse.put("ADMIN_FEE", null);
//			generalResponse.put("TRX_AMOUNT", null);
//			generalResponse.put("BILL_ID", null);
//			generalResponse.put("BILLER_REAL_RESPONSE", null);
//		}
//
//		return generalResponse;
//	}
//}