package com.revature.daos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.revature.daos.AccountDAO;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {

	@Override
	public List<Account> getAccountsForUser(User user) {
		List<Account> accounts = new Vector<>();
		String query = "select a.id, description from account a "
				+ "join ownership o on a.id = o.account_id "
				+ "	join person p on o.person_id = p.id where p.username = ?;";

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
		// We need to use a Statement, but all our queries require parameters.
		// So we'll use a Statement here, when we shouldn't.
		String query = "select sum(amount) from \"transaction\" where account_id = "
				+ a.getId() + " ;";
		ResultSet rs = null;
		Connection c = null;
		try {
			c = ConnectionUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Statement s = c.createStatement()) {
			rs = s.executeQuery(query);

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
		if (amt.compareTo(BigDecimal.ZERO) < 0) {
			BigDecimal bal = getBalance(a);
			if(bal == null) { return false; }
			if (bal.add(amt).compareTo(BigDecimal.ZERO) < 0) {
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

			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void createAccount(int userId, String desc) {
		String query = "insert into account (description) values (?) returning id;";
		ResultSet rs = null;
		
		Connection c = null;
		int accountId = 0;
		try {
			c = ConnectionUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (PreparedStatement ps = c.prepareStatement(query)) {
			ps.setString(1, desc);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				accountId = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		query = "insert into ownership (person_id, account_id) values (?,?);";
		try (PreparedStatement ps = c.prepareStatement(query)) {
			ps.setInt(1, userId);
			ps.setInt(2, accountId);
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Transaction> getHistory(Account a) {
		List<Transaction> ts = new ArrayList<Transaction>();
		if(a == null) { return ts; }
		String query = "select * from \"transaction\" where account_id = ? order by tstamp";
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

			while (rs.next()) {
				Transaction t = new Transaction();
				t.setAmount(rs.getBigDecimal("amount"));
				t.setTimestamp(rs.getTimestamp("tstamp"));
				t.setDescription(rs.getString("description"));
				ts.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ts;
	}
}
