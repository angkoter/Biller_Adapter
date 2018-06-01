package com.jatismobile.plugin.iso8583.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

public class StreamToISOMessage {
	
	public void process (InputStream response, GenericPackager packager) throws IOException, ISOException {
		
		DataInputStream in = new DataInputStream(response);
        int bytesRead = 0;

        byte[] messageByte = new byte[9680];
		messageByte[0] = in.readByte();
        messageByte[1] = in.readByte();
        ByteBuffer byteBuffer = ByteBuffer.wrap(messageByte, 0, 2);

        int bytesToRead = byteBuffer.getShort();

        boolean end = false;
		String messageString = "";
		while(!end)
        {
            bytesRead = in.read(messageByte);
            messageString += new String(messageByte, 0, bytesRead);
            if (messageString.length() == bytesToRead )
            {
                end = true;
            }
        }
		byteBuffer.clear();
		in.close();

		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		isoMsg.unpack(messageString.getBytes());
		
//		handler.receive(isoMsg);
	}
}
