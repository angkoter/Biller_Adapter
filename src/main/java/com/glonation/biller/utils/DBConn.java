package com.glonation.biller.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConn {
	
	public Statement stt;
	public PreparedStatement stat;
	public Connection con;
	
	private String dbUrl;
	private String dbName;
	private String user;
	private String password;
	
	public DBConn (String dbUrl, String dbName, String user, String password) {
		this.dbUrl = dbUrl;
		this.dbName = dbName;
		this.user = user;
		this.password = password;
	}
	
	public void connectDB(){
//		String url = "jdbc:mysql://"+dbUrl+dbName;
		String url = "jdbc:mariadb://"+dbUrl+dbName;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, user, password);
			//System.out.println("DB connection successful for database \"" + db_name + "\"");
			stt = con.createStatement();
		}
		catch (Exception e) {
			System.out.println("Failed to connect to DB..");
			e.printStackTrace();
		}
	}
	
	public ResultSet res (String q){
		ResultSet res = null;
		try {
			res = stt.executeQuery(q);
			res.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public boolean stat (String q){
		boolean res = false;
		try {
			stat = con.prepareStatement(q);
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public void setString (int x,String s){
		try {
			stat.setString(x,s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setInt (int x,int s){
		try {
			stat.setInt(x,s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setDoub(int x, double d){
		try {
			stat.setDouble(x, d);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public boolean exec () throws SQLException{
		boolean cek = false;
		stat.execute();
		cek = true;
	
		return cek;
	}
	
	public void closeDB(){
		try {
			stt.close();
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void conClose() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
