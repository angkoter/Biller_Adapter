package com.jatismobile.plugin.iso8583;

public class DBConfiguration {
	
	private static String dbUrl;
	private static String dbName;
	private static String user;
	private static String password;
	private static DBConfiguration config;
	
	public static final synchronized DBConfiguration getConfig() {
		
		if (config == null) {
			config = new DBConfiguration();
		}
		
		return config;
	}
	
	public static DBConfiguration getConfig (String dbUrl, String dbName, String user, String password) {
		
		if (config == null) {
			DBConfiguration.dbUrl = dbUrl;
			DBConfiguration.dbName = dbName;
			DBConfiguration.user = user;
			DBConfiguration.password = password;
			config = new DBConfiguration();
		}
		
		return config;
	}

	public String getDbUrl() {
		return dbUrl;
	}
	public String getDbName() {
		return dbName;
	}
	public String getUser() {
		return user;
	}
	public String getPassword() {
		return password;
	}
}
