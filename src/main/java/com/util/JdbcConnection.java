package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.constant.Constant;

public class JdbcConnection {

	private static Connection con = null;

	static String databaseUrl = Constant.POSTGRES_DB_URL;
	static String username = Constant.POSTGRES_DB_USERNAME;
	static String password = Constant.POSTGRES_DB_PASSWORD;

	public static Connection getConnection() {
		if (con == null) {
			try {
				con = DriverManager.getConnection(databaseUrl, username, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}

	public static void setProperties(String url, String un, String pw) {
		databaseUrl = url;
		username = un;
		password = pw;
	}
}
