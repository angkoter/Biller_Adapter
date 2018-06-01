package com.jatismobile.plugin.iso8583.utils;

import java.nio.ByteBuffer;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public class ISOHeader {
	public static byte[] generateHeader (int dec) {
		byte[] rear = Converter.hexToString(""+Integer.valueOf(Converter.decToHex(dec)));
		byte[] front = Converter.hexToString("0"+Integer.valueOf(Converter.decToHex(00)));
		byte[] joined = join(front, rear);
		return joined;
	}
	
	public static byte[] getTrailer (int dec) {
		byte[] rear = Converter.hexToString(""+Integer.valueOf(Converter.decToHex(dec)));
		return rear;
	}
	
	public static byte[] join(byte[] a, byte[] b) {
	    // join two byte arrays
	    final byte[] ret = new byte[a.length + b.length];
	    System.arraycopy(a, 0, ret, 0, a.length);
	    System.arraycopy(b, 0, ret, a.length, b.length);
	    return ret;
	}
	public static byte[] getTwoCharactersHeader(ISOMsg isoMsg) throws ISOException {
		byte[] messageBody = isoMsg.pack();
		short messageLength = (short)(messageBody.length+1);
//		short messageLength = (short)(131);
		ByteBuffer bb=ByteBuffer.allocate(2); 
		bb.putShort((short)messageLength);
		byte[] header=bb.array();	
//		System.out.println("this is header : " + new String(header));
		return header;
//		return (messageLength+"").getBytes();
	}
	public static byte[] getTwoCharactersHeader() throws ISOException {
		short messageLength = (short)(133); 
		ByteBuffer bb=ByteBuffer.allocate(2); 
		bb.putShort((short)messageLength);
		byte[] header=bb.array();		
		return header;
	}
	public static void main(String[] args) {
//		System.out.println(generateHeader(133));
		String res = new String(getTrailer(3));
		System.out.println(res );
		PrintStringToFile.print(res);
	}
}
