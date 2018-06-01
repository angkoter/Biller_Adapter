//package com.jatismobile.plugin.iso8583.examples;
//
//import java.io.*;
//import java.net.*;
//import java.nio.ByteBuffer;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//
//import org.apache.commons.io.IOUtils;
//import org.jpos.iso.ISOException;
//import org.jpos.iso.ISOMsg;
//import org.jpos.iso.packager.GenericPackager;
//import org.jpos.iso.packager.ISO93APackager;
//
//import com.jatismobile.plugin.iso8583.utils.ISOHeader;
//
//public class Requester{
//	Socket requestSocket;
//	DataOutputStream out;
// 	DataInputStream in;
// 	String message;
//	Requester(){}
//	void run() 
//	{
//		try{
//			//1. creating a socket to connect to the server
//			int port = 1423;
//			requestSocket = new Socket("localhost", port);
//			System.out.println("Connected to port : " + port);
//			//2. get Input and Output streams
//			out = new DataOutputStream(requestSocket.getOutputStream());
//			out.flush();
//			in = new DataInputStream(requestSocket.getInputStream());
//			//3: Communicating with the server
//			ISOMsg isoMsg = null;
//			boolean tester = true;
//			GenericPackager packager = null;
////			do{
//				try{
//					packager = new GenericPackager("fields.xml");
//					System.out.println("looping");
//					ISOMsg isoMessage = PackISOMessage.buildISOMessage(ISOMsg.OUTGOING);
//					sendMessage(new String(ISOHeader.join(ISOHeader.join(ISOHeader.generateHeader(133), isoMessage.pack()),"\u0003".getBytes())));
//
//					System.out.println("1");
//					ISOMsg isoMsgRes = new ISOMsg();
//					System.out.println("2");
//					isoMsgRes.setPackager(packager);
//					String ss = readFully2(in);
//					System.out.println("3 : " + ss );
//					
//					isoMsgRes.unpack(ss.getBytes());
//					System.out.println("4");
////					isoMsg.unpack(in.toString().getBytes());
//					System.out.println("R<server>MTI : " + isoMsgRes.getMTI());
//					System.out.println("R<server>Header : " + isoMsgRes.getHeader());
//					System.out.println("R<server>Field 48 : " + isoMsgRes.getString(48));
//					System.out.println("R<server>message : " + new String(isoMsgRes.pack()));
//					System.out.println("R<server>" + isoMsgRes.getValue());
//					tester = false;
//				}
//				catch(Exception e){
//					System.err.println("data received in unknown format, " + e.getMessage()+", " + e.getCause());
//				}
////			}
////			while(tester);
//		}
//		catch(UnknownHostException unknownHost){
//			System.err.println("You are trying to connect to an unknown host!");
//		}
//		catch(IOException ioException){
//			ioException.printStackTrace();
//		}
//		finally{
//			//4: Closing connection
//			try{
//				in.close();
//				out.close();
//				requestSocket.close();
//			}
//			catch(IOException ioException){
//				ioException.printStackTrace();
//			}
//		}
//	}
//
//	void sendMessage(String msg) throws ISOException
//	{
//		try{
//			byte[] ss = ISOHeader.getTwoCharactersHeader();
////			out.write("0210623A40010AC180180613400338000000201706228904902357380622061560120300867581375865600DEVJATIS2009001008000009990000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA3609990000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA9990000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA".getBytes(), 0, "�0210623A40010AC180180613400338000000201706228904902357380622061560120300867581375865600DEVJATIS2009001008000009990000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA3609990000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA9990000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA".getBytes().length);
////			byte[] mm = ISOHeader.join(ss, "0210623A40010AC180180613400338000000201706228904902357380622061560120300867581375865600DEVJATIS2009001008000009990000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA3609990000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA9990000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA".getBytes());
//			byte[] mm = ISOHeader.join(ss, "0200623A400108C1800006134003380000061411053289049011053206140615601203008675813758656DEVJATIS200900100800000018888880125679873305360".getBytes(StandardCharsets.UTF_8));
//			byte[] nn = ISOHeader.join(mm, "\u0003".getBytes(StandardCharsets.UTF_8));
//			out.write(nn, 0, nn.length);
//			out.flush();
//			System.out.println("client>" + msg);
//		}
//		catch(IOException ioException){
//			ioException.printStackTrace();
//		}
//	}
//	public static void main(String args[]) throws Exception
//	{
//		Requester client = new Requester();
//		client.run();
//	}
//	
//	// convert InputStream to String
//	private static String getStringFromInputStream(InputStream is) {
//
//		BufferedReader br = null;
//		StringBuilder sb = new StringBuilder();
//
//		String line;
//		try {
//
//			br = new BufferedReader(new InputStreamReader(is));
//			while ((line = br.readLine()) != null) {
//				sb.append(line);
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//		return sb.toString();
//	}
//	
//	public static String readFully(InputStream input) {
//		byte[] buffer;
//		try {
//			DataInputStream dataInput = new DataInputStream(input);
//			try {
//				buffer = new byte[dataInput.available()];
//				dataInput.readFully(buffer);
//				return new String(buffer, 0, buffer.length-0, "UTF-8");
//			} finally {
//				dataInput.close();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//	
//	public static String readFully2 (InputStream input) throws IOException {
//		DataInputStream in = new DataInputStream(input);
//        int bytesRead = 0;
//
//        byte[] messageByte = new byte[4096];;
//		messageByte[0] = in.readByte();
//        messageByte[1] = in.readByte();
//        ByteBuffer byteBuffer = ByteBuffer.wrap(messageByte, 0, 2);
//
//        int bytesToRead = byteBuffer.getShort();
//        System.out.println("Client about to read " + bytesToRead + " octets");
//
//        //The following code shows in detail how to read from a TCP socket
//
//        boolean end = false;
//		String messageString = "";
//		while(!end)
//        {
//            bytesRead = in.read(messageByte);
//            messageString += new String(messageByte, 0, bytesRead);
//            if (messageString.length() == bytesToRead )
//            {
//                end = true;
//            }
//        }
//
//        //All the code in the loop can be replaced by these two lines
//        //in.readFully(messageByte, 0, bytesToRead);
//        //messageString = new String(messageByte, 0, bytesToRead);
//
//        System.out.println("MESSAGE: " + messageString);
//		return messageString;
//	}
//}