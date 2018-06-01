package com.jatismobile.plugin.iso8583.jatelindo;

import org.apache.commons.lang3.StringUtils;

public class Field63Builder {
	public static String getField(String kodeLoket, String namaLoket, String alamatLoket, String noTelp, String kodeKab) {
		String output = "";

        String kode_loket = justifyLeft(kodeLoket, 32, " ");
        String nama_loket = justifyLeft(namaLoket, 30, " ");
        String alamat_loket = justifyLeft(alamatLoket, 50, " ");
        String no_telp = justifyLeft(noTelp, 18, " ");
        String kode_kab = justifyLeft(kodeKab, 4, " ");
        output = kode_loket+nama_loket+alamat_loket+no_telp+kode_kab;
		
		return output;
	}
	
	public static String getField() {
		String output = getField(
				"JTSMOBILE",
				"INFORMASI TEKNOLOGI INDONESIA",
				"Jl. Mampang Prapatan No.3 Jakarta 12790",
				"0217940946",
				"3174");
		return output;
	}

	private static String justifyLeft(String input, int length, String filler) {
		String output = "";
		if (input.length() == length) {
			output = input;
		}
		else if (input.length() < length) {
			output = StringUtils.rightPad(input, length, " ");
		}
		else if (input.length() > length) {
			output = input.substring(0, length);
		}
		else if (input.length() == 0) {
			output = input;
		}
		return output;
	}

	public static void main (String[] a) {
		System.out.println("asdfasdf : " + Field63Builder.getField(
				"JTSMOBILE",
				"INFORMASI TEKNOLOGI INDONESIA",
				"Jl. Mampang Prapatan No.3 Jakarta 12790",
				"0217940946",
				"3174"));
	}
}
