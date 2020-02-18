package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private static Connection connection;

	public static Connection getConnection() throws SQLException {
		// TODO read from file
		String url = "jdbc:postgresql://localhost:5432/bank";
		String username = "bankuser";
		String password = "9gf5dvj7gd";

		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(url, username, password);
		}
		return connection;
	}

}
