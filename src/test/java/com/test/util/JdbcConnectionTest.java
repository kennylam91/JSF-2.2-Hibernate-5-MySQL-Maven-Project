package com.test.util;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.Test;

import com.constant.Constant;
import com.util.JdbcConnection;

public class JdbcConnectionTest {

	@Test
	public void testGetConnection() {
		Connection con = JdbcConnection.getConnection();
		assertNotNull(con);
	}
	@Test
	public void testsetProperties() {
		String testUrl = Constant.POSTGRES_TEST_DB_URL;
		String testUsername = Constant.POSTGRES_TEST_DB_USERNAME;
		String testPassword = Constant.POSTGRES_TEST_DB_PASSWORD;
		JdbcConnection.setProperties(testUrl, testUsername, testPassword);
		Connection testCon = JdbcConnection.getConnection();
		assertNotNull(testCon);
	}
}
