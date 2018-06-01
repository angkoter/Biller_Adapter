//package com.jatismobile.plugin.iso8583.examples;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.apache.commons.io.output.ByteArrayOutputStream;
//import org.bouncycastle.crypto.tls.NewSessionTicket;
//import org.jpos.iso.ISOMsg;
//
//import com.jatismobile.plugin.iso8583.utils.ISOHeader;
//import com.jatismobile.plugin.iso8583.utils.PrintStringToFile;
// 
///**
// *
// * @author Martinus Ady H <mrt.itnewbies@gmail.com>
// */
//public class ClientISO {
// 
//    private final static Integer PORT_SERVER = 1423;
// 
//    /**
//     * @param args the command line arguments
//     * @throws Exception 
//     */
//    public static void main(String[] args) throws Exception {
////        Socket clientSocket = new Socket("202.152.22.118", PORT_SERVER);
//        Socket clientSocket = new Socket("localhost", PORT_SERVER);
//        String networkRequest = buildNetworkReqMessage();
// 
////        OutputStreamWriter outgoing = new OutputStreamWriter(clientSocket.getOutputStream());
//        OutputStream outgoing = clientSocket.getOutputStream();
////        InputStreamReader incoming = new InputStreamReader(clientSocket.getInputStream(),"UTF-8");
//        InputStreamReader incoming = new InputStreamReader(clientSocket.getInputStream());
////        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        System.out.println("req : " + networkRequest);
////        outgoing.print(networkRequest);
//        
//        outgoing.write(networkRequest.getBytes("UTF-8"));
//        outgoing.flush();
//        int data;
//        StringBuffer sb = new StringBuffer();
//        int counter = 0;
//        // tambahan 4 karakter karena msg header adalah 4 digit msg length
//        int lengthOfMsg = 4;
//        while((data = incoming.read()) != -1) {
//            counter++;
//            sb.append(data);
////            if (counter == 4) {
////            	lengthOfMsg += Integer.valueOf(sb.toString());
////            }
////            else if (counter == 5) {
////            	outgoing.close();
////            	break;
////            }
//            System.out.println("counter : " + sb.toString());
//            // klo panjang msg dari MTI sampai END OF MSG sama dengan nilai
//            // header maka lanjutkan ke method processingMsg();
//            if (counter == 136) {
////            	System.out.println("Rec. Msg ["+sb.toString()+"] len ["+sb.toString().length()+"]");
//            	PrintStringToFile.print(sb.toString());
////            	clientSocket.setKeepAlive(false);
//            }
//        }
//        
//        
//        
////        int n = clientSocket.getInputStream().available();
////        byte[] bytes = new byte[n];
////        clientSocket.getInputStream().read(bytes, 0, n);
////        String sb = new String(bytes, StandardCharsets.UTF_8);
//        System.out.println("Rec. Msg ["+new String(sb)+"] len ["+new String(sb).toString().length()+"]");
//        
//        outgoing.close();
//        incoming.close();
////        in.close();
//        clientSocket.close();
////        ByteArrayOutputStream baos = new ByteArrayOutputStream();
////        byte[] buf = new byte[8192];
////        for (;;) {
////            int nread = clientSocket.getInputStream().read(buf, 0, buf.length);
////            if (nread <= 0) {
////                break;
////            }
////            baos.write(buf, 0, nread);
////        }
////        clientSocket.getInputStream().close();
////        baos.close();
////        byte[] bytes = baos.toByteArray();
////        System.out.println("byte : " + new String(bytes));
//    }
// 
//    private static String buildNetworkReqMessage() throws Exception {
//        StringBuilder networkReq = new StringBuilder();
// 
////        // MTI 1800
////        networkReq.append("0200");
////        // untuk request, DE yang aktif adalah DE[3,7,11,12,13,48 dan 70]
////        String bitmapReq = ISOUtil.getHexaBitmapFromActiveDE(new int[] {3,7,11,12,13,48,70});
////        networkReq.append(bitmapReq);
////        // DE 3 processing code
////        networkReq.append("000001");
////        // DE 7 transmission date and time
////        networkReq.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
////        // DE 11 system trace audit number
////        networkReq.append("000001");
////        // DE 12 local time transaction
////        networkReq.append(new SimpleDateFormat("HHmmss").format(new Date()));
////        // DE 13 local time transaction
////        networkReq.append(new SimpleDateFormat("MMdd").format(new Date()));
////        // DE 48 Additional Private Data
////        final String clientID = "CLNT001";
////        // length de 48
////        String lengthBit48 = "";
////        if (clientID.length() < 10) lengthBit48 = "00" + clientID.length();
////        if (clientID.length() < 100 && clientID.length() >= 10) lengthBit48 = "0" + clientID.length();
////        if (clientID.length() == 100) lengthBit48 = String.valueOf(clientID.length());
////        networkReq.append(lengthBit48);
////        networkReq.append(clientID);
//// 
////        // DE 70 Network Information Code
////        networkReq.append("001");
//// 
////        // tambahkan 4 digit length of msg sbg header
////        String msgHeader = "";
////        if (networkReq.toString().length() < 10) msgHeader = "000" + networkReq.toString().length();
////        if (networkReq.toString().length() < 100 && networkReq.toString().length() >= 10) msgHeader = "00" + networkReq.toString().length();
////        if (networkReq.toString().length() < 1000 && networkReq.toString().length() >= 100) msgHeader = "0" + networkReq.toString().length();
////        if (networkReq.toString().length() >= 1000) msgHeader = String.valueOf(networkReq.toString().length());
//// 
////        StringBuilder finalNetworkReqMsg = new StringBuilder();
////        finalNetworkReqMsg.append(msgHeader);
////        finalNetworkReqMsg.append(networkReq.toString());
////        return finalNetworkReqMsg.toString();
//        
//		ISOMsg isoMessage = PackISOMessage.buildISOMessage(ISOMsg.OUTGOING);
//		String ss = new String(ISOHeader.join(ISOHeader.join(ISOHeader.generateHeader(133), isoMessage.pack()),"\u0003".getBytes()));
////        return ss;
//		return "�0200623A400108C1800006134003380000061411053289049011053206140615601203008675813758656DEVJATIS200900100800000018888880125679873305360";
//    }
//}