package com.jatismobile.plugin.iso8583.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.text.ParseException;

public class DateIncrementer {
	static public String addOneDay(String date) {
		return stringToDateToString1(LocalDate.parse(stringToDateToString2(date)).plusDays(1).toString());
	}

	public static void main(String[] args) {
		System.out.println(addOneDay(new SimpleDateFormat("yyyyMMdd").format(new Date())));
	}
	
    public static String stringToDateToString1 (String dateInString) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat secFormat = new SimpleDateFormat("MMdd");
        String output = null;
        try {

            Date date = formatter.parse(dateInString);
            output = secFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }
    public static String stringToDateToString2(String dateInString) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat secFormat = new SimpleDateFormat("yyyy-MM-dd");
        String output = null;
        try {

            Date date = formatter.parse(dateInString);
            output = secFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }
}