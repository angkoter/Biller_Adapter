//package com.jatismobile.plugin.iso8583.examples;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.Socket;
//
//public class Client {
//    public static void main(String args[])
//    {
//        try {
//            String cau1 = "012301230123012301230quit";// Cau duoc gui toi server
//            String ketQua;//Cau duoc server xu ly va tra lai la in hoa
//          
//            BufferedReader inFormUser= new BufferedReader(new InputStreamReader(System.in));// Tao input stream
//            Socket clientSocket= new Socket("127.0.0.1",1423);// Tao clinent socket de ket noi toi server
//            DataOutputStream sendToServer= new DataOutputStream(clientSocket.getOutputStream());// Tao output stream ket noi toi socket
//            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//            while(true)
//            {
//                cau1 = inFormUser.readLine();// Nhap vao 1 cau
//                sendToServer.writeBytes(cau1+'\n');// gui toi server
//                if (cau1.equalsIgnoreCase("quit"))// Gap chuoi quit
//                    break;
//                ketQua = inFromServer.readLine();// Nhan lai tu server
//                System.out.println("FROM SERVER: "+ketQua);
//            }
//            clientSocket.close();//Dong ket noi
//        } catch (IOException e) {
//            System.out.println("Exception Client: "+e.getMessage());
//        }
//    }
//}