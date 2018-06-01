package com.jatismobile.plugin.iso8583.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import com.jatismobile.plugin.iso8583.DBConfiguration;

public class ISOResponseDataInserter {
	public static boolean insert (HashMap<String, Object> data, DBConfiguration config, String type, String isoString, String STAN) {
		
        Date date = new Date();
        Timestamp dateCreated = (new Timestamp(date.getTime()));
		String query = "insert into isoresponsedata (TrxId,STAN,Type,Response,DateCreated)"
		        + " values (?, ?, ?,?,?)";
		
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = DBConnection.dbConnection(config);
			statement = con.prepareStatement(query);
			statement.setString(1, data.get("INQUIRY_ID").toString());
			statement.setString(2, STAN);
			statement.setString(3, type);
			statement.setString(4, isoString);
			statement.setString(5, dateCreated.toString());
			statement.execute();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				statement.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main (String[] args) {
		
	}
}
