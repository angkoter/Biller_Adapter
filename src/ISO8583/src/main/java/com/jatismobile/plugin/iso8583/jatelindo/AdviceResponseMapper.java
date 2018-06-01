package com.jatismobile.plugin.iso8583.jatelindo;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import com.jatismobile.plugin.iso8583.DBConfiguration;
import com.jatismobile.plugin.iso8583.SuccessResponseJatelindo;
//import com.jatismobile.plugin.iso8583.utils.Field48PurchaseParser;

public class AdviceResponseMapper {
	private static AdviceResponseMapper generalPurchaseMapper;
	private static ObjectMapper om;

	public static final synchronized AdviceResponseMapper getMapper() {
		if (generalPurchaseMapper == null) {
			generalPurchaseMapper = new AdviceResponseMapper();
			om = new ObjectMapper();
		}
		return generalPurchaseMapper;
	}

	public HashMap<String, Object> getMappedResponse(ISOMsg response, String format) throws ISOException, JsonGenerationException, JsonMappingException, IOException {
		HashMap<String, Object> generalResponse = new LinkedHashMap<String, Object>();
		if (response != null) {
			if (response.getString(39).equals(SuccessResponseJatelindo.getResponseCode(DBConfiguration.getConfig()).getRc())) {
				if (response.getString(48).toString().length() > 56) {
					String field48_60_61 = null;
					if (response.getString(60) != null && response.getString(61) != null) {
						field48_60_61 = response.getString(48).toString() + response.getString(60) + response.getString(61);
					}
					else if (response.getString(60) != null && response.getString(61) == null) {
						field48_60_61 = response.getString(48).toString() + response.getString(60);
					}
					else if (response.getString(60) == null && response.getString(61) != null) {
						field48_60_61 = response.getString(48).toString() + response.getString(61);
					}
					else if (response.getString(60) == null && response.getString(61) == null) {
						field48_60_61 = response.getString(48).toString();
					}
					HashMap<String, Object> field486061 = Field48GeneralParser.parse(format, om, field48_60_61);
					HashMap<String, Object> realResponse = new LinkedHashMap<String, Object>();
					realResponse.put("NO_REFERENSI", response.getString(37));
					realResponse.put("STAN", response.getString(11));
					realResponse.put("pesan_biller", response.getString(62));
					realResponse.put("loket", response.getString(63));
					realResponse.put("Data", field486061);
		
					generalResponse.put("PURCHASE_STATUS", response.getString(39));
					generalResponse.put("PURCHASE_STATUS_DESC", null);
					generalResponse.put("REFERENCE_NUMBER", response.getString(37));
					generalResponse.put("PURCHASE_TIME", Field48GeneralParser.getPurchaseTime(om, format, field486061, response));
					generalResponse.put("BILLER_REAL_RESPONSE", new String(response.pack()));
				}
				else {
					HashMap<String, Object> realResponse = new LinkedHashMap<String, Object>();
					realResponse.put("NO REFERENSI", response.getString(37));
					realResponse.put("STAN", response.getString(11));
					realResponse.put("pesan_biller", response.getString(62));
					realResponse.put("loket", response.getString(63));
		
					generalResponse.put("PURCHASE_STATUS", response.getString(39));
					generalResponse.put("PURCHASE_STATUS_DESC", null);
					generalResponse.put("REFERENCE_NUMBER", response.getString(37));
					generalResponse.put("PURCHASE_TIME", response.getString(7));
					generalResponse.put("BILLER_REAL_RESPONSE", new String(response.pack()));
				}
			}
			else {
				HashMap<String, Object> realResponse = new LinkedHashMap<String, Object>();
				realResponse.put("NO REFERENSI", response.getString(37));
				realResponse.put("STAN", response.getString(11));
				realResponse.put("pesan_biller", response.getString(62));
				realResponse.put("loket", response.getString(63));
	
				generalResponse.put("PURCHASE_STATUS", response.getString(39));
				generalResponse.put("PURCHASE_STATUS_DESC", null);
				generalResponse.put("REFERENCE_NUMBER", response.getString(37));
				generalResponse.put("PURCHASE_TIME", response.getString(7));
				generalResponse.put("BILLER_REAL_RESPONSE", om.writeValueAsString(realResponse));
			}
		} else {
			generalResponse.put("PURCHASE_STATUS", null);
			generalResponse.put("PURCHASE_STATUS_DESC", null);
			generalResponse.put("REFERENCE_NUMBER", null);
			generalResponse.put("PURCHASE_TIME", null);
			generalResponse.put("BILLER_REAL_RESPONSE", null);
		}

		return generalResponse;
	}
	
