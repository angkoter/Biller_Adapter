//package com.jatismobile.plugin.iso8583.jatelindo;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//
//import org.codehaus.jackson.JsonGenerationException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
//
//public class Field48PurchaseParser {
//
//	public static HashMap<String, Object> getFamilyHeadTemplate() {
//		LinkedHashMap<String, Object> output = new LinkedHashMap<String, Object>();
//		output.put("No_VA_Keluarga", 16);
//		output.put("Jumlah_Bulan", 2);
//		output.put("No_VA_Kepala_Keluarga", 16);
//		output.put("Total_Premi", 12);
//		output.put("Biaya_Admin", 8);
//		output.put("JPA_Refnum", 32);
//		output.put("Jumlah_anggota_keluarga", 2);
//		return output;
//	}
//
//	public static HashMap<String, Object> getFamilyMemberTemplate() {
//		LinkedHashMap<String, Object> output = new LinkedHashMap<String, Object>();
//		output.put("Kode_premi_anggota_keluarga", 16);
//		output.put("No_Va_anggota_keluarga", 16);
//		output.put("Nama", 30);
//		output.put("kdCabang", 6);
//		output.put("nmCabang", 20);
//		output.put("Biaya_Premi_yang_harus_dibayar", 12);
//		output.put("Biaya_Premi_bulan_ini", 12);
//		output.put("Premi_di_muka", 12);
//		return output;
//	}
//
//	public static HashMap<String, Object> familyHeadMapper(String field48String) {
//		HashMap<String, Object> output = Field48PurchaseParser.getFamilyHeadTemplate();
//		for (HashMap.Entry<String, Object> entry : output.entrySet()) {
//			String key = entry.getKey();
//			Object value = entry.getValue();
//			int length = (int) value;
//			String val = StringCutter.getCut(field48String, length);
//			field48String = StringCutter.getRemainingString(field48String, length);
//			output.put(key, val);
////			System.out.println("key : " + entry.getKey() + ", value : " + entry.getValue());
//		}
//		return output;
//	}
//	
//	public static List<HashMap<String, Object>> familyMemberMapper(int totalFamilyMember, String familyMemberString) {
//		List<HashMap<String, Object>> output = new ArrayList<HashMap<String, Object>>();
////		String nameList = "";
////		System.out.println("fam fam : " + familyMemberString);
//		for (int i = 0; i < totalFamilyMember; i++) {
//			HashMap<String, Object> familyMember = Field48PurchaseParser.getFamilyMemberTemplate();
//			for (HashMap.Entry<String, Object> entry : familyMember.entrySet()) {
//				String key = entry.getKey();
//				Object value = entry.getValue();
////				System.out.println(key + " : " + value);
//				int length = (int) value;
//				String val = StringCutter.getCut(familyMemberString, length);
////				System.out.println("val : " +val);
//				familyMemberString = StringCutter.getRemainingString(familyMemberString, length);
//				familyMember.put(key, val);
//			}
//			output.add(familyMember);
//		}
//		return output;
//	}
//
//	public static String getFamilyHeadString(String field48String) {
//		String output = field48String.substring(0,88);
//		return output;
//	}
//	
//	public static String getFamilyMemberString(String field48String) {
//		String output = field48String.substring(88);
//		return output ;
//	}
//	
//	public static HashMap<String, Object> getParsedField(String field48String) {
//		HashMap<String, Object> output = familyHeadMapper(Field48PurchaseParser.getFamilyHeadString(field48String));
//		int totalFamilyMember = Integer.valueOf(output.get("Jumlah_anggota_keluarga").toString());
//		String familyMemberString = getFamilyMemberString(field48String);
//		List<HashMap<String, Object>> familyMember = familyMemberMapper(totalFamilyMember, familyMemberString);
//		output.put("Tanggal_Lunas", getTanggalLunas(totalFamilyMember, field48String));
//		output.put("List_data_anggota_keluarga", familyMember);
//		return output ;
//	}
//
//	public static String getFamilyNameList(String field48String) {
//		String output = "";
//		HashMap<String, Object> map = Field48PurchaseParser.familyHeadMapper(Field48PurchaseParser.getFamilyHeadString(field48String));
//		int totalFamilyMember = Integer.valueOf(map.get("Jumlah_anggota_keluarga").toString());
//		String familyMemberString = Field48PurchaseParser.getFamilyMemberString(field48String);
//		for (int i = 0; i < totalFamilyMember; i++) {
//			String val = StringCutter.getCut(familyMemberString, 124);
//			familyMemberString = StringCutter.getRemainingString(familyMemberString, 124);
//			output = output + val.substring(32,62) + ",";
////			System.out.println(i + " : " + val);
//		}
//		output = output.substring(0, output.length()-1);
//		return output;
//	}
//	
//	public static String getTanggalLunas(int totalFamilyMember, String field48String) {
//		String output = "";
//		String familyMemberString = Field48PurchaseParser.getFamilyMemberString(field48String);
//		for (int i = 0; i < totalFamilyMember; i++) {
//			familyMemberString = StringCutter.getRemainingString(familyMemberString, 124);
//		}
//		output = familyMemberString;
//		return output;
//	}
//	
//	public static String getFamilyHeadName(String field48String) {
//		String familyMemberString = Field48PurchaseParser.getFamilyMemberString(field48String);
//		String output = familyMemberString.substring(32,62);
//		return output;
//	}
//
//	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
//		//satu anggota
////		String field48String = "0000000062155293010000062155293   00000002550000002500011891288         8888800062155293RIZAL AN NAHL                 1005  SUKABUMI            000000025500000000025500000000000000";
//		
//		//banyak anggota
//		String field48String = "8888801835277671010001835277671   000000178500000025004BF1F49F51CD4214E85536A7231F0A46031892575         8888801835277671MEIRA                         0901  JAKARTA PUSAT       0000000595000000000595000000000000001892576         8888801835277682JOKO                          0901  JAKARTA PUSAT       0000000595000000000595000000000000001892577         8888801835277693ANEE                          0901  JAKARTA PUSAT       00000005950000000005950000000000000020170711160655";
//		
////		String field48String = "8888801089107471010001016581342   00000141900000002500211895795         8888801016581342SOIMAH                        1101  SEMARANG            0000000510000000000510000000000000001895813         8888801016590432RATMINI                       1101  SEMARANG            0000000800000000000800000000000000001895815         8888801016592614SALBIYAH                      1101  SEMARANG            0000000800000000000800000000000000001895814         8888801016592625KURNIASIH                     1101  SEMARANG            0000000800000000000800000000000000001895820         8888801016601298NASIKIN                       1101  SEMARANG            0000000510000000000510000000000000001895821         8888801016605012MERINA ANGGI R                1101  SEMARANG            0000000510000000000510000000000000001895479         8888801016607936WARSITI                       1101  SEMARANG            0000000595000000000595000000000000001895801         8888801016610502KUSTINAH                      1101  SEMARANG            0000000800000000000800000000000000001895811         8888801016617498YUNI ASTUTI                   1101  SEMARANG            0000000510000000000510000000000000001895799         8888801016634284RASIKEM                       1101  SEMARANG            000000080000000000080000000000000055 D B006016 A B006026 B B006036    006046 C B006056 D B007017 A B007027 B B007037    007047 C B007057 D B008018 A B008028 B B008038    008048 C B008058 D B009019 A B009029 B B009039    009049 C B009059 D B0100110A B0100210B B0100310   0100410C B0100510D B0110111A B0110211B B0110311   0110411C B0110511D B0120112A B0120212B B0120312   0120412C B0120512D B0130113A B0130213B B0130313   0130413C B0130513D B0140114A B0140214B B0140314   0140414C B0140514D B0150115A B0150215B B0150315   0150415C B0150515D B0160116A B0160216B B0160316   0160416C B0160516D B0170117A B0170217B B0170317   0170417C B0170517D B0180118A B0180218B B0180318   0180418C B0180518D B0190119A B0190219B B0190319   0190419999C B01905 B0200120A B0200220B B0200320   0200420C B0200520D B0BISBIS    0310001011 A B001021 B B001031    001041 C B001051 D B002012 A B002022 B B002032    002042 C B002052 D B003013 A B003023 B B003033    003043 C B003";
//		HashMap<String, Object> famHead = Field48PurchaseParser.familyHeadMapper(Field48PurchaseParser.getFamilyHeadString(field48String));
//		for (HashMap.Entry<String, Object> entry : famHead.entrySet()) {
//			System.out.println(entry.getKey() + " : " + entry.getValue());
//		}
//		System.out.println("===Family Member===");
//		int totalFamilyMember = Integer.valueOf(famHead.get("Jumlah_anggota_keluarga").toString());
//		
//		String familyMemberString = Field48PurchaseParser.getFamilyMemberString(field48String);
//		List<HashMap<String, Object>> familyMember = Field48PurchaseParser.familyMemberMapper(totalFamilyMember, familyMemberString);
////		HashMap<String, Object> nameList = familyMember.get(totalFamilyMember);
////		System.out.println("this is nameList : " + nameList);
////		familyMember.remove(totalFamilyMember);
//		for (int i = 0; i < totalFamilyMember ; i++) {
//			System.out.println("family member : " + familyMember.get(i));
//		}
////		nameList = familyMember.get(totalFamilyMember-1);
////		System.out.println("this is nameList2 : " + nameList);
//		famHead.put("List_data_anggota_keluarga", familyMember);
//		ObjectMapper om = new ObjectMapper();
//		String billerRealResponseJson = om.writeValueAsString(famHead);
//		System.out.println("BILLER_REAL_RESPONSE");
//		System.out.println(billerRealResponseJson);
//		System.out.println("out : " + Field48PurchaseParser.getFamilyNameList(field48String));
//		System.out.println("tanggal lunas : " + getTanggalLunas(totalFamilyMember, field48String));
//	}
//}
