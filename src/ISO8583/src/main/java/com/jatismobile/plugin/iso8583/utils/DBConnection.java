package com.jatismobile.plugin.iso8583.utils;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.jatismobile.plugin.iso8583.DBConfiguration;

import java.sql.Connection;

public class DBConnection {

	private static Connection con;

	public static Connection dbConnection(DBConfiguration config)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://" + config.getDbUrl() + config.getDbName();
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(url, config.getUser(), config.getPassword());
		return con;
	}
}
