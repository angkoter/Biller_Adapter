//package com.jatismobile.plugin.iso8583.examples;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.EOFException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.jpos.iso.ISOException;
//import org.jpos.iso.ISOMsg;
//import org.jpos.iso.packager.GenericPackager;
//
///**
// *
// * @author Martinus Ady H <mrt.itnewbies@gmail.com>
// */
//public class ServerISO {
//
//	private static final Integer PORT = 1423;
//	private static final Map<String, Integer> mappingDENetworkMsg = new HashMap<String, Integer>();
//
//	/*
//	 * Method ini berfungsi untuk menginisialisasi data element dan panjang tiap
//	 * -tiap data element yang aktif
//	 */
//	private static void initMappingDENetworkRequest() {
//		/* [data-element] [panjang data element] */
//		mappingDENetworkMsg.put("3", 6);
//		mappingDENetworkMsg.put("7", 8);
//		mappingDENetworkMsg.put("11", 6);
//		mappingDENetworkMsg.put("12", 6);
//		mappingDENetworkMsg.put("13", 4);
//		mappingDENetworkMsg.put("39", 3);
//		mappingDENetworkMsg.put("48", 999);
//		mappingDENetworkMsg.put("70", 3);
//	}
//
//	/**
//	 * @param args
//	 *            the command line arguments
//	 * @throws ISOException
//	 */
//	public static void main(String[] args) throws IOException, ISOException {
//		initMappingDENetworkRequest();
//		ServerSocket serverSocket = new ServerSocket(PORT);
//		System.out.println("Server siap menerima koneksi pada port [" + PORT + "]");
//		Socket socket = serverSocket.accept();
//		DataInputStream inStreamReader = new DataInputStream(socket.getInputStream());
//		DataOutputStream sendMsg = new DataOutputStream(socket.getOutputStream());
//
//		int data;
//		StringBuffer sb = new StringBuffer();
//		int counter = 0;
//
//		// tambahan 4 karakter karena msg header adalah 4 digit msg length
//		// StringBuffer inputLine = new StringBuffer();
//		// String tmp;
//		// try {
//		// while ((tmp = inStreamReader.readUTF()) != null) {
//		// inputLine.append(tmp);
//		// System.out.println(tmp);
//		// }
//		// }
//		// catch (NumberFormatException e) {
//		// //e.printStackTrace();
//		// handleNetworkMsg(new String(""), sendMsg);
//		// }
//		// serverSocket.setSoTimeout(30000);
//		String ss = readFully(inStreamReader);
//
//		System.out.println("receive from client : " + ss.toString());
//
//		// GenericPackager packager = new GenericPackager("fields.xml");
//		// ISOMsg isoMsg = new ISOMsg();
//		// isoMsg.setPackager(packager);
//		// isoMsg.unpack(inputLine.toString().replace(" ", "").getBytes());
//		// handleNetworkMsg(new String(isoMsg.pack()), sendMsg);
//		writeFully(sendMsg);
//	}
//
//	/**
//	 * Memproses msg yang dikirim oleh client berdasarkan nilai MTI.
//	 * 
//	 * @param data
//	 *            request msg yang berisi [header 4byte][MTI][BITMAP][DATA
//	 *            ELEMENT]
//	 * @param sendMsg
//	 *            object printWriter untuk menuliskan msg ke network stream
//	 * @throws IOException
//	 */
//	private static void processingMsg(String data, DataOutputStream sendMsg) throws IOException {
//		// msg.asli tanpa 4 digit msg.header
//		String origMsgWithoutMsgHeader = data.substring(4, data.length());
//
//		// cek nilai MTI
//		if (ISOUtil.findMTI(origMsgWithoutMsgHeader).equalsIgnoreCase("0200")) {
//			handleNetworkMsg(origMsgWithoutMsgHeader, sendMsg);
//		}
//	}
//
//	/**
//	 * Method ini akan memproses network management request dan akan menambahkan
//	 * 1 data element yaitu data element 39 (response code) 000 ke client/sender
//	 * 
//	 * @param networkMsg
//	 *            request msg yang berisi [header 4byte][MTI][BITMAP][DATA
//	 *            ELEMENT]
//	 * @param sendMsg
//	 *            object printWriter untuk menuliskan msg ke network stream
//	 * @throws IOException
//	 */
//	private static void handleNetworkMsg(String networkMsg, DataOutputStream sendMsg) throws IOException {
//		// int panjangBitmap = ISOUtil.findLengthOfBitmap(networkMsg);
//		// String hexaBitmap = networkMsg.substring(4, 4+panjangBitmap);
//		//
//		// // hitung bitmap
//		// String binaryBitmap = ISOUtil.findBinaryBitmapFromHexa(hexaBitmap);
//		// String[] activeDE = ISOUtil.findActiveDE(binaryBitmap).split(";");
//		//
//		// StringBuilder networkResp = new StringBuilder();
//		//
//		// // setting MTI untuk reply network request
//		// networkResp.append("0210");
//		//
//		// // untuk reply, DE yang aktif adalah DE[3,7,11,12,13,39,48 dan 70]
//		// String bitmapReply = ISOUtil.getHexaBitmapFromActiveDE(new int[]
//		// {3,7,11,12,13,39,48, 70});
//		// networkResp.append(bitmapReply);
//		//
//		// // index msg dimulai dr (4 digit MTI+panjang bitmap = index DE ke 3)
//		// int startIndexMsg = 4+ISOUtil.findLengthOfBitmap(networkMsg);
//		// int nextIndex = startIndexMsg;
//		// String sisaDefaultDE = "";
//		//
//		// // ambil nilai DE yang sama dulu
//		// for (int i=0;i<activeDE.length;i++) {
//		// // ambil bit ke 3
//		// if (activeDE[i].equalsIgnoreCase("3")) {
//		// nextIndex += mappingDENetworkMsg.get(activeDE[i]);
//		// networkResp.append(networkMsg.substring(startIndexMsg, nextIndex));
//		// debugMessage(3, networkMsg.substring(startIndexMsg, nextIndex));
//		// } else if(activeDE[i].equalsIgnoreCase("7")) {
//		// startIndexMsg = nextIndex;
//		// nextIndex += mappingDENetworkMsg.get(activeDE[i]);
//		// networkResp.append(networkMsg.substring(startIndexMsg, nextIndex));
//		// debugMessage(7, networkMsg.substring(startIndexMsg, nextIndex));
//		// } else if(activeDE[i].equalsIgnoreCase("11")) {
//		// startIndexMsg = nextIndex;
//		// nextIndex += mappingDENetworkMsg.get(activeDE[i]);
//		// networkResp.append(networkMsg.substring(startIndexMsg, nextIndex));
//		// debugMessage(11, networkMsg.substring(startIndexMsg, nextIndex));
//		// } else if(activeDE[i].equalsIgnoreCase("12")) {
//		// startIndexMsg = nextIndex;
//		// nextIndex += mappingDENetworkMsg.get(activeDE[i]);
//		// networkResp.append(networkMsg.substring(startIndexMsg, nextIndex));
//		// debugMessage(12, networkMsg.substring(startIndexMsg, nextIndex));
//		// } else if(activeDE[i].equalsIgnoreCase("13")) {
//		// startIndexMsg = nextIndex;
//		// nextIndex += mappingDENetworkMsg.get(activeDE[i]);
//		// networkResp.append(networkMsg.substring(startIndexMsg, nextIndex));
//		// debugMessage(13, networkMsg.substring(startIndexMsg, nextIndex));
//		// } else if(activeDE[i].equalsIgnoreCase("48")) {
//		// startIndexMsg = nextIndex;
//		// // ambil dulu var.len utk DE 48
//		// int varLen = Integer.valueOf(networkMsg.substring(startIndexMsg,
//		// (startIndexMsg+3)));
//		// // 3 digit utk variabel len
//		// varLen += 3;
//		// nextIndex += varLen;
//		// sisaDefaultDE += networkMsg.substring(startIndexMsg, nextIndex);
//		// debugMessage(48, networkMsg.substring(startIndexMsg, nextIndex));
//		// } else if(activeDE[i].equalsIgnoreCase("70")) {
//		// startIndexMsg = nextIndex;
//		// nextIndex += mappingDENetworkMsg.get(activeDE[i]);
//		// sisaDefaultDE += networkMsg.substring(startIndexMsg, nextIndex);
//		// debugMessage(70, networkMsg.substring(startIndexMsg, nextIndex));
//		// }
//		// }
//		//
//		// // kasih response kode 39 success
//		// networkResp.append("000");
//		// // tambahkan sisa default DE
//		// networkResp.append(sisaDefaultDE);
//		//
//		// // tambahkan length 4 digit utk msg.header
//		// String msgHeader = "";
//		// if (networkResp.length() < 10) msgHeader = "000" +
//		// networkResp.length();
//		// if (networkResp.length() < 100 && networkResp.length() >= 10)
//		// msgHeader = "00" + networkResp.length();
//		// if (networkResp.length() < 1000 && networkResp.length() >= 100)
//		// msgHeader = "0" + networkResp.length();
//		// if (networkResp.length() >= 1000) msgHeader =
//		// String.valueOf(networkResp.length());
//		//
//		// String finalMsg = msgHeader + networkResp.toString();
//		//
//		// // send to client
//		// sendMsg.writeBytes(finalMsg);
//		sendMsg.writeBytes(
//				"�0200623A400108C1800006134003380000061411053289049011053206140615601203008675813758656DEVJATIS200900100800000018888880125679873305360");
//		sendMsg.flush();
//	}
//
//	private static void debugMessage(Integer fieldNo, String msg) {
//		System.out.println("[" + fieldNo + "] [" + msg + "]");
//	}
//
//	public static void read(InputStream is) throws IOException {
//
//		DataInputStream dis = null;
//
//		try {
//
//			// create new data input stream
//			dis = new DataInputStream(is);
//
//			// available stream to be read
//			int length = dis.available();
//
//			// create buffer
//			byte[] buf = new byte[length];
//
//			// read the full data into the buffer
//			dis.readFully(buf);
//
//			int counter = 0;
//			// for each byte in the buffer
//			for (byte b : buf) {
//
//				// convert byte to char
//				char c = (char) b;
//
//				// prints character
//				System.out.print(c);
//				counter++;
//			}
//
//		} catch (Exception e) {
//
//			// if any error occurs
//			e.printStackTrace();
//		} finally {
//
//			// releases all system resources from the streams
//			if (is != null)
//				is.close();
//			if (dis != null)
//				dis.close();
//		}
//	}
//
//	public static void read2(InputStream is) throws IOException {
//		DataInputStream dis = null;
//
//		try {
//
//			// create new data input stream
//			dis = new DataInputStream(is);
//
//			// available stream to be read
//			int length = dis.available();
//
//			// create buffer
//			byte[] buf = new byte[length];
//
//			// read the full data into the buffer
//			dis.readFully(buf, 0, 4096);
//
//			// for each byte in the buffer
//			for (byte b : buf) {
//				char c = '0';
//				if (b != 0)
//					c = (char) b;
//
//				// prints character
//				System.out.print(c);
//			}
//
//		} catch (Exception e) {
//
//			// if any error occurs
//			e.printStackTrace();
//		} finally {
//
//			// releases all system resources from the streams
//			if (is != null)
//				is.close();
//			if (dis != null)
//				dis.close();
//		}
//	}
//
//	public static String readFully(InputStream input) {
//		byte[] buffer;
//		try {
//			DataInputStream dataInput = new DataInputStream(input);
//			try {
//				buffer = new byte[dataInput.available()];
//				dataInput.readFully(buffer);
//				return new String(buffer, 0, buffer.length, "UTF-8");
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
//	public static String writeFully(OutputStream output) {
//		byte[] buffer = "�0210623A40010AC180180613400338000000201706228904902357380622061560120300867581375865600DEVJATIS2009001008000009990000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA3609990000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA9990000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA"
//				.getBytes();
//		// byte[] buffer = {87,64,72,31,90};
//		try {
//			DataOutputStream dataOutput = new DataOutputStream(output);
//			try {
//				// buffer = new byte[dataOutput.size()];
//				dataOutput.write(buffer, 2, buffer.length - 2);
//				dataOutput.flush();
//			} finally {
//				// dataOutput.close();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//}