package com.jatismobile.plugin.iso8583;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.jatismobile.plugin.iso8583.utils.DBConnection;

public class SuccessResponseJatelindo {
	private static SuccessResponseJatelindo rc;
	private String responseCode;

	public SuccessResponseJatelindo(DBConfiguration config) {
		this.responseCode = getResponse(config);
	}

	public static SuccessResponseJatelindo getResponseCode (DBConfiguration config) {
		if (rc == null) {
			rc = new SuccessResponseJatelindo(config);
		}
		return rc;
	}
	
	private String getResponse(DBConfiguration config) {
		String query = "SELECT DISTINCT BillerStatusCode FROM billerstatusmapping WHERE JatisStatusCode = '2001' and BillerId = (SELECT BillerId FROM biller WHERE BillerName like ? limit 1)";
		PreparedStatement statement = null;
		Connection con = null;
		String output = null;
		try {
			con = DBConnection.dbConnection(config);
			statement = con.prepareStatement(query);
			statement.setString(1, "%Jatelindo%");
			ResultSet temp = statement.executeQuery();
			while (temp.next()) {
				output = temp.getString("BillerStatusCode");
			}
			statement.close();
			con.close();
		} catch (Exception e) {
			return null;
		}
//		System.out.println("RC : " + output);
		return output;
	}

	public String getRc() {
		return responseCode;
	}
}