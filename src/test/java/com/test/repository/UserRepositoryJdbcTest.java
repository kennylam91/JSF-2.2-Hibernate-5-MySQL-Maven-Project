package com.test.repository;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.beans.User;
import com.constant.AUTHORITIES;
import com.constant.Constant;
import com.repository.impl.UserRepositoryImplJdbc;
import com.util.JdbcConnection;

public class UserRepositoryJdbcTest {
	
	UserRepositoryImplJdbc userRepo;
	
	@Before
	public void init() {
		JdbcConnection.setProperties(Constant.POSTGRES_TEST_DB_URL, Constant.POSTGRES_TEST_DB_USERNAME,
				Constant.POSTGRES_TEST_DB_PASSWORD);
		userRepo = new UserRepositoryImplJdbc();
	}
	
	@Test
	public void testSaveUserAndFindUserByEmailWithValidEmail() {
		User user = User.builder().username("phamlam").password("phamlam").email("phamlam@gmail.com")
				.authority(AUTHORITIES.STUDENT_ROLE).build();
		userRepo.save(user);
		String email = "phamlam@gmail.com";
		User userFound = userRepo.findUserByEmail(email);
		String expected = "phamlam@gmail.com";
		String actual = userFound.getEmail();
		assertTrue(expected.contentEquals(actual));
	}
	@Test
	public void testFindUserByEmailWithInvalidEmail() {
		String email = "phamlam1@gmail.com";
		User user = userRepo.findUserByEmail(email);
		assertNull(user);
	}
}
