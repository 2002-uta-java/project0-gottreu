package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.daos.UserDAO;
import com.revature.utils.ConnectionUtil;

public class UserDAOImpl implements UserDAO {

	@Override
	public boolean checkPassword(String username, String password) {
		// TODO Auto-generated method stub
		/*
		 * select crypt('security', (select password from person where
		 * username='gottreu')) = password from person where username = 'gottreu';
		 */
		/*String query = "select " + 
		 "crypt(?, (select password from person where username=?)) "
				+ "= password, " 
				+ "password, "
				+ "crypt(?, (select password from person where username=?)) "
				+ "from person where username = ?;";*/
		String query = "select " + 
				 "crypt(?, (select password from person where username=?)) "
						+ "= password " 
						+ "from person where username = ?;";
		ResultSet rs = null;
		try (Connection c = ConnectionUtil.getConnection(); 
				PreparedStatement ps = c.prepareStatement(query)) {
			ps.setString(1, password);
			ps.setString(2, username);
			ps.setString(3, username);

			rs = ps.executeQuery();

			if (rs.next()) {
				System.out.println(rs);
				Boolean b = rs.getBoolean(1);
				//String oldP = rs.getString(2);
				//String newP = rs.getString(3);
				System.out.println(b);
				//System.out.println(oldP);
				//System.out.println(newP);
				return b;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}
