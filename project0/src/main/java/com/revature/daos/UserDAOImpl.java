package com.revature.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.daos.UserDAO;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class UserDAOImpl implements UserDAO {

	@Override
	public boolean checkPassword(String username, String password) {
		String query = "select crypt(?, (select password from person where username=?)) "
				+ " = password from person where username = ?;";
		ResultSet rs = null;
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(query)) {
			ps.setString(1, password);
			ps.setString(2, username);
			ps.setString(3, username);

			rs = ps.executeQuery();

			if (rs.next()) {
				Boolean b = rs.getBoolean(1);
				return b;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public User findUserByUsername(String username) {
		User u = null;
		String query = "select id, username, legal_name from person where username = ?;";
		ResultSet rs = null;
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(query)) {
			ps.setString(1, username);

			rs = ps.executeQuery();

			if (rs.next()) {
				u = new User();
				u.setId(rs.getInt("id"));
				u.setLegalName(rs.getString("legal_name"));
				u.setUsername(rs.getString("username"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}

	@Override
	public boolean addPerson(String legalName, String username, String password) {
		String call = "{call add_person(?, ?, ?)}";

		ResultSet rs = null;

		try (Connection c = ConnectionUtil.getConnection();
				CallableStatement cs = c.prepareCall(call)) {
			cs.setString(1, legalName);
			cs.setString(2, username);
			cs.setString(3, password);

			cs.execute();

			rs = cs.getResultSet();

			while (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			// If the username is not unique, we get an exception.
			if (e.getMessage().matches("(.|\\n)*person_username_key(.|\\n)*")) {
				return false;
			}
			e.printStackTrace();
		}
		return false;
	}

}
