package com.jatismobile.plugin.iso8583.jatelindo;

import org.apache.commons.lang3.StringUtils;

public class VAValidator {
	
	private static String getValidatedVA (String va) {
		return StringUtils.leftPad(va.substring(va.length()-11, va.length()), 16, "8");
	}
	
//	public static boolean validateLength (String input) {
//		if (input.length() >= 13 && input.length() < 17) {
//			return true;
//		}
//		else {
//			return false;
//		}
//	}
	
	public static String validatePeriod (String period) {
		if (period.length() == 0) {	
			return "invalid";
		}
		else {
			return period;
		}
	}
	
	private static boolean validatePrefixAndLength (String va) {
		if (va.startsWith("88888") && va.length() == 16) {
			return true;
		}
		else if (va.startsWith("000") && va.length() == 13) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static String process (String va) {
		if (validatePrefixAndLength(va)) {
			return getValidatedVA(va);
		}
		else {
			return "invalid";
		}
	}
	
	public static void main (String[] a) {
		String ss = VAValidator.getValidatedVA("8888801835277671");
		if (ss != null) {
			System.out.println(ss + ", : lenghth : " + ss.length());
		}
		else {
			System.out.println("Panjang No Va tidak valid");
		}
		System.out.println(validatePeriod(""));
		System.out.println();
		System.out.println(process("0002215407677"));
	}
}
