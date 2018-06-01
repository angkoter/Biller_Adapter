package com.glonation.biller.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class ChecksumGen {
	
	public String getChecksum (String input, String algorithm) {
		String string = input;
		String checksum = null;
		if (algorithm.equals("SHA1")) {
			checksum = getSHA1(string);
		}
		else if (algorithm.equals("MD5")) {
			checksum = getMD5(string);
		}
		else if (algorithm.equals("SHA256")) {
			checksum = getSHA256(string);
		}
		return checksum;
	}
	
	private String getSHA256(String string){
		String sha256 = DigestUtils.sha256Hex(string);
		return sha256;
	}
	private String getMD5 (String string) {
		String md5 = DigestUtils.md5Hex(string);
		return md5;
	}
	
	@SuppressWarnings("deprecation")
	private String getSHA1 (String string) {
		String sha1 = DigestUtils.shaHex(string);
		return sha1;
	}
	
	private static String getSHAv2 (String string) {
		String sha1 = DigestUtils.sha1Hex(string);
		return sha1;
	}
}
