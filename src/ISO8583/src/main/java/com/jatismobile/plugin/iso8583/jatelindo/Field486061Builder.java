package com.jatismobile.plugin.iso8583.jatelindo;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jpos.iso.ISOMsg;

public class Field486061Builder {
	public static ISOMsg build(ISOMsg isoMsg, LinkedHashMap<String, Object> mapField486061, String format, HashMap<String, Object> requestMap) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper om = new ObjectMapper();

		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> temp48 = mapField486061;
		StringBuilder sb = new StringBuilder();
		for (HashMap.Entry<String, Object> entry : temp48.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
//			System.out.println("key : " + key + ", value : " + value);
			if (value instanceof List) {
//				System.out.println("masuk list : " + value);
				List<LinkedHashMap<String, Object>> val = (List) value;
				int loop = ((List) value).size();
				for (int i = 0; i < loop; i++) {
					for (HashMap.Entry<String, Object> entry2 : val.get(i).entrySet()) {
						sb.append(entry2.getValue().toString());
					}
				}
			}
			else {
				sb.append(value);
			}
		}
//		System.out.println("sb : " + sb.toString());
		
		String temp = sb.toString();
		String f48 = "";
		String f60 = "";
		String f61 = "";
		ISOMsg output = isoMsg;
		if (temp.length() >= 1000) {
			f48 = temp.substring(0, 999);
			temp = temp.substring(0, 999);
			if (temp.length() >= 1000) {
				f60 = temp.substring(0, 999);
				temp = temp.substring(0, 999);
				f61 = temp;
				output.set(48, Field48Builder.build(format, requestMap)+f48.substring(18));
				output.set(60, f60);
				output.set(61, f61);
			}
			else {
				f60 = temp;
				output.set(48, Field48Builder.build(format, requestMap)+f48.substring(18));
				output.set(60, f60);
			}
		}
		else {
			f48 = temp;
			output.set(48, Field48Builder.build(format, requestMap)+f48.substring(18));
		}
		return output;
	}
	
	public static ISOMsg build(ISOMsg isoMsg, ISOMsg requesField486061) throws JsonParseException, JsonMappingException, IOException{
		String requesField48 = requesField486061.getString(48);
		String requestField60 = requesField486061.getString(60);
		String requestFiled61 = requesField486061.getString(61);
		String field48Builded = isoMsg.getString(48);
		
		if (requestField60 != null) {
//			System.out.println("60 : " + requestField60);
			requesField48 = requesField48+requestField60;
//			System.out.println("reqgabungan : " + requesField48);
		}
		if (requestFiled61 != null) {
//			System.out.println("61 : " + requestFiled61);
			requesField48 = requesField48+requestFiled61;
//			System.out.println("reqgabungan : " + requesField48);
		}
		
		String temp = requesField48.toString();
		String f48 = "";
		String f60 = "";
		String f61 = "";
		ISOMsg output = isoMsg;
		if (temp.length() >= 1000) {
			f48 = temp.substring(0, 999);
			temp = temp.substring(0, 999);
			if (temp.length() >= 1000) {
				f60 = temp.substring(0, 999);
				temp = temp.substring(0, 999);
				f61 = temp;
				output.set(48, field48Builded+f48.substring(18));
				output.set(60, f60);
				output.set(61, f61);
			}
			else {
				f60 = temp;
				output.set(48, field48Builded+f48.substring(18));
				output.set(60, f60);
			}
		}
		else {
			f48 = temp;
			output.set(48, field48Builded+f48.substring(18));
		}
		return output;
	}
}
