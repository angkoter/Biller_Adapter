//package com.jatismobile.plugin.iso8583.examples;
//
//import java.io.IOException;
//
//import org.jpos.iso.ISOChannel;
//import org.jpos.iso.ISOException;
//import org.jpos.iso.ISOHeader;
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
//import com.jatismobile.plugin.iso8583.interfaces.ISO8583Handler;
//
//public class Test2 {
//
//    public static void main(String[] args) throws Exception {
//
//        Logger l = new Logger();
//        l.addListener(new SimpleLogListener());
//        GenericPackager serverPkg = new GenericPackager("fields.xml");
//        serverPkg.setLogger(l, "ServerAtas"); // So that the output can be differentiated based on realm
//
//        GenericPackager clientPkg = new GenericPackager("fields.xml");
//        clientPkg.setLogger(l, "clientbawaanatas");// So that the output can be differentiated based on realm
//        // Simulate a server and listen on a port
//        ISOMsg count = PackISOMessage.buildISOMessage(ISOMsg.INCOMING);
//        ISOChannel serverChannel = new ASCIIChannel(serverPkg);
//        ((ASCIIChannel) serverChannel).setHeader(com.jatismobile.plugin.iso8583.utils.ISOHeader.generateHeader(133));
//        // AN equivalent in your channel adaptor deploy file would be
//        // <channel class="org.jpos.iso.channel.ASCIIChannel"
//        // packager="org.jpos.iso.packager.GenericPackager"
//        // header="ISO70100000"> .....
//        // This is evident from the ChanelAdaptor code
////         QFactory.invoke (channel, "setHeader", e.getAttributeValue ("header"));
//        ((ASCIIChannel) serverChannel).setLogger(l, "ServerBawah");
//        ISOServer server = new ISOServer(1423, (ServerChannel) serverChannel,
//                new ThreadPool(10, 100, "serverListeningThread"));
//        server.addISORequestListener(new ISORequestListener() {
//            // If the client sends a message, the server will respond with and approval if its a request message
//            @Override
//            public boolean process(ISOSource source, ISOMsg msg) {
//                try {
////                    if (!msg.isRequest()) {
//                        msg.setResponseMTI();
//                        msg.set(39, "00");
//                        msg.set(48,"0000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA");
//                        msg.set(60,"0000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA");
//                        msg.set(61,"0000001089110518010001089110518   00000172050000002500151895794         8988801089110518TANTY LESTARI                 0905  JAKARTA UTARA       0000000510000000000510000000000000001895812         8988801089110676RAHMAT ARIEF                  1808  LUWU                0000000800000000000800000000000000001891591         8988801215305289SITI CARYATI                  0903  JAKARTA TIMUR       0000000425000000000425000000000000001891854         8988801228222732SRI TITIEK INDRAWATI          0902  JAKARTA SELATAN     0000000425000000000425000000000000001125877         8988801255971971SDF                           0401  PEKANBARU           0000000935000000000935000000000000001696724         8988801256652461HARYATI                       1201  YOGYAKARTA          0000002975000000002975000000000000001696957         8988801256654248MA'MUROTUN                    0902  JAKARTA SELATAN     0000004165000000004165000000000000001697213         8988801256656498SUPRIYADI                     0902  JAKARTA");
//                        System.out.println("msg : " + new String(msg.getBytes()));
//                        source.send(msg);
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
//        ASCIIChannel clientChannel = new ASCIIChannel("127.0.0.1", 1423, clientPkg);
//        clientChannel.setHeader(com.jatismobile.plugin.iso8583.utils.ISOHeader.generateHeader(133));
//        clientChannel.setLogger(l, "clientbawaanbawah");
//        clientChannel.connect(); // connect to server, it will be seen in the output console
//        ISOChannel connectChannel = server.getLastConnectedISOChannel();// Since server can have multiple connections,
////        connectChannel.connect();//we get the last one that connected to it.
//        ISOMsg serverInitiatedRequest = PackISOMessage.buildISOMessage(ISOMsg.OUTGOING);
//        serverInitiatedRequest.setHeader(com.jatismobile.plugin.iso8583.utils.ISOHeader.generateHeader(133));
//        connectChannel.send(serverInitiatedRequest); // use the last one connected to send a request message to the client.
//        ISOMsg receivedRequest = clientChannel.receive();// receive the serers request message at the client
//        ISOMsg clientResponse = (ISOMsg) receivedRequest.clone();
//        clientResponse.setResponseMTI();
//        clientResponse.set(39, "00");
//        clientChannel.send(clientResponse); // send the response to server
//
//    }
//
//}