//package com.jatismobile.plugin.iso8583.examples;
//
//import java.nio.ByteBuffer;
//import java.text.SimpleDateFormat;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//
//import org.jpos.iso.ISOException;
//import org.jpos.iso.ISOHeader;
//import org.jpos.iso.ISOMsg;
//import org.jpos.iso.ISOPackager;
//import org.jpos.iso.header.BASE1Header;
//import org.jpos.iso.header.BaseHeader;
//import org.jpos.iso.packager.GenericPackager;
//
//import com.jatismobile.plugin.iso8583.utils.Converter;
//import com.jatismobile.plugin.iso8583.utils.DateIncrementer;
//import com.jatismobile.plugin.iso8583.utils.PrintStringToFile;
//
////import javassist.bytecode.ByteArray;
//
//public class PackISOMessage {
//    public static void main(String[] args) {
//        try {
//        	ISOPackager p = new GenericPackager("fields.xml");
//            ISOMsg message = PackISOMessage.buildISOMessage(ISOMsg.OUTGOING);
//            System.out.printf("Message = %s", message.getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    public static ISOMsg buildISOMessage(int dest) throws Exception {
//		ISOPackager p = new GenericPackager("fields.xml");
//		
//		ISOMsg isoMsg = new ISOMsg();
//        isoMsg.set(0,"0200");
//        isoMsg.set(2, "134003");//product code
//        isoMsg.set(3, "380000");//processing type
//        isoMsg.set(7, new SimpleDateFormat("yyyyMMdd").format(new Date()));
//        isoMsg.set(11, "890490");
//        isoMsg.set(12, new SimpleDateFormat("HHmmss").format(new Date()));
//        isoMsg.set(13, new SimpleDateFormat("MMdd").format(new Date()));
//        isoMsg.set(15, "0615");
//        isoMsg.set(18, "6012");//merchant type
//        isoMsg.set(32, "008");//kode bank
//        isoMsg.set(37, "675813758656");//reference number random but it is used the same for one cycle
//        isoMsg.set(41, "DEVJATIS");//terminal_id
//        isoMsg.set(42, "200900100800000");//acceptor_id
//        isoMsg.set(48, "8888801089107471");//nova
//        isoMsg.set(49, "360");//currency
//        isoMsg.setTrailer(Converter.convertHexToString("03").getBytes());
//        isoMsg.setDirection(dest);
//		isoMsg.setPackager(p);
//		byte[] messageBody = isoMsg.pack();
////		short messageLength = (short)(messageBody.length+1); 
////		ByteBuffer bb=ByteBuffer.allocate(2); 
////		bb.putShort((short)messageLength);
////		byte[] header=bb.array();
////		isoMsg.setHeader(com.jatismobile.plugin.iso8583.utils.ISOHeader.generateHeader(133));
//		System.out.println("Pack : " + new String(messageBody));
//		return isoMsg;
//    }
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