package com.jatismobile.plugin.iso8583.jatelindo;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Field48Builder {
	public static String build (HashMap<String, Object> mapFormat, HashMap<String, Object> request) {
		
//		ObjectMapper om = new ObjectMapper();
//		
//		LinkedHashMap<String, Object> mapFormat = null;
//		try {
//			mapFormat = om.readValue(format, LinkedHashMap.class);
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		HashMap<String, Object> items = (HashMap<String, Object>) mapFormat.get("48");
		
		List<HashMap<String, Object>> list = (List<HashMap<String, Object>>)items.get("ITEM");
		
		String output = "";
		
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> object = list.get(i);
			boolean isPhoneNumber = (boolean) object.get("IS_PHONE_NUMBER");
			
			if (isPhoneNumber) {
				output = output + phoneNumberTelco(object, request);
			}
			else {
				if (object.containsKey("LENGTH")) {
					output = output + notPhoneNumberTelcoItem(object, request);
				}
				else {
					output = output + notPhoneNumber(object, request);
				}
			}
		}

		return output;
	}
	
	public static String build (String format, HashMap<String, Object> request) {
		
		ObjectMapper om = new ObjectMapper();
		
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
		
		HashMap<String, Object> items = (HashMap<String, Object>) mapFormat.get("48");
		
		List<HashMap<String, Object>> list = (List<HashMap<String, Object>>)items.get("ITEM");
		
		String output = "";
		
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> object = list.get(i);
			boolean isPhoneNumber = (boolean) object.get("IS_PHONE_NUMBER");
			
			if (isPhoneNumber) {
				output = output + phoneNumberTelco(object, request);
			}
			else {
				if (object.containsKey("LENGTH")) {
					output = output + notPhoneNumberTelcoItem(object, request);
				}
				else {
					output = output + notPhoneNumber(object, request);
				}
			}
		}

		return output;
	}
	
	public static String notPhoneNumber (HashMap<String, Object> object, HashMap<String, Object> request) {
		String output = "";
		String value = object.get("VALUE").toString();
		String[] temp = value.split("\\|");
		if (temp.length > 1) {
			for (int i = 0; i < temp.length; i++) {
				if (request.containsKey(temp[i])) {
					if (temp[i].equals("SUBSCRIBER_NUMBER")) {
						String subsriberNumber = request.get(temp[i]).toString();
						output = output + VAValidator.process(subsriberNumber);
					}
					else {
						output = output + request.get(temp[i]).toString();
					}
				}
				else {
					if (temp[i].startsWith("comparison_")) {
						String[] operatorAndOperand = temp[i].split("_");
						if (operatorAndOperand[1].equals(">")) {
							int operand1 = Integer.valueOf(output);
							int operand2 = Integer.valueOf(operatorAndOperand[2]);//operand reference
							int operand3 = Integer.valueOf(operatorAndOperand[3]);//res if true
							int operand4 = Integer.valueOf(operatorAndOperand[5]);//res if false
							if (operand1 > operand2) {
								output = operand3+"";
							}
							else {
								output = operand4+"";
							}
						}
						else if (operatorAndOperand[1].equals("<")) {
							int operand1 = Integer.valueOf(output);
							int operand2 = Integer.valueOf(operatorAndOperand[2]);//operand reference
							int operand3 = Integer.valueOf(operatorAndOperand[3]);//res if true
							int operand4 = Integer.valueOf(operatorAndOperand[5]);//res if false
							if (operand1 < operand2) {
								output = operand3+"";
							}
							else {
								output = operand4+"";
							}
						}
						
					}
					else {
						output = output + temp[i];
					}
				}
			}
		}
		else {
			if (request.containsKey(temp[0])) {
				output = request.get(temp[0]).toString();
			}
			else {
				output = temp[0];
			}
		}
		return output;
	}
	
	public static String notPhoneNumberTelcoItem (HashMap<String, Object> object, HashMap<String, Object> request) {
		
		String output = "";
		String value = object.get("VALUE").toString();
		String[] temp = value.split("\\|");
		if (temp.length > 1) {
			for (int i = 0; i < temp.length; i++) {
				if (request.containsKey(temp[i])) {
					output = output + request.get(temp[i]).toString();
				}
				else {
					output = output + temp[i];
				}
			}
		}
		else {
			if (request.containsKey(temp[0])) {
				
				if (object.containsKey("PADDING_SELECTOR")) {
					String[] config = object.get("PADDING_SELECTOR").toString().split("\\|");
					if (config[1].equals("REAL_LENGTH")) {
						int valueToCompare = request.get(config[0]).toString().length();//=_11_0_LJ_0_=_12_1_RJ_0
						String[] operandAndOperator = config[2].split("_");
						String cond1_operator = operandAndOperator[0];//=
						String cond1_referer = operandAndOperator[1];//ex. 11
						String cond1_result = operandAndOperator[2];//ex. 0
						String padding1_Value = operandAndOperator[3];//ex. LJ
						String padder1_Value = operandAndOperator[4];//ex. 0
						
						String cond2_operator = operandAndOperator[5];//=
						String cond2_referer = operandAndOperator[6];//ex. 12
						String cond2_result = operandAndOperator[7];//ex. 1
						String padding2_Value = operandAndOperator[8];//ex. RJ
						String padder2_Value = operandAndOperator[9];//ex. 0
						if (cond1_operator.equals("=")) {
							if (valueToCompare == (Integer.valueOf(cond1_referer))) {
								String tinput = request.get(temp[0]).toString();
								
								if (padding1_Value.equals("LJ")) {
									padding1_Value = "LEFT_JUSTIFIED";
								}
								else if (padding1_Value.equals("RJ")) {
									padding1_Value = "RIGHT_JUSTIFIED";
								}
								
								if (request.containsKey(tinput)) {
									String input = request.get(tinput).toString();//ok
									int len = Integer.valueOf(object.get("LENGTH").toString());//ok
									String padding = padding1_Value;//ok
									String padder = padder1_Value;//ok
									output = fixer(input, len, padding, padder);//ok
									
								}
								else {
									String input = tinput;
									int len = Integer.valueOf(object.get("LENGTH").toString());//ok
									String padding = padding1_Value;//ok
									String padder = padder1_Value;//ok
									output = fixer(input, len, padding, padder);//ok
								}
								output = output + cond1_result;//ok
							}
						}
						
						if (cond2_operator.equals("=")) {
							if (valueToCompare == (Integer.valueOf(cond2_referer))) {
								String tinput = request.get(temp[0]).toString();
								
								if (padding2_Value.equals("LJ")) {
									padding2_Value = "LEFT_JUSTIFIED";
								}
								else if (padding2_Value.equals("RJ")) {
									padding2_Value = "RIGHT_JUSTIFIED";
								}
								
								if (request.containsKey(tinput)) {
									String input = request.get(tinput).toString();//ok
									int len = Integer.valueOf(object.get("LENGTH").toString());//ok
									String padding = padding2_Value;//ok
									String padder = padder2_Value;//ok
									output = fixer(input, len, padding, padder);//ok
									
								}
								else {
									String input = tinput;
									int len = Integer.valueOf(object.get("LENGTH").toString());//ok
									String padding = padding2_Value;//ok
									String padder = padder2_Value;//ok
									output = fixer(input, len, padding, padder);//ok
								}
								output = output + cond2_result;//ok
							}
						}
					}
					return output;
				}
				
				if (object.get("PADDING").toString().equals("FIXED")) {
					String tinput = request.get(temp[0]).toString();
					
					if (request.containsKey(tinput)) {
						String input = request.get(tinput).toString();
						int len = Integer.valueOf(object.get("LENGTH").toString());
						output = fixerString(input, len);
					}
					else {
						String input = tinput;
						int len = Integer.valueOf(object.get("LENGTH").toString());
						output = fixerString(input, len);
					}
				}
				else {
					String tinput = request.get(temp[0]).toString();
					
					if (request.containsKey(tinput)) {
						String input = request.get(tinput).toString();
						int len = Integer.valueOf(object.get("LENGTH").toString());
						String padding = object.get("PADDING").toString();
						String padder = object.get("PADDER").toString();
						output = fixer(input, len, padding, padder);
						
					}
					else {
						String input = tinput;
						int len = Integer.valueOf(object.get("LENGTH").toString());
						String padding = object.get("PADDING").toString();
						String padder = object.get("PADDER").toString();
						output = fixer(input, len, padding, padder);
					}
				}
				
				
			}
			else {
				if (object.get("PADDING").toString().equals("FIXED")) {
					String tinput = object.get("VALUE").toString();
					
					if (request.containsKey(tinput)) {
						String input = request.get(tinput).toString();
						int len = Integer.valueOf(object.get("LENGTH").toString());
						output = fixerString(input, len);
					}
					else {
						String input = tinput;
						int len = Integer.valueOf(object.get("LENGTH").toString());
						output = fixerString(input, len);
					}
				}
				else {
					String tinput = object.get("VALUE").toString();
					
					if (request.containsKey(tinput)) {
						String input = request.get(tinput).toString();
						int len = Integer.valueOf(object.get("LENGTH").toString());
						String padding = object.get("PADDING").toString();
						String padder = object.get("PADDER").toString();
						output = fixer(input, len, padding, padder);
						
					}
					else {
						String input = tinput;
						int len = Integer.valueOf(object.get("LENGTH").toString());
						String padding = object.get("PADDING").toString();
						String padder = object.get("PADDER").toString();
						output = fixer(input, len, padding, padder);
					}
				}
			}
			
			//here
			
		}
		return output;
	}
	
	public static String fixerString (String input, int len) {
		
		String output = "";
		if (input.length() == len) {
			output = input;
		}
		else if (input.length() > len) {
			output = input.substring(0, len);
		}
		else if (input.length() < len) {
			output = StringUtils.rightPad(input, len, " ");
		}

		return output;
	}
	
	public static String fixer (String input, int len, String padding, String padder) {
		
		if (padder.equalsIgnoreCase("SPACE")) {
			padder = " ";
		}
		
		String output = "";
		if (input.length() == len) {
			output = input;
		}
		else if (input.length() > len) {
			output = input.substring(0, len);
		}
		else if (input.length() < len) {
			if (padding.equalsIgnoreCase("RIGHT_JUSTIFIED")) {
				output = StringUtils.leftPad(input, len, padder);
			}
			else if (padding.equalsIgnoreCase("LEFT_JUSTIFIED")) {
				output = StringUtils.rightPad(input, len, padder);
			}
			else {
				output = fixerString(input, len);
			}
		}

		return output;
	}
	
	public static String phoneNumberTelco (HashMap<String, Object> object, HashMap<String, Object> request) {
		String output = "";
		String tinput = object.get("VALUE").toString();
		String length = object.get("LENGTH").toString();
		int len = Integer.valueOf(length);
		String getCutSide = object.get("CUT").toString();
		String padding = object.get("PADDING").toString();
		String padder = object.get("PADDER").toString();
		
		if (request.containsKey(tinput)) {
			String input = request.get(tinput).toString();
			if (getCutSide.equals("LEFT")) {
				if (input.startsWith("08")) {
					input = input.substring(0, 4);
					output = fixer(input, len, padding, padder);
				}
				else if (input.startsWith("02")) {
					input = input.substring(0, 3);
					output = fixer(input, len, padding, padder);
				}
			}
			else if (getCutSide.equals("RIGHT")) {
				if (input.startsWith("08")) {
					input = input.substring(4);
					output = fixer(input, len, padding, padder);
				}
				else if (input.startsWith("02")) {
					input = input.substring(3);
					output = fixer(input, len, padding, padder);
				}
			}
		}
		else {
			String input = tinput;
			if (getCutSide.equals("LEFT")) {
				if (input.startsWith("08")) {
					input = input.substring(0, len);
					output = fixer(input, len, padding, padder);
				}
				else if (input.startsWith("02")) {
					input = input.substring(0, 3);
					output = fixer(input, len, padding, padder);
				}
			}
			else if (getCutSide.equals("RIGHT")) {
				if (input.startsWith("08")) {
					input = input.substring(4);
					output = fixer(input, len, padding, padder);
				}
				else if (input.startsWith("02")) {
					input = input.substring(3);
					output = fixer(input, len, padding, padder);
				}
			}
		}
		
		return output;
	}
	
	public static void main (String[] args) throws JsonParseException, JsonMappingException, IOException {
		String ss = "";
		String[] tes = ss.split("\\|");
		System.out.println(tes.length);
		System.out.println(tes[0]);
		
		String format = "{  \r\n" + 
				"   \"0\":\"0200\",\r\n" + 
				"   \"2\":\"PRODUCT_CODE\",\r\n" + 
				"   \"3\":\"171000\",\r\n" + 
				"   \"7\":\"TRANSMISSION_DATE\",\r\n" + 
				"   \"11\":\"STAN\",\r\n" + 
				"   \"12\":\"LOCAL_TIME\",\r\n" + 
				"   \"13\":\"LOCAL_DATE\",\r\n" + 
				"   \"15\":\"SETTLEMENT_DATE\",\r\n" + 
				"   \"18\":\"6012\",\r\n" + 
				"   \"32\":\"008\",\r\n" + 
				"   \"37\":\"RANDOM_REFERENCE_NUMBER\",\r\n" + 
				"   \"41\":\"DEVJATIS\",\r\n" + 
				"   \"42\":\"200900100800000\",\r\n" + 
				"   \"48\":\r\n" + 
				"		{\r\n" + 
				"			\"ITEM\":\r\n" + 
				"				[\r\n" + 
				"					{\"VALUE\":\"JTL53L3\", \"LENGTH\":\"7\", \"IS_PHONE_NUMBER\":false, \"PADDING\":\"LEFT_JUSTIFIED\", \"PADDER\":\"0\"},\r\n" + 
				"					{\"VALUE\":\"SUBSCRIBER_NUMBER\", \"LENGTH\":\"11\", \"IS_PHONE_NUMBER\":false, \"PADDING\":\"LEFT_JUSTIFIED\", \"PADDER\":\"SPACE\"},\r\n" + 
				"					{\"VALUE\":\"000000000000\", \"LENGTH\":\"12\", \"IS_PHONE_NUMBER\":false, \"PADDING\":\"FIXED\"},\r\n" + 
				"					{\"VALUE\":\"0\", \"LENGTH\":\"1\", \"IS_PHONE_NUMBER\":false, \"PADDING\":\"FIXED\"},\r\n" + 
				"					{\"VALUE\":\"JPOS_INQUIRY_RESPONSE|48\", \"LENGTH\":\"31\", \"IS_PHONE_NUMBER\":false, \"PADDING\":\"FIXED\"},\r\n" + 
				"					{\"VALUE\":\"0\", \"LENGTH\":\"1\", \"IS_PHONE_NUMBER\":false, \"PADDING\":\"FIXED\"}\r\n" + 
				"				]\r\n" + 
				"		},\r\n" + 
				"   \"49\":\"360\",\r\n" + 
				"   \"62\":\"JPOS_INQUIRY_RESPONSE|62\",\r\n" + 
				"   \"63\":\"JTSMOBILE                       INFORMASI TEKNOLOGI INDONESIA Jl. Mampang Prapatan No.3 Jakarta 12790           0217940946        \"\r\n" + 
				"}";
		
//		String format = "{  \r\n" + 
//				"   \"0\":\"0200\",\r\n" + 
//				"   \"2\":\"PRODUCT_CODE\",\r\n" + 
//				"   \"3\":\"380000\",\r\n" + 
//				"   \"7\":\"TRANSMISSION_DATE\",\r\n" + 
//				"   \"11\":\"STAN\",\r\n" + 
//				"   \"12\":\"LOCAL_TIME\",\r\n" + 
//				"   \"13\":\"LOCAL_DATE\",\r\n" + 
//				"   \"15\":\"SETTLEMENT_DATE\",\r\n" + 
//				"   \"18\":\"6012\",\r\n" + 
//				"   \"32\":\"008\",\r\n" + 
//				"   \"37\":\"RANDOM_REFERENCE_NUMBER\",\r\n" + 
//				"   \"41\":\"       6\",\r\n" + 
//				"   \"42\":\"         654321\",\r\n" + 
//				"  \"48\":{  \r\n" + 
//				"	 \"ITEM\":[  \r\n" + 
//				"		{  \r\n" + 
//				"		   \"VALUE\":\"SUBSCRIBER_NUMBER|PERIOD\",\r\n" + 
//				"		   \"IS_PHONE_NUMBER\":false\r\n" + 
//				"		}\r\n" + 
//				"	 ]\r\n" + 
//				"  },\r\n" + 
//				"   \"49\":\"360\"\r\n" + 
//				"}";
		
//		String format = "{  \r\n" + 
//				"   \"0\":\"0200\",\r\n" + 
//				"   \"2\":\"PRODUCT_CODE\",\r\n" + 
//				"   \"3\":\"380000\",\r\n" + 
//				"   \"7\":\"TRANSMISSION_DATE\",\r\n" + 
//				"   \"11\":\"STAN\",\r\n" + 
//				"   \"12\":\"LOCAL_TIME\",\r\n" + 
//				"   \"13\":\"LOCAL_DATE\",\r\n" + 
//				"   \"15\":\"SETTLEMENT_DATE\",\r\n" + 
//				"   \"18\":\"6012\",\r\n" + 
//				"   \"32\":\"008\",\r\n" + 
//				"   \"37\":\"RANDOM_REFERENCE_NUMBER\",\r\n" + 
//				"   \"41\":\"DEVJATIS\",\r\n" + 
//				"   \"42\":\"200900100800000\",\r\n" + 
//				"	\"48\":\r\n" + 
//				"		{\r\n" + 
//				"			\"ITEM\":\r\n" + 
//				"				[\r\n" + 
//				"					{\"VALUE\":\"0001\", \"LENGTH\":\"4\", \"IS_PHONE_NUMBER\":false, \"PADDING\":\"FIXED\"},\r\n" + 
//				"					{\"VALUE\":\"SUBSCRIBER_NUMBER\", \"LENGTH\":\"4\", \"IS_PHONE_NUMBER\":true, \"CUT\":\"LEFT\", \"PADDING\":\"RIGHT_JUSTIFIED\", \"PADDER\":\"0\"},\r\n" + 
//				"					{\"VALUE\":\"SUBSCRIBER_NUMBER\", \"LENGTH\":\"10\", \"IS_PHONE_NUMBER\":true, \"CUT\":\"RIGHT\", \"PADDING\":\"LEFT_JUSTIFIED\", \"PADDER\":\"SPACE\"}\r\n" + 
//				"				]\r\n" + 
//				"		},\r\n" + 
//				"   \"49\":\"360\"\r\n" + 
//				"}";
		
		HashMap<String, Object> request = new HashMap<String, Object>();
		request.put("SUBSCRIBER_NUMBER", "777888");
//		request.put("SUBSCRIBER_NUMBER", "0001231231231");
//		request.put("PERIOD", "01");
		
//		request.put("SUBSCRIBER_NUMBER", "0233282145");
//		request.put("PERIOD", "01");
		
		System.out.println(format.replace("\\", ""));
		
		ObjectMapper om = new ObjectMapper();
		HashMap<String, Object> mapFormat = om.readValue(format.replace("\\", ""), HashMap.class);
		
		String inquiryField48 = build(mapFormat, request);
		
		System.out.println(inquiryField48);
		System.out.println(inquiryField48.length());
	}
}
