package com.jatismobile.plugin.iso8583.jatelindo;

public class StringCutter {

	public static String getCut(String input, int length) {
		String output = input.substring(0, length);
		return output;
	}

	public static String getRemainingString(String input, int length) {
		String output = input.substring(length);
		return output ;
	}

	public static void main (String[] args) {
		int length = 16;
		String input = "0000000062155293010000062155293   00000002550000002500011891288         8888800062155293RIZAL AN NAHL                 1005  SUKABUMI            000000025500000000025500000000000000";
		System.out.println(StringCutter.getCut(input, length));
		System.out.println(StringCutter.getRemainingString(input, length));
	}
}