	public HashMap<String, Object> getMappedResponse(ISOMsg response, ISOMsg request, String format) throws ISOException, JsonGenerationException, JsonMappingException, IOException {
		HashMap<String, Object> generalResponse = new LinkedHashMap<String, Object>();
		HashMap<String, Object> mFormat = om.readValue(format, HashMap.class);
		String tempMaterial = mFormat.get("ResponseParsingMaterial").toString();
		String[] material = tempMaterial.split("\\|");
		if (response != null) {
			if (response.getString(39).equals(SuccessResponseJatelindo.getResponseCode(DBConfiguration.getConfig()).getRc())) {
				if (response.getString(48).toString().length() > request.getString(48).toString().length()) {
					String field48_60_61 = "";
					for (int i = 0; i < material.length; i++) {
						String string = material[i];
						int materialIndex = Integer.valueOf(string);
						field48_60_61 = field48_60_61 + response.getString(materialIndex);
					}
					
					HashMap<String, Object> field486061 = Field48GeneralParser.parse(format, om, field48_60_61);
		
					generalResponse.put("PURCHASE_STATUS", response.getString(39));
					generalResponse.put("PURCHASE_STATUS_DESC", null);
					generalResponse.put("REFERENCE_NUMBER", response.getString(37));
					generalResponse.put("PURCHASE_TIME", Field48GeneralParser.getPurchaseTime(om, format, field486061, response));
					generalResponse.put("BILLER_REAL_RESPONSE", new String(response.pack()));
				}
				else {
					HashMap<String, Object> realResponse = new LinkedHashMap<String, Object>();
					realResponse.put("NO REFERENSI", response.getString(37));
					realResponse.put("STAN", response.getString(11));
					realResponse.put("pesan_biller", response.getString(62));
					realResponse.put("loket", response.getString(63));
		
					generalResponse.put("PURCHASE_STATUS", response.getString(39));
					generalResponse.put("PURCHASE_STATUS_DESC", null);
					generalResponse.put("REFERENCE_NUMBER", response.getString(37));
					generalResponse.put("PURCHASE_TIME", response.getString(7));
					generalResponse.put("BILLER_REAL_RESPONSE", new String(response.pack()));
				}
			}
			else {
				HashMap<String, Object> realResponse = new LinkedHashMap<String, Object>();
				realResponse.put("NO REFERENSI", response.getString(37));
				realResponse.put("STAN", response.getString(11));
				realResponse.put("pesan_biller", response.getString(62));
				realResponse.put("loket", response.getString(63));
	
				generalResponse.put("PURCHASE_STATUS", response.getString(39));
				generalResponse.put("PURCHASE_STATUS_DESC", null);
				generalResponse.put("REFERENCE_NUMBER", response.getString(37));
				generalResponse.put("PURCHASE_TIME", response.getString(7));
				generalResponse.put("BILLER_REAL_RESPONSE", om.writeValueAsString(realResponse));
			}
		} else {
			generalResponse.put("PURCHASE_STATUS", null);
			generalResponse.put("PURCHASE_STATUS_DESC", null);
			generalResponse.put("REFERENCE_NUMBER", null);
			generalResponse.put("PURCHASE_TIME", null);
			generalResponse.put("BILLER_REAL_RESPONSE", null);
		}

		return generalResponse;
	}
	
}