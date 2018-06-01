package com.jatismobile.plugin.iso8583.utils;

import java.util.Random;

public class STANStringGenerator {
	public static String getSTANString (int length) {
		char[] chars = "0123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

	public static void main (String[] args) {
		System.out.println(getSTANString(6));
	}
}
