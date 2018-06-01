//package com.jatismobile.plugin.iso8583.examples;
//
//import java.io.DataOutputStream;
//import java.io.OutputStream;
//import java.io.PrintStream;
//import java.net.Socket;
//import java.net.URI;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//
//import org.apache.commons.io.IOUtils;
//import org.bouncycastle.util.encoders.Hex;
//import org.jpos.iso.ISOException;
//import org.jpos.iso.ISOMsg;
//import org.jpos.iso.ISOPackager;
//import org.jpos.iso.channel.ASCIIChannel;
//import org.jpos.iso.packager.GenericPackager;
//import org.jpos.iso.packager.XMLPackager;
//
//import com.jatismobile.plugin.iso8583.utils.ISOHeader;
//import com.jatismobile.plugin.iso8583.utils.PrintStringToFile;
//import com.jatismobile.plugin.iso8583.utils.ReadFile;
//
//public class SendISO {
//
//	public void  getResponse(String input, int dir) throws Exception {
//		PackISOMessage p = new PackISOMessage();
//		HashMap<String, Object> mm = new HashMap<String, Object>();
//		byte[] response = null;
////		ISOMsg isoMsg = null;
//		try {
//			GenericPackager packager = new GenericPackager("fields.xml");
////		isoMsg = PackISOMessage.buildISOMessage(mm,packager, ISOMsg.OUTGOING);
//			
//			ASCIIChannel chl = new ASCIIChannel("localhost",
//					6544, packager);
//			
////			byte[] in = "�0200623A400108C1800006134003380000061411053289049011053206140615601203008675813758656DEVJATIS200900100800000018888880125679873305360".getBytes();
//			byte[] in = "0200623A400108C1800006134003380000061411053289049011053206140615601203008675813758656DEVJATIS200900100800000018888880125679873305360".getBytes();
////			byte[] msg = ISOHeader.join(ISOHeader.generateHeader(133).getBytes(), in);
//			@SuppressWarnings("deprecation")
//			ISOMsg iso2 = new ISOMsg();
//			iso2.setPackager(packager);
//			iso2.unpack(in);
////			iso2.setHeader(ISOHeader.getTwoCharactersHeader(iso2));
//			iso2.setDirection(ISOMsg.OUTGOING);
////			iso2.setTrailer("\u0003".getBytes());
//			System.out.println("value 1: " + iso2.getValue());
////			System.out.println("value 2: " + new String(iso2.getTrailer()));
//			
//			chl.connect();
////			chl.send(isoMsg);
////			PrintStringToFile.print(new String(in).replace(ReadFile.read(), ""));
////			System.out.println("is it req :" + isoMsg.isRequest());
//			chl.send(iso2);
//			response = chl.receive().pack();
//			System.out.println("response : " + new String(response));
////			chl.reconnect();
//			chl.disconnect();
//
//		} catch (Exception e) {
//			System.out.println("Failed," + e.getMessage().toString() + ", caused by : " + e.getCause());
//		}
//		
////		return (""+isoMsg.getMTI());
////		return iso;
//	}
//	
//	public ISOMsg getResponse(HashMap<String, Object> input, int dir, ISOPackager packager) throws Exception {
//		HashMap<String, Object> mm = new HashMap<String, Object>();
//		ISOMsg response = null;
//		ISOMsg isoMsg = null;
//		try {
//		isoMsg = PackISOMessage.buildISOMessage(dir);
//			
//		
//		ISOMsg isoMsg2 = PackISOMessage.buildISOMessage(ISOMsg.INCOMING);
//			ASCIIChannel chl = new ASCIIChannel("localhost",6544, packager);
//			chl.setHeader(ISOHeader.generateHeader(133));
////			int xx = packager.createISOMsg().pack().length;
////			System.out.println("panjang xx : " + xx);
//			
////			byte[] bapuk = "�0200623A400108C1800006134003380000061411053289049011053206140615601203008675813758656DEVJATIS200900100800000018888880125679873305360".getBytes(StandardCharsets.UTF_8);
////			isoMsg.unpack(bapuk);
//			
//			chl.connect();
//			chl.send(isoMsg);
//			response = chl.receive();
//			response.setPackager(packager);
//			
////	        ISOMsg clientResponse = (ISOMsg) response.clone();
////	        clientResponse.setMTI("0210");
////	        clientResponse.setPackager(packager);
////	        clientResponse.set(39, "00");
////	        clientResponse.setHeader(ISOHeader.getTwoCharactersHeader(clientResponse));
////	        chl.send(clientResponse);
//			
//			System.out.println("response : " + response.getString(60));
//			
//			
//			chl.disconnect();	
//
//		} catch (Exception e) {
////			System.out.println("Failed," + e.getMessage().toString() + ", caused by : " + e.getCause());
//			e.printStackTrace();
//		}
//		
////		return (""+isoMsg.getMTI());
//		return response;
//	}
//	
//	public static void main (String[] args) throws Exception {
//		SendISO s = new SendISO();
////		s.getResponse("",ISOMsg.OUTGOING);
//		
//		GenericPackager packager = new GenericPackager("fields.xml");
////		XMLPackager packager = new XMLPackager();
//		s.getResponse(new HashMap<String, Object>(),ISOMsg.OUTGOING, packager);
//	}
//}
