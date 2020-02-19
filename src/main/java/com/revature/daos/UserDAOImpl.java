package com.revature.daos;

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
		String query = "select " + 
				"crypt(?, (select password from person where username=?)) " + 
				"= password from person where username = ?;";
		ResultSet rs = null;
		try (Connection c = ConnectionUtil.getConnection(); 
				PreparedStatement ps = c.prepareStatement(query)) {
			ps.setString(1, password);
			ps.setString(2, username);
			ps.setString(3, username);

			rs = ps.executeQuery();

			if (rs.next()) {
				//System.out.println(rs);
				Boolean b = rs.getBoolean(1);
				// String oldP = rs.getString(2);
				// String newP = rs.getString(3);
				//System.out.println(b);
				// System.out.println(oldP);
				// System.out.println(newP);
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
		String query = "select username, legal_name " + 
				"from person where username = ?;";
		ResultSet rs = null;
		try (Connection c = ConnectionUtil.getConnection(); 
				PreparedStatement ps = c.prepareStatement(query)) {
			ps.setString(1, username);

			rs = ps.executeQuery();

			if (rs.next()) {
				u = new User();
				u.setLegalName(rs.getString("legal_name"));
				u.setUsername(rs.getString("username"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}

}
