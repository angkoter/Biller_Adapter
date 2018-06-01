package com.jatismobile.plugin.iso8583.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.lang3.StringUtils;

import com.jatismobile.plugin.iso8583.DBConfiguration;

public class BillerRealResponse {
	public static String getInquiryResponse(String trxId, DBConfiguration config) {
		String query = "select BillerRealResponse from InquiryResponse where TrxId = ?";
		PreparedStatement statement = null;
		Connection con = null;
		String output = null;
		try {
			con = DBConnection.dbConnection(config);
			statement = con.prepareStatement(query);
			statement.setString(1, trxId);
			ResultSet temp = statement.executeQuery();
			while (temp.next()) {
				output = temp.getString("BillerRealResponse");
			}
			statement.close();
			con.close();
		} catch (Exception e) {
			return null;
		}
		
		output = org.apache.commons.lang3.StringEscapeUtils.unescapeJava(output);
		
		return output;
	}
}
