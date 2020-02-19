package com.revature.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ConnectionUtil {
	private static Connection connection;

	public static Connection getConnection() throws SQLException {
		String url = null;
		String username = null;
		String password = null;
		String file = "db-creds";

		if ("1".equals(System.getenv("JUNIT_TEST"))) {
			//url = "jdbc:h2:~/test";
			file = "testing-creds";
		}
		FileReader fr;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.err.println("can't read " + file);
			return null;
		}
		Scanner sc = new Scanner(fr);

		url = sc.nextLine();
		username = sc.nextLine();
		password = sc.nextLine();
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(url, username, password);
		}
		return connection;
	}

}
