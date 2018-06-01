package com.jatismobile.plugin.iso8583.utils;

import org.jpos.iso.ISOException;
import org.jpos.iso.packager.GenericPackager;

public class XMLStringToGenericPackager {
	public static GenericPackager getPackager (String xmlString) throws ISOException {
		GenericPackager packager = new GenericPackager(xmlString);
		return packager;
	}
}
