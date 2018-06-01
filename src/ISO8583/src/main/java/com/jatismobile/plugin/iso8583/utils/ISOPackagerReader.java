package com.jatismobile.plugin.iso8583.utils;

import java.io.File;
import java.util.HashMap;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;

public class ISOPackagerReader {
	public static ISOPackager packager(String path) throws ISOException {
		ISOPackager packager = new GenericPackager(path);
		return packager;
	}

	public static HashMap<String, ISOPackager> packagerMap() {
		
		System.out.println(System.getProperties().keySet());
		File folder = new File("your/path");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}

		return null;
	}
	
	public static void main (String[] args) {
		packagerMap();
	}
}
