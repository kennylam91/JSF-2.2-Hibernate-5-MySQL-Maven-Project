package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.constant.Constant;

public class JdbcConnection {

	private static Connection con = null;

	public static Connection getConnection() {
		if (con == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(Constant.ORACLE_DB_URL, Constant.ORACLE_DB_USERNAME,
						Constant.ORACLE_DB_PASSWORD);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return con;
	}
}
