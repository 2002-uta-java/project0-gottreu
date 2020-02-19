package com.revature.services;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.h2.tools.RunScript;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.daos.UserDAO;
import com.revature.daos.UserDAOImpl;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class UserServiceTest {
	// private UserDAO ud = new UserDAOImpl();
	private UserService us = new UserService();

	@BeforeClass
	public static void setUp() throws SQLException, FileNotFoundException {
		try (Connection c = ConnectionUtil.getConnection()) {
			RunScript.execute(c, new FileReader("setup.sql"));
		}
	}

	@AfterClass
	public static void tearDown() throws SQLException, FileNotFoundException {
		try (Connection c = ConnectionUtil.getConnection()) {
			RunScript.execute(c, new FileReader("teardown.sql"));
		}
	}

	@Test
	public void test() {
		// fail("Not yet implemented");
		Map<String, String> e = System.getenv();
		for (String k : e.keySet()) {
			// System.out.println(k + " -> " + e.get(k));
		}
		assertTrue(true);
	}

	@Test
	public void checkPasswordTest() {
		assertTrue(us.checkPassword("gottreu", "security"));
		assertFalse(us.checkPassword("gottreu", "foo"));
	}

	@Test
	public void findUserByUsernameTest() {
		assertNotNull(us.findUserByUsername("gottreu"));
		assertNull(us.findUserByUsername("reginald"));
	}

	@Test
	public void addPersonTest() {
		assertTrue(us.addPerson("x", "someuser", "asdfsdf"));
		assertFalse(us.addPerson("ln", "gottreu", "sfsdf"));
	}

}
