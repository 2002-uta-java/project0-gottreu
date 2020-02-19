package com.revature.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.revature.daos.AccountDAO;
import com.revature.daos.AccountDAOImpl;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;

public class AccountService {
	private AccountDAO accountDAO = new AccountDAOImpl();

	public List<Account> getAccountsForUser(User user) {
		return accountDAO.getAccountsForUser(user);
	}

	public BigDecimal getBalance(Account a) {
		return accountDAO.getBalance(a);
	}

	public boolean addDeposit(Account a, BigDecimal depAmt) {
		return accountDAO.addTransaction(a, depAmt, Timestamp.valueOf(LocalDateTime.now()),
				"Deposit");
	}

	public boolean makeWithdrawal(Account a, BigDecimal amt) {
		return accountDAO.addTransaction(a, amt.negate(), Timestamp.valueOf(LocalDateTime.now()),
				"Withdrawal");
	}

	public void createAccount(User user, String desc) {
		accountDAO.createAccount(user.getId(), desc);
	}

	public void printHistory(Account a) {
		List<Transaction> ts = accountDAO.getHistory(a);
		for(Transaction t: ts) {
			System.out.println(t);
		}
	}
}
