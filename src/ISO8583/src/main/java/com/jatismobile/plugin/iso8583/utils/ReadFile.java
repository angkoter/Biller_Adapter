package com.jatismobile.plugin.iso8583.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFile {
	public static String read() {
		String read = "";
		try {
			InputStream in = new FileInputStream(new File("D:/jatelindo2.log"));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			int count = 0;
			while (br.read() != -1) {
//				int ss = br.read();
//				char xx = (char) ss;
				read = br.readLine();
				System.out.println(" char : " + read);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return read;
	}
	public static void main (String[] args) {
		read();
	}
}
