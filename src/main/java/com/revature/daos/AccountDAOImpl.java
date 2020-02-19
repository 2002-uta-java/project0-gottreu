package com.revature.daos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import com.revature.daos.AccountDAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {

	@Override
	public List<Account> getAccountsForUser(User user) {
		// User u = null;
		List<Account> accounts = new Vector<>();
		String query = "select a.id, description from account a " + "join ownership o on a.id = o.account_id "
				+ "	join person p on o.person_id = p.id " + "	where p.username = ?;";

		ResultSet rs = null;
		Connection c = null;
		try {
			c = ConnectionUtil.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try (PreparedStatement ps = c.prepareStatement(query)) {
			ps.setString(1, user.getUsername());

			rs = ps.executeQuery();

			while (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setDescription(rs.getString("description"));
				accounts.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accounts;
	}

	@Override
	public BigDecimal getBalance(Account a) {
		String query = "select sum(amount) from \"transaction\" t " + "	where account_id = ?;";
		ResultSet rs = null;
		Connection c = null;
		try {
			c = ConnectionUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (PreparedStatement ps = c.prepareStatement(query)) {
			ps.setInt(1, a.getId());

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getBigDecimal(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addTransaction(Account a, BigDecimal amt, Timestamp ts, String desc) {
		String query = "insert into \"transaction\" (account_id, tstamp, amount, description) "
				+ " values (?, ?, ?, ?);";
		// ResultSet rs = null;
		if (amt.compareTo(BigDecimal.ZERO) < 0) {
			BigDecimal bal = getBalance(a);
			if (bal.add(amt).compareTo(BigDecimal.ZERO) < 0) {
				System.err.println("insufficient funds");
				return false;
			}
		}
		Connection c = null;
		try {
			c = ConnectionUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (PreparedStatement ps = c.prepareStatement(query)) {
			ps.setInt(1, a.getId());
			ps.setTimestamp(2, ts);
			ps.setBigDecimal(3, amt);
			ps.setString(4, desc);

			return ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
