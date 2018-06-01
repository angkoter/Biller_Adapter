package com.jatismobile.plugin.iso8583.examples;
//package com.jatismobile.plugin.iso8583;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
// 
///**
// *
// * @author Martinus Ady H <mrt.itnewbies@gmail.com>
// */
//public class ClientISOD {
// 
//    private final static Integer PORT_SERVER = 12345;
// 
//    /**
//     * @param args the command line arguments
//     * @throws Exception 
//     */
//    public static void main(String[] args) throws Exception {
//        Socket clientSocket = new Socket("localhost", 2004);
//        PackISOMessage packMessage = new PackISOMessage();
//        String networkRequest = packMessage.buildISOMessage();
////        String networkRequest = buildNetworkReqMessage();
// 
//        PrintWriter outgoing = new PrintWriter(clientSocket.getOutputStream());
//        InputStreamReader incoming = new InputStreamReader(clientSocket.getInputStream());
//        
//        System.out.println("isoMessage : " + networkRequest);
//        
//        outgoing.print(networkRequest);
//        outgoing.flush();
// 
//        int data;
//        StringBuffer sb = new StringBuffer();
//        int counter = 0;
//        // tambahan 4 karakter karena msg header adalah 4 digit msg length
//        int lengthOfMsg = 4;
//        while((data = incoming.read()) != 0) {
//            counter++;
//            sb.append((char) data);
//            if (counter == 4) lengthOfMsg += Integer.valueOf(sb.toString());
// 
//            // klo panjang msg dari MTI sampai END OF MSG sama dengan nilai
//            // header maka lanjutkan ke method processingMsg();
//            if (lengthOfMsg == sb.toString().length()) {
//                System.out.println("Rec. Msg ["+sb.toString()+"] len ["+sb.toString().length()+"]");
//            }
//        }
// 
//        outgoing.close();
//        incoming.close();
//        clientSocket.close();
//    }
//}