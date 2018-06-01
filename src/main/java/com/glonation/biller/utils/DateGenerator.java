package com.glonation.biller.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateGenerator {
	public static String getDate(String paramDate, String dateFormatTemplate) {
		String output = "";
		try {
			Date tempDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(paramDate);
			SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatTemplate);
			output = dateFormat.format(tempDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
}
