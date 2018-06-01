//package com.jatismobile.plugin.iso8583.examples;
//
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.util.HashMap;
//
//import org.codehaus.jackson.map.ObjectMapper;
//import org.jpos.iso.ISOChannel;
//import org.jpos.iso.ISOException;
//import org.jpos.iso.ISOMsg;
//import org.jpos.iso.ISORequestListener;
//import org.jpos.iso.ISOServer;
//import org.jpos.iso.ISOSource;
//import org.jpos.iso.ServerChannel;
//import org.jpos.iso.channel.ASCIIChannel;
//import org.jpos.iso.packager.GenericPackager;
//import org.jpos.util.Logger;
//import org.jpos.util.SimpleLogListener;
//import org.jpos.util.ThreadPool;
//
//import com.jatismobile.plugin.iso8583.utils.ISOHeader;
//
//public class Test {
//
//    public static void main(String[] args) throws Exception {
//
//        Logger l = new Logger();
//        l.addListener(new SimpleLogListener());
//        GenericPackager serverPkg = new GenericPackager("fields.xml");
//        GenericPackager packager = serverPkg;
//        serverPkg.setLogger(l, "Server"); // So that the output can be differentiated based on realm
//
//        GenericPackager clientPkg = serverPkg;
//        clientPkg.setLogger(l, "Client");// So that the output can be differentiated based on realm
//        // Simulate a server and listen on a port
//        ISOChannel serverChannel = new ASCIIChannel(serverPkg);
//
////		short messageLength = (short)(133); 
////		ByteBuffer bb=ByteBuffer.allocate(2); 
////		bb.putShort((short)messageLength);
////		byte[] header=bb.array();
//        ((ASCIIChannel) serverChannel).setHeader(ISOHeader.generateHeader(133));
//
//        ((ASCIIChannel) serverChannel).setLogger(l, "server");
//        ISOServer server = new ISOServer(1423, (ServerChannel) serverChannel,
//                new ThreadPool(10, 100, "serverListeningThread"));
//
//        server.addISORequestListener(new ISORequestListener() {
//            // If the client sends a message, the server will respond with and approval if its a request message
//            @Override
//            public boolean process(ISOSource source, ISOMsg msg) {
//                try {
////                    if (!msg.isRequest()) {
//                	ISOMsg isoMsg2 = (ISOMsg) msg.clone();
//                	isoMsg2.setResponseMTI();
//                        isoMsg2.set(48,"0000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA");
//                        isoMsg2.set(39, "00");
//                        isoMsg2.setPackager(serverPkg);
//                        
//                        source.send(isoMsg2);
//                        System.out.println("draft 37 : " + isoMsg2.getString(37));
//                        System.out.println("draft 39 : " + isoMsg2.getString(39));
//                        System.out.println("draft 48 : " + isoMsg2.getString(48));
//                        System.out.println("draft response : " + new String(isoMsg2.pack()));
////                    }
//                }
//                catch (ISOException | IOException ex) {
//
//                }
//
//                return true;
//            }
//        });
//        Thread serverThread = new Thread(server);
//        serverThread.start(); // beyond this point the server is listening for a client connection
//
//        ASCIIChannel clientChannel = new ASCIIChannel("127.0.0.1", 6544, clientPkg);
//       //Similar to server, you can configure the constant in your deploy file​
//        clientChannel.setHeader(ISOHeader.generateHeader(133));
//        clientChannel.setLogger(l, "client");
//        clientChannel.connect(); // connect to server, it will be seen in the output console
//        ISOChannel connectChannel = server.getLastConnectedISOChannel();// Since server can have multiple connections,
//                                                                        // we get the last one that connected to it.
//		HashMap<String, Object> generalRequest = new HashMap<String, Object>();
//		generalRequest.put("TRX_ID","1234567654321");
//		generalRequest.put("PRODUCT_CODE","134003");
//		generalRequest.put("SUBSCRIBER_NUMBER","888880108910747101");
//		generalRequest.put("JATIS_BILLER_ID","5");
//		generalRequest.put("VENDOR_BILLER_ID","7");
//
//		ObjectMapper om = new ObjectMapper();
//		String template = "{\"0\":\"0200\",\r\n\"2\":\"PRODUCT_CODE\",\r\n\"3\":\"380000\",\r\n\"7\":\"TRANSMISSION_DATE\",\r\n\"11\":\"STAN\",\r\n\"12\":\"LOCAL_TIME\",\r\n\"13\":\"LOCAL_DATE\",\r\n\"15\":\"SETTLEMENT_DATE\",\r\n\"18\":\"6012\",\r\n\"32\":\"008\",\r\n\"37\":\"RANDOM_REFERENCE_NUMBER\",\r\n\"41\":\"DEVJATIS\",\r\n\"42\":\"200900100800000\",\r\n\"48\":\"SUBSCRIBER_NUMBER\",\r\n\"49\":\"360\"}";
//		String requestMap = om.writeValueAsString(generalRequest);
//		ISOMsg isoMsg = PackISOMessage.buildISOMessage(ISOMsg.INCOMING);
//		isoMsg.setPackager(clientPkg);
//		isoMsg.setHeader(ISOHeader.generateHeader(133));
//        connectChannel.send(isoMsg);
//       //use the last one connected to send a request message to the client.
//        ISOMsg receivedRequest = clientChannel.receive();// receive the serers request message at the client
//
//        ISOMsg clientResponse = (ISOMsg) receivedRequest.clone();
//        clientResponse.setResponseMTI();
//        clientResponse.setHeader(ISOHeader.generateHeader(133));
////        clientResponse.set(60,"0000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA");
////        clientResponse.set(61,"0000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA");
//        clientChannel.send(clientResponse); // send the response to server
//        System.out.println("11 : " + receivedRequest.getString(11));
//        System.out.println("asdfasdf : " + new String(clientResponse.pack()));
//    }
//
//}