//package com.jatismobile.plugin.iso8583.examples;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.nio.ByteBuffer;
//
//import org.jpos.iso.ISOException;
//import org.jpos.iso.ISOHeader;
//import org.jpos.iso.ISOMsg;
//import org.jpos.iso.ISOPackager;
//import org.jpos.iso.channel.ASCIIChannel;
//import org.jpos.iso.header.BaseHeader;
//import org.jpos.iso.packager.GenericPackager;
//
//public class Channel {
//	public static void main (String[] args) throws ISOException, IOException {
////		String path = System.getProperties().getProperty("user.dir") + "/fields.xml";
//		ISOPackager p = new GenericPackager("fields.xml");
////		ISOPackager p = new GenericPackager(path);
//		ASCIIChannel channel = new ASCIIChannel ("localhost", 1423, p);
//		channel.connect();
////		ASCIIChannel channel = new ASCIIChannel ("202.152.22.118", 1423, p);
////		ASCIIChannel channel = new ASCIIChannel ("103.252.51.103", 6544, p);
////		ASCIIChannel channel = new ASCIIChannel (args[0], Integer.valueOf(args[1]), p);
////		channel.connect ();
//		
//		ISOMsg isoMsg = new ISOMsg();
////		isoMsg.setHeader("".getBytes());
//        isoMsg.setMTI("0200");
//        isoMsg.set(2, "134003");//product code
//        isoMsg.set(3, "380000");//processing type
////        isoMsg.set(7, new SimpleDateFormat("yyyyMMdd").format(new Date()));
//        isoMsg.set(7, "0614110532");
//        isoMsg.set(11, "890490");
////        isoMsg.set(12, new SimpleDateFormat("HHmmss").format(new Date()));
//        isoMsg.set(12, "110532");
////        isoMsg.set(13, new SimpleDateFormat("MMdd").format(new Date()));
//        isoMsg.set(13, "0614");
//        isoMsg.set(15, "0615");
//        isoMsg.set(18, "6012");//merchant type
//        isoMsg.set(32, "008");//kode bank
//        isoMsg.set(37, "675813758656");//reference number random but it is used the same for one cycle
//        isoMsg.set(41, "DEVJATIS");//terminal_id
//        isoMsg.set(42, "200900100800000");//acceptor_id
//        isoMsg.set(48, "888880125679873305");//nova
//        isoMsg.set(49, "360");//currency
//
//		isoMsg.setPackager(p); 
//		byte[] messageBody=isoMsg.pack(); 
//		short messageLength=(short)messageBody.length; 
//		ByteBuffer bb=ByteBuffer.allocate(2); 
//		bb.putShort((short)messageLength);
//		isoMsg.setHeader(("" +(int)messageLength).getBytes());
//		byte[] header=bb.array(); 
//		BaseHeader bh=new BaseHeader(concat(header,isoMsg.getHeader())); 
//		isoMsg.setHeader((ISOHeader)bh);
//		System.out.println();
//
//		channel.send(isoMsg.getBytes());
//
//	}
//	
//		
//	private static byte[] concat(byte[] array1, byte[] array2) {
//		byte[] result = new byte[array1.length + array2.length];
//		for (int i=0; i<array1.length; i++) {
//		result[i] = array1[i];
//		}
//		for (int j=0; j<array2.length; j++) {
//		result[array1.length+j] = array2[j];
//		}
//		return result;
//	}
//}
