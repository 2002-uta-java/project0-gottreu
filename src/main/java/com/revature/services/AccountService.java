package com.revature.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.revature.daos.AccountDAO;
import com.revature.daos.AccountDAOImpl;
import com.revature.models.Account;
import com.revature.models.User;

public class AccountService {
	private AccountDAO accountDAO = new AccountDAOImpl();

	public List<Account> getAccountsForUser(User user) {
		return accountDAO.getAccountsForUser(user);
	}

	public BigDecimal getBalance(Account a) {
		return accountDAO.getBalance(a);
	}

	public void addDeposit(Account a, BigDecimal depAmt) {
		accountDAO.addTransaction(a, depAmt,
				Timestamp.valueOf(LocalDateTime.now()), "Deposit");
	}

	public void makeWithdrawal(Account a, BigDecimal amt) {
		accountDAO.addTransaction(a, amt.negate(),
				Timestamp.valueOf(LocalDateTime.now()), "Withdrawal");
	}
}
