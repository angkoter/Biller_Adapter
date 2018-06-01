//package com.jatismobile.plugin.iso8583.utils;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//
//import org.codehaus.jackson.JsonParseException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.jpos.iso.ISOMsg;
//
//public class Field486061Builder {
//	public static ISOMsg build(ISOMsg isoMsg, LinkedHashMap<String, Object> mapField486061, HashMap<String, Object> requestMap) throws JsonParseException, JsonMappingException, IOException{
//		ObjectMapper om = new ObjectMapper();
//
//		@SuppressWarnings("unchecked")
//		LinkedHashMap<String, Object> temp48 = mapField486061;
//		StringBuilder sb = new StringBuilder();
//		for (HashMap.Entry<String, Object> entry : temp48.entrySet()) {
//			String key = entry.getKey();
//			Object value = entry.getValue();
////			System.out.println("key : " + key + ", value : " + value);
//			if (value instanceof List) {
////				System.out.println("masuk list : " + value);
//				List<LinkedHashMap<String, Object>> val = (List) value;
//				int loop = ((List) value).size();
//				for (int i = 0; i < loop; i++) {
//					for (HashMap.Entry<String, Object> entry2 : val.get(i).entrySet()) {
//						sb.append(entry2.getValue().toString());
//					}
//				}
//			}
//			else {
//				sb.append(value);
//			}
//		}
////		System.out.println("sb : " + sb.toString());
//		
//		String temp = sb.toString();
//		String f48 = "";
//		String f60 = "";
//		String f61 = "";
//		ISOMsg output = isoMsg;
//		if (temp.length() >= 1000) {
//			f48 = temp.substring(0, 999);
//			temp = temp.substring(0, 999);
//			if (temp.length() >= 1000) {
//				f60 = temp.substring(0, 999);
//				temp = temp.substring(0, 999);
//				f61 = temp;
////				output.set(48, f48);
//				String noVa = "";
//				if (requestMap.get("PERIOD").toString().length() > 0) {
//					noVa = requestMap.get("SUBSCRIBER_NUMBER").toString() + requestMap.get("PERIOD").toString();
//					output.set(48, noVa+f48.substring(18));
//				}
//				else {
//					noVa = requestMap.get("SUBSCRIBER_NUMBER").toString();
//					output.set(48, noVa+f48.substring(18));
//				}
//				
//				output.set(60, f60);
//				output.set(61, f61);
//			}
//			else {
//				f60 = temp;
////				output.set(48, f48);
//				String noVa = "";
//				if (requestMap.get("PERIOD").toString().length() > 0) {
//					noVa = requestMap.get("SUBSCRIBER_NUMBER").toString() + requestMap.get("PERIOD").toString();
//					output.set(48, noVa+f48.substring(18));
//				}
//				else {
//					noVa = requestMap.get("SUBSCRIBER_NUMBER").toString();
//					output.set(48, noVa+f48.substring(18));
//				}
//				output.set(60, f60);
//			}
//		}
//		else {
//			f48 = temp;
////			output.set(48, f48);
//			String noVa = "";
//			if (requestMap.get("PERIOD").toString().length() > 0) {
//				noVa = requestMap.get("SUBSCRIBER_NUMBER").toString() + requestMap.get("PERIOD").toString();
//				output.set(48, noVa+f48.substring(18));
//			}
//			else {
//				noVa = requestMap.get("SUBSCRIBER_NUMBER").toString();
//				output.set(48, noVa+f48.substring(18));
//			}
//		}
//		return output;
//	}
//	//'{\"No_VA_Keluarga\":\"8888801835277671\",\"Jumlah_Bulan\":\"11\",\"No_VA_Kepala_Keluarga\":\"0001835277671   \",\"Total_Premi\":\"000001963500\",\"Biaya_Admin\":\"00002500\",\"Jumlah_anggota_keluarga\":\"03\",\"List_data_anggota_keluarga\":[{\"Kode_premi_anggota_keluarga\":\"1892575         \",\"No_Va_anggota_keluarga\":\"8888801835277671\",\"Nama\":\"MEIRA                         \",\"kdCabang\":\"0901  \",\"nmCabang\":\"JAKARTA PUSAT       \",\"Biaya_Premi_yang_harus_dibayar\":\"000000654500\",\"Biaya_Premi_bulan_ini\":\"000000059500\",\"Premi_di_muka\":\"000000595000\"},{\"Kode_premi_anggota_keluarga\":\"1892576         \",\"No_Va_anggota_keluarga\":\"8888801835277682\",\"Nama\":\"JOKO                          \",\"kdCabang\":\"0901  \",\"nmCabang\":\"JAKARTA PUSAT       \",\"Biaya_Premi_yang_harus_dibayar\":\"000000654500\",\"Biaya_Premi_bulan_ini\":\"000000059500\",\"Premi_di_muka\":\"000000595000\"},{\"Kode_premi_anggota_keluarga\":\"1892577         \",\"No_Va_anggota_keluarga\":\"8888801835277693\",\"Nama\":\"ANEE                          \",\"kdCabang\":\"0901  \",\"nmCabang\":\"JAKARTA PUSAT       \",\"Biaya_Premi_yang_harus_dibayar\":\"000000654500\",\"Biaya_Premi_bulan_ini\":\"000000059500\",\"Premi_di_muka\":\"000000595000\"}]}'
//	
////	public static void main (String[] args) throws JsonParseException, JsonMappingException, IOException {
////		String billerRealResponse = "{\"No_VA_Keluarga\":\"8888801835277671\",\"Jumlah_Bulan\":\"11\",\"No_VA_Kepala_Keluarga\":\"0001835277671   \",\"Total_Premi\":\"000001963500\",\"Biaya_Admin\":\"00002500\",\"Jumlah_anggota_keluarga\":\"03\",\"List_data_anggota_keluarga\":[{\"Kode_premi_anggota_keluarga\":\"1892575         \",\"No_Va_anggota_keluarga\":\"8888801835277671\",\"Nama\":\"MEIRA                         \",\"kdCabang\":\"0901  \",\"nmCabang\":\"JAKARTA PUSAT       \",\"Biaya_Premi_yang_harus_dibayar\":\"000000654500\",\"Biaya_Premi_bulan_ini\":\"000000059500\",\"Premi_di_muka\":\"000000595000\"},{\"Kode_premi_anggota_keluarga\":\"1892576         \",\"No_Va_anggota_keluarga\":\"8888801835277682\",\"Nama\":\"JOKO                          \",\"kdCabang\":\"0901  \",\"nmCabang\":\"JAKARTA PUSAT       \",\"Biaya_Premi_yang_harus_dibayar\":\"000000654500\",\"Biaya_Premi_bulan_ini\":\"000000059500\",\"Premi_di_muka\":\"000000595000\"},{\"Kode_premi_anggota_keluarga\":\"1892577         \",\"No_Va_anggota_keluarga\":\"8888801835277693\",\"Nama\":\"ANEE                          \",\"kdCabang\":\"0901  \",\"nmCabang\":\"JAKARTA PUSAT       \",\"Biaya_Premi_yang_harus_dibayar\":\"000000654500\",\"Biaya_Premi_bulan_ini\":\"000000059500\",\"Premi_di_muka\":\"000000595000\"}]}";
////		ISOMsg isoMsg = null;
////		Field486061Builder.build(isoMsg, billerRealResponse);
////	}
//}
