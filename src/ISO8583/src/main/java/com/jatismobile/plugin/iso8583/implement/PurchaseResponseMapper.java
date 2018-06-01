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
//import com.jatismobile.plugin.iso8583.DBConfiguration;
//import com.jatismobile.plugin.iso8583.SuccessResponseJatelindo;
//import com.jatismobile.plugin.iso8583.utils.Field48PurchaseParser;
//
//public class PurchaseResponseMapper {
//	private static PurchaseResponseMapper generalPurchaseMapper;
//	private static ObjectMapper om;
//
//	public static final synchronized PurchaseResponseMapper getMapper() {
//		if (generalPurchaseMapper == null) {
//			generalPurchaseMapper = new PurchaseResponseMapper();
//			om = new ObjectMapper();
//		}
//		return generalPurchaseMapper;
//	}
//
//	public HashMap<String, Object> getMappedResponse(ISOMsg response) throws ISOException, JsonGenerationException, JsonMappingException, IOException {
//		HashMap<String, Object> generalResponse = new LinkedHashMap<String, Object>();
//		if (response != null) {
//			if (response.getString(39).equals(SuccessResponseJatelindo.getResponseCode(DBConfiguration.getConfig()).getRc())) {
//				if (response.getString(48).toString().length() > 56) {
//					String field48_60_61 = null;
//					if (response.getString(60) != null && response.getString(61) != null) {
//						field48_60_61 = response.getString(48).toString() + response.getString(60) + response.getString(61);
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
//					
//					HashMap<String, Object> field486061 = Field48PurchaseParser.getParsedField(field48_60_61);
//					HashMap<String, Object> realResponse = new LinkedHashMap<String, Object>();
//					realResponse.put("NO REFERENSI", response.getString(37));
//					realResponse.put("TANGGAL", field486061.get("Tanggal_Lunas"));
//					realResponse.put("STAN", response.getString(11));
//					realResponse.put("Name_List", Field48PurchaseParser.getFamilyNameList(field48_60_61));
//					realResponse.put("pesan_biller", response.getString(62));
//					realResponse.put("loket", response.getString(63));
//					realResponse.put("Data", field486061);
//		
//					generalResponse.put("PURCHASE_STATUS", response.getString(39));
//					generalResponse.put("PURCHASE_STATUS_DESC", null);
//					generalResponse.put("REFERENCE_NUMBER", response.getString(37));
//					generalResponse.put("PURCHASE_TIME", response.getString(7));
//					generalResponse.put("BILLER_REAL_RESPONSE", om.writeValueAsString(realResponse));
//				}
//				else {
//					HashMap<String, Object> realResponse = new LinkedHashMap<String, Object>();
//					realResponse.put("NO REFERENSI", response.getString(37));
//					realResponse.put("STAN", response.getString(11));
//					realResponse.put("pesan_biller", response.getString(62));
//					realResponse.put("loket", response.getString(63));
//		
//					generalResponse.put("PURCHASE_STATUS", response.getString(39));
//					generalResponse.put("PURCHASE_STATUS_DESC", null);
//					generalResponse.put("REFERENCE_NUMBER", response.getString(37));
//					generalResponse.put("PURCHASE_TIME", response.getString(7));
//					generalResponse.put("BILLER_REAL_RESPONSE", om.writeValueAsString(realResponse));
//				}
//			}
//			else {
//				HashMap<String, Object> realResponse = new LinkedHashMap<String, Object>();
//				realResponse.put("NO REFERENSI", response.getString(37));
//				realResponse.put("STAN", response.getString(11));
//				realResponse.put("pesan_biller", response.getString(62));
//				realResponse.put("loket", response.getString(63));
//	
//				generalResponse.put("PURCHASE_STATUS", response.getString(39));
//				generalResponse.put("PURCHASE_STATUS_DESC", null);
//				generalResponse.put("REFERENCE_NUMBER", response.getString(37));
//				generalResponse.put("PURCHASE_TIME", response.getString(7));
//				generalResponse.put("BILLER_REAL_RESPONSE", om.writeValueAsString(realResponse));
//			}
//		} else {
//			generalResponse.put("PURCHASE_STATUS", null);
//			generalResponse.put("PURCHASE_STATUS_DESC", null);
//			generalResponse.put("REFERENCE_NUMBER", null);
//			generalResponse.put("PURCHASE_TIME", null);
//			generalResponse.put("BILLER_REAL_RESPONSE", null);
//		}
//
//		return generalResponse;
//	}
//}