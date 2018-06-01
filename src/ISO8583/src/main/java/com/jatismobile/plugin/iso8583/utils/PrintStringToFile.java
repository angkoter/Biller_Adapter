package com.jatismobile.plugin.iso8583.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PrintStringToFile {
	public static void print (String s) {
		String path = System.getProperties().getProperty("user.dir");
		File file = new File(path + "/newfile.log");
		String content = s;

		try (FileOutputStream fop = new FileOutputStream(file)) {

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
