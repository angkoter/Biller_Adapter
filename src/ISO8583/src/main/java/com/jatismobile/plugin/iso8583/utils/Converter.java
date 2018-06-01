package com.jatismobile.plugin.iso8583.utils;

import java.lang.StringBuilder;
import java.util.Base64;

import org.bouncycastle.util.encoders.HexTranslator;

public class Converter {
	private static final int sizeOfIntInHalfBytes = 8;
	private static final int numberOfBitsInAHalfByte = 4;
	private static final int halfByte = 0x0F;
	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	public static String decToHex(int dec) {
		StringBuilder hexBuilder = new StringBuilder(sizeOfIntInHalfBytes);
		hexBuilder.setLength(sizeOfIntInHalfBytes);
		for (int i = sizeOfIntInHalfBytes - 1; i >= 0; --i) {
			int j = dec & halfByte;
			hexBuilder.setCharAt(i, hexDigits[j]);
			dec >>= numberOfBitsInAHalfByte;
		}
//		System.out.println("hex : " + hexBuilder.toString());
		return hexBuilder.toString();
	}

	public static String convertHexToString(String hex) {

		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {

			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);

			temp.append(decimal);
		}
//		System.out.println("Decimal : " + sb.toString());

		return sb.toString();
	}
	
	public static String hexToAscii (String s) {
//	    String hex = "75546f7272656e745c436f6d706c657465645c6e667375635f6f73745f62795f6d757374616e675c50656e64756c756d2d392c303030204d696c65732e6d7033006d7033006d7033004472756d202620426173730050656e64756c756d00496e2053696c69636f00496e2053696c69636f2a3b2a0050656e64756c756d0050656e64756c756d496e2053696c69636f303038004472756d2026204261737350656e64756c756d496e2053696c69636f30303800392c303030204d696c6573203c4d757374616e673e50656e64756c756d496e2053696c69636f3030380050656e64756c756d50656e64756c756d496e2053696c69636f303038004d50330000";
	    String hex = s;
	    StringBuilder output = new StringBuilder();
	    for (int i = 0; i < hex.length(); i+=2) {
	        String str = hex.substring(i, i+2);
	        output.append((char)Integer.parseInt(str, 16));
	    }
//	    System.out.println(output.toString());
		return null;
	}

	public static String decToHexToAscii(int dec) {
		String ascii = convertHexToString(decToHex(dec));
		return ascii;
	}
	
	public static byte[] hexToString(String hex) {
	    // hexToString that works at a byte level, not a character level
	    byte[] output = new byte[(hex.length() + 1) / 2];
	    for (int i = hex.length() - 1; i >= 0; i -= 2) {
	        int from = i - 1;
	        if (from < 0) {
	            from = 0;
	        }
	        String str = hex.substring(from, i + 1);
	        output[i/2] = (byte)Integer.parseInt(str, 16);
	    }
	    return output;
	}
}