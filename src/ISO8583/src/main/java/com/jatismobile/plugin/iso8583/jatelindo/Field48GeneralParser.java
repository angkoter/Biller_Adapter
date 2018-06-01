package com.jatismobile.plugin.iso8583.jatelindo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jpos.iso.ISOMsg;

public class Field48GeneralParser {

	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> parse(String format, ObjectMapper om, String field48String) {

		LinkedHashMap<String, Object> mapFormat = null;
		try {
			mapFormat = om.readValue(format, LinkedHashMap.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		LinkedHashMap<String, Object> output = new LinkedHashMap<String, Object>();

		HashMap<String, Object> items = (HashMap<String, Object>) mapFormat.get("48");

		String[] temp1 = items.get("ITEM").toString().split("\\|");

		for (int i = 0; i < temp1.length; i++) {
			String[] temp2 = temp1[i].split(":");
			if (temp2[0].equals("LIST")) {
				HashMap<String, Object> mapList = (HashMap<String, Object>) items.get(temp2[1]);
				int length = getTotalUsed(output, mapList);
				String val = StringCutter.getCut(field48String, length);
				field48String = StringCutter.getRemainingString(field48String, length);
				List<HashMap<String, Object>> list = listHandler(temp2[1], output, mapList, val);
				output.put(temp2[1], list);
			} else {
				int length = Integer.valueOf(temp2[1]);
				String val = StringCutter.getCut(field48String, length);
				field48String = StringCutter.getRemainingString(field48String, length);
				output.put(temp2[0], val);
			}			
		}

		return output;
	}

	public static int getTotalUsed(HashMap<String, Object> mapItem, HashMap<String, Object> mapList) {
		int size = Integer.valueOf(mapItem.get(mapList.get("SIZE").toString()).toString());
		String[] temp1 = mapList.get("ITEM").toString().split("\\|");
		int output = 0;
		for (int j = 0; j < temp1.length; j++) {
			String[] temp2 = temp1[j].split(":");
			output = output + Integer.valueOf(temp2[1]);
		}
		return (size * output);
	}

	public static List<HashMap<String, Object>> listHandler(String listName, HashMap<String, Object> mapItem,
			HashMap<String, Object> mapList, String field48String) {
		int size = Integer.valueOf(mapItem.get(mapList.get("SIZE").toString()).toString());
		String[] temp1 = mapList.get("ITEM").toString().split("\\|");
		List<HashMap<String, Object>> output = new ArrayList<HashMap<String, Object>>(size);
		LinkedHashMap<String, Object> familyHeadTemplate = null;
		for (int i = 0; i < size; i++) {
			familyHeadTemplate = new LinkedHashMap<String, Object>();
			for (int j = 0; j < temp1.length; j++) {
				String[] temp2 = temp1[j].split(":");
				if (temp2[0].equals("LIST")) {
					// int length = Integer.valueOf(temp2[1]);
					// String val = StringCutter.getCut(field48String, length);
					// field48String = StringCutter.getRemainingString(field48String, length);
					// listHandler(temp2[1], mapList, (HashMap<String, Object>)
					// mapList.get(temp2[1]), val);
				} else {
					int length = Integer.valueOf(temp2[1]);
					String val = StringCutter.getCut(field48String, length);
					field48String = StringCutter.getRemainingString(field48String, length);
					familyHeadTemplate.put(temp2[0], val);
				}
			}
			output.add(familyHeadTemplate);
		}
		return output;
	}

	public static String getFamilyNameList(String format, ObjectMapper om, String field48String) {

		LinkedHashMap<String, Object> mapFormat = null;
		try {
			mapFormat = om.readValue(format, LinkedHashMap.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		LinkedHashMap<String, Object> toutput = new LinkedHashMap<String, Object>();

		HashMap<String, Object> items = (HashMap<String, Object>) mapFormat.get("48");

		String[] temp1 = items.get("ITEM").toString().split("\\|");
		String output = "";

		for (int i = 0; i < temp1.length; i++) {
			String[] temp2 = temp1[i].split(":");
			if (temp2[0].equals("LIST")) {
				HashMap<String, Object> mapList = (HashMap<String, Object>) items.get(temp2[1]);
				int length = getTotalUsed(toutput, mapList);
				String val = StringCutter.getCut(field48String, length);
				field48String = StringCutter.getRemainingString(field48String, length);
				output = listHandlerName(temp2[1], toutput, mapList, val);
			} else {
				int length = Integer.valueOf(temp2[1]);
				String val = StringCutter.getCut(field48String, length);
				field48String = StringCutter.getRemainingString(field48String, length);
				toutput.put(temp2[0], val);
			}
		}
		return output;
	}

	private static String listHandlerName(String listName, HashMap<String, Object> mapItem,
			HashMap<String, Object> mapList, String field48String) {

		int size = Integer.valueOf(mapItem.get(mapList.get("SIZE").toString()).toString());
		String[] temp1 = mapList.get("ITEM").toString().split("\\|");
		String output = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < temp1.length; j++) {
				String[] temp2 = temp1[j].split(":");
				if (temp2[0].equals("LIST")) {

				} else {
					int length = Integer.valueOf(temp2[1]);
					String val = StringCutter.getCut(field48String, length);
					field48String = StringCutter.getRemainingString(field48String, length);
					if (temp2[0].equals("Nama")) {
						output = output + val + ", ";
					}
				}
			}
		}
		output = output.substring(0, output.length() - 2);
		return output;
	}

	public static String getFamilyHeadName(String field48String) {
		String familyMemberString = getFamilyMemberString(field48String);
		String output = familyMemberString.substring(32, 62);
		return output;
	}

	public static String getSubscriberName(ObjectMapper om, String format, HashMap<String, Object> field486061) {

		LinkedHashMap<String, Object> mapFormat = null;
		try {
			mapFormat = om.readValue(format, LinkedHashMap.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String items = mapFormat.get("SUBSCRIBER_NAME").toString();

		String[] temp1 = items.split("\\|");

		String subscriberName = "";
		if (temp1.length > 1) {
			if (temp1[0].equalsIgnoreCase("LIST")) {
				List<HashMap<String, Object>> item2 = (List<HashMap<String, Object>>) field486061.get(temp1[1]);
				for (int i = 0; i < item2.size(); i++) {
					String tempName = item2.get(i).get(temp1[2]).toString();
					subscriberName = subscriberName + tempName + ", ";
				}
			}
		} else {
			subscriberName = field486061.get(temp1[0]).toString();
		}

		return subscriberName;
	}
	
	public static String getAdminFee(ObjectMapper om, String format, HashMap<String, Object> field486061) {

		LinkedHashMap<String, Object> mapFormat = null;
		try {
			mapFormat = om.readValue(format, LinkedHashMap.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String adminFee = "";
		String items = mapFormat.get("ADMIN_FEE").toString();
		if (!items.equals("")) {
			String[] temp1 = items.split("\\|");
			if (temp1.length > 1) {
				if (temp1[0].equalsIgnoreCase("LIST")) {
					List<HashMap<String, Object>> item2 = (List<HashMap<String, Object>>) field486061.get(temp1[1]);
					for (int i = 0; i < item2.size(); i++) {
						String tempName = item2.get(i).get(temp1[2]).toString();
						adminFee = adminFee + tempName;
					}
				}
			} else {
				adminFee = field486061.get(temp1[0]).toString();
			}
		}
		else {
			adminFee = "0";
		}

		return adminFee;
	}
	
	public static String getTrxAmount(ObjectMapper om, String format, HashMap<String, Object> field486061) {

		LinkedHashMap<String, Object> mapFormat = null;
		try {
			mapFormat = om.readValue(format, LinkedHashMap.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String items = mapFormat.get("TRX_AMOUNT").toString();
		
		String trxAmount = "";
		if (!items.equals("")) {
			String[] temp1 = items.split("\\|");
			double tempTrx = 0.0;
			if (temp1.length > 1) {
				if (temp1[0].equalsIgnoreCase("MAPDEF")) {
					String[] item2 = temp1[1].split("\\:");
					for (int i = 0; i < item2.length; i++) {
						double tempT = 0.0;
						try {
							tempT = Double.valueOf(field486061.get(item2[i]).toString());
						} catch (Exception e) {
	//						
						}
						tempTrx = tempTrx + tempT;
					}
				}
				else if (temp1[0].equalsIgnoreCase("MAP")) {
					String key = field486061.get(temp1[1]).toString();
					String value = field486061.get(temp1[2]+key).toString();
					trxAmount = value;
				}
			} else {
				trxAmount = field486061.get(temp1[0]).toString();
			}
			
			if (!(tempTrx+"").equals("0.0")) {
				trxAmount = tempTrx + "";
			}
		}
		else {
			trxAmount = "0";
		}
		
		return trxAmount;
	}
	
	public static String getPurchaseTime(ObjectMapper om, String format, HashMap<String, Object> field486061, ISOMsg response) {

		LinkedHashMap<String, Object> mapFormat = null;
		try {
			mapFormat = om.readValue(format, LinkedHashMap.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String purchaseTime = "";
		String items = mapFormat.get("PURCHASE_TIME").toString();
		
		if (!items.equals("")) {
			String[] temp1 = items.split("\\|");
			if (temp1.length > 1) {
				if (temp1[0].equalsIgnoreCase("LIST")) {
					List<HashMap<String, Object>> item2 = (List<HashMap<String, Object>>) field486061.get(temp1[1]);
					for (int i = 0; i < item2.size(); i++) {
						String tempName = item2.get(i).get(temp1[2]).toString();
						purchaseTime = purchaseTime + tempName;
					}
				}
				else if (temp1[0].equalsIgnoreCase("MAP")) {
					String key = field486061.get(temp1[1]).toString();
					String value = field486061.get(temp1[2]+key).toString();
					purchaseTime = value;
				}
			}
			else {
				purchaseTime = field486061.get(temp1[0]).toString();
			}
	
			return purchaseTime;
		}
		else {
//			purchaseTime = response.getString(37);
			purchaseTime = "";
			return purchaseTime;
		}
	}

	public static String getFamilyMemberString(String field48String) {
		String output = field48String.substring(56);
		return output;
	}

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		// satu anggota
		// String field48String = "0000000062155293010000062155293
		// 00000002550000002500011891288 8888800062155293RIZAL AN NAHL 1005 SUKABUMI
		// 000000025500000000025500000000000000";

		// banyak anggota
//		String field48String = "8888801835277671110001835277671   00000196350000002500031892575         8888801835277671MEIRA                         0901  JAKARTA PUSAT       0000006545000000000595000000005950001892576         8888801835277682JOKO                          0901  JAKARTA PUSAT       0000006545000000000595000000005950001892577         8888801835277693ANEE                          0901  JAKARTA PUSAT       000000654500000000059500000000595000";
//		String field48String = "JTL53L31423456789551444444444401B0D8BDFAB419956087B74AD75E6FF080MUP210Z5575FD5259742D54C50F001100112233INDAH PUTRI              I3  000054321020000000000200000000002000010204120000099979200000989692000009699011200000100002346128011423456789520171215113705";

		String field48String = "8888801231231231010001731156535   000000240000000025000330402919        8888801731156535ASRUL ABDILLAH ALI            1011  BEKASI              00000008000000000000000000000008000030402920        8888801731156546RINI RISNAWATI                1011  BEKASI              00000008000000000000000000000008000030402921        8888801731156557AHMAD ZAKI RISADI             1011  BEKASI              000000080000000000000000000000080000";
		
//		String format = "{  \r\n" + 
//				"   \"48\":{  \r\n" + 
//				"      \"ITEM\":\"Product_ID:4|Kode_Area:4|Phone_Number:10|Customer_Name:30|No_Referensi_Switching:32|Total_Bill:1|Biaya_Admin:8|Expire:8|Bill_Ref_#1:11|Bill_Amount_#1:12|Bill_Ref_#2:11|Bill_Amount_#2:12|Bill_Ref_#3:11|Bill_Amount_#3:12\"\r\n" + 
//				"   },\r\n" + 
//				"   \"SUBSCRIBER_NAME\":\"Customer_Name\",\r\n" + 
//				"   \"ADMIN_FEE\":\"Biaya_Admin\",\r\n" + 
//				"   \"TRX_AMOUNT\":\"MAPDEF|Bill_Amount_#1:Bill_Amount_#2:Bill_Amount_#3\"\r\n" + 
//				"}";
		
//		String format = "{  \r\n" + 
//				"   \"48\":{  \r\n" + 
//				"      \"ITEM\":\"Switcher_ID:7|Meter_Id:11|Idpel:12|Flag:1|Trx_id:32|Jatelindo_Reference_Number:32|Vending_receipt_Number:8|Nama_Pelanggan:25|Tarif:4|Kategori_Pengunaan_Daya:9|Pilihan_Pembelian:1|Nilai_Minor_Biaya_Admin:1|Biaya Admin:10|Nilai_Minor_Biaya_Materai:1|Biaya_Materai:10|Nilai_Minor_Rupiah_PPN:1|Rupiah_PPN:10|Nilai_Minor_Rupiah_PPJU:1|Rupiah_PPJU:10|Nilai_Minor_Biaya_Angsuran:1|Rupiah_Angsuran:10|Nial_Minor_Pembelian_Listrik:1|Pembelian_Listrik:12|Nilai_Minor_Pembelian_KWH_Unit:1|Pembelian_Jumlah_KWH:10|Token_Number:20\"\r\n" + 
//				"   },\r\n" + 
//				"   \"ResponseParsingMaterial\":\"48\",\r\n" + 
//				"   \"PURCHASE_TIME\":\"\"\r\n" + 
//				"}";
		
		String format = "{  \r\n" + 
				"   \"48\":{\r\n" + 
				"      \"ITEM\":\"No_VA_Keluarga:16|Jumlah_Bulan:2|No_VA_Kepala_Keluarga:16|Total_Premi:12|Biaya_Admin:8|Jumlah_anggota_keluarga:2|LIST:List_anggota_keluarga\",\r\n" + 
				"      \"List_anggota_keluarga\":{  \r\n" + 
				"         \"SIZE\":\"Jumlah_anggota_keluarga\",\r\n" + 
				"         \"ITEM\":\"Kode_premi_anggota_keluarga:16|No_Va_anggota_keluarga:16|Nama:30|kdCabang:6|nmCabang:20|Biaya_Premi_yang_harus_dibayar:12|Biaya_Premi_bulan_ini:12|Premi_di_muka:12\"\r\n" + 
				"      }\r\n" + 
				"   },\r\n" + 
				"   \"SUBSCRIBER_NAME\":\"LIST|List_anggota_keluarga|Nama\",\r\n" + 
				"   \"ADMIN_FEE\":\"Biaya_Admin\",\r\n" + 
				"   \"TRX_AMOUNT\":\"Total_Premi\"\r\n" + 
				"}";

		ObjectMapper om = new ObjectMapper();
		HashMap<String, Object> map = parse(format.replace("\\", ""), om, field48String);
		System.out.println("format : " + format.replace("\\", ""));
		System.out.println(map);
		System.out.println(om.writeValueAsString(map));
//		System.out.println(getFamilyNameList(format.replace("\\", ""), om, field48String));
//		System.out.println(getFamilyHeadName(field48String));
		HashMap<String, Object> field486061 = new HashMap<>();
//		field486061.put("Total_Bill", "3");
//		field486061.put("Bill_Amount_#1", "110000");
//		field486061.put("Bill_Amount_#2", "120000");
//		field486061.put("Biil_Amount#3", "120000");
//		System.out.println(getTrxAmount(om, format, field486061));
	}

	public static LinkedHashMap<String, Object> getDenomList(HashMap<String, Object> mFormat,
			HashMap<String, Object> field486061) {
		
//		LinkedHashMap<String, Object> output = new LinkedHashMap<String, Object>();
//		
//		for (HashMap.Entry<String, Object> entry : field486061.entrySet()) {
//			
//		}
		
		return null;
	}
}