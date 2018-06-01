package com.glonation.biller.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.glonation.biller.AdapterSettings;

public class IDGenerator {
	public String generateId(String billerid,String trxId,int lengthId) {
		Date date = new Date();
		Random random = new Random();
		SimpleDateFormat dateFormat = new SimpleDateFormat("ssddmmHH");
		String stringDate = dateFormat.format(date);
			while(stringDate.length()<lengthId){
				stringDate = stringDate + String.valueOf(random.nextInt(9));
			};	

		return stringDate;
	}
	public String generateIdAlfNum(String billerid,String trxId,int lengthId) {
		Date date = new Date();
		Random random = new Random();
		SimpleDateFormat dateFormat = new SimpleDateFormat("ssddmmHH");
		String stringDate = dateFormat.format(date);
		if(lengthId>11){
			String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();
			stringDate =new StringBuilder(new String(stringDate)).insert(0, alphabet.charAt(random.nextInt(26))).toString();
			stringDate =new StringBuilder(new String(stringDate)).insert(3, alphabet.charAt(random.nextInt(26))).toString();
			stringDate =new StringBuilder(new String(stringDate)).insert(9, alphabet.charAt(random.nextInt(26))).toString();
			stringDate =new StringBuilder(new String(stringDate)).insert(10, alphabet.charAt(random.nextInt(26))).toString();
			while(stringDate.length()<lengthId){
				if(random.nextInt(100)%2==1){
					stringDate = stringDate + String.valueOf(alphabet.charAt(random.nextInt(26)));
				}else{
					stringDate = stringDate + String.valueOf(random.nextInt(9));	
				}
				
			};
		}else{
			
			while(stringDate.length()<lengthId){
				stringDate = stringDate + String.valueOf(random.nextInt(9));
			};
		}
		
			

		return stringDate;
	}
	
	
	

	public void saveBillerTrxId(String trxId, String billerTrxId, AdapterSettings Setting) {
		try (Connection conn = connection(Setting)) {
			try (PreparedStatement st = conn.prepareStatement(
					"insert into billertransactionid(TrxId,BillerTrxId) values(?,?)")) {
				st.setString(1, trxId);
				st.setString(2, billerTrxId);
				st.executeUpdate();
			} catch (Exception ex) {
				
				ex.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String getBillerTrxId(String trxId, AdapterSettings Setting) {
		String billerTrxId="";
		try (Connection conn = connection(Setting)) {
			try (PreparedStatement st = conn.prepareStatement(
					"select * from billertransactionid where TrxId=?")) {
				st.setString(1, trxId);	
				try (ResultSet rs = st.executeQuery()) {
	                while (rs.next()) {
	                	billerTrxId = rs.getString("BillerTrxId");
	                }
	            }
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return billerTrxId;
	}

	private Connection connection(AdapterSettings setting) {
		Connection conn;
		AdapterSettings as = setting;
		// System.out.println("db user = " + AdapterSettings.getDbUser());
		String url = "jdbc:mariadb://" + as.getDbUrl() + as.getDbName();
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(url, as.getDbUser(), as.getDbPassword());
			conn.setAutoCommit(true);
		} catch (Exception ex) {
			conn = null;
			ex.printStackTrace();
		}
		return conn;
	}


}
