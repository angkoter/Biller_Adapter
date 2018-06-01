package com.jatismobile.plugin.iso8583.utils;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

public class StringToISOMessage {
	public static ISOMsg getISOMessage (String isoString, GenericPackager packager) throws ISOException {
		
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		isoMsg.unpack(isoString.getBytes());
		
		return isoMsg;
	}
}
