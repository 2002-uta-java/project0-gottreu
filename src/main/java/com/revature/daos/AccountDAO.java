package com.revature.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;

public interface AccountDAO {

	List<Account> getAccountsForUser(User user);

	BigDecimal getBalance(Account a);

	boolean addTransaction(Account a, BigDecimal amt, Timestamp ts, String desc);

	void createAccount(int id, String desc);

}
