package com.test.util;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.Test;

import com.util.JdbcConnection;

public class JdbcConnectionTest {

	@Test
	public void testGetConnection() {
		Connection con = JdbcConnection.getConnection();
		assertNotNull(con);
	}
}
