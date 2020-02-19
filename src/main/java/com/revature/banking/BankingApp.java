package com.revature.banking;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserService;

public class BankingApp {

	private static AccountService as = new AccountService();
	private static UserService us = new UserService();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s;
		User currentUser = null;
		Account currentAccount = null;
		Boolean exit = false;
		while (!exit) { // || sc.hasNextLine()) {
			try {
				if (currentUser == null) {
					System.out.println("[create | login]");
					s = sc.nextLine();
					// System.out.println(">>" + s + "<<");
					switch (s) {
					case "create": {
						// System.out.println("not implemented");
						System.out.println("Enter legal name");
						String legalName = sc.nextLine();
						String username;
						do {
							System.out.println("Enter username");
							username = sc.nextLine();
						} while (!username.matches("[a-zA-Z]\\w+"));
						String p1 = "A";
						String p2 = "b";
						do {
							System.out.println("Enter password");
							p1 = sc.nextLine();
							System.out.println("Enter password again");
							p2 = sc.nextLine();
						} while (!p1.equals(p2));
						if (us.addPerson(legalName, username, p1)) {
							System.out.println("Username " + username + " added.");
						} else {
							System.out.println("Username already exists.");
						}
						break;
					}
					case "login": {
						System.out.println("username > ");
						String username = sc.nextLine();
						System.out.println("password > ");
						String password = sc.nextLine();
						currentUser = login(username, password);
						if (currentUser == null) {
							System.out.println("Login failed.");
						} else {
							System.out.println("Welcome " + currentUser.getLegalName());
							// AccountService as = new AccountService();
							List<Account> accounts = as.getAccountsForUser(currentUser);
							if (accounts != null && accounts.size() > 0) {
								currentAccount = accounts.get(0);
								System.out.println("Using account " + currentAccount);
							} else {
								System.out.println("no account exists");
								System.out.println("Enter description for new account");
								//System.err.println("account creation unimplemented");
								String desc = sc.nextLine();
								as.createAccount(currentUser, desc);
								currentAccount = as.getAccountsForUser(currentUser).get(0);
							}
						}
						break;
					}
					}
				} else {
					System.out.println("[balance | deposit | withdraw | logout]");
					s = sc.nextLine();
					switch (s) {
					case "balance":
						BigDecimal bal;
						bal = as.getBalance(currentAccount);
						if(bal == null) {
							bal = BigDecimal.ZERO;
						}
						System.out.println("Current balance is " + bal);
						break;
					case "deposit": {
						// System.out.println("not implemented");
						BigDecimal depAmt = new BigDecimal(-1);
						boolean needsInput = true;

						do {
							System.out.println("Enter deposit amount");
							if (sc.hasNextBigDecimal()) {
								depAmt = sc.nextBigDecimal();
								sc.nextLine();
								if (depAmt.compareTo(BigDecimal.ZERO) > 0 && depAmt.scale() <= 2) {
									needsInput = false;
								}
							} else {
								// System.err.println("invalid ");
								sc.nextLine();
							}

						} while (needsInput);
						as.addDeposit(currentAccount, depAmt);
						System.out.println("Depositing " + depAmt);
						System.out.println("New balance is " + as.getBalance(currentAccount));
						break;
					}
					case "withdraw": {
						BigDecimal amt = new BigDecimal(-1);
						boolean needsInput = true;
						do {
							System.out.println("Enter withdrawal amount");
							if (sc.hasNextBigDecimal()) {
								amt = sc.nextBigDecimal();
								sc.nextLine();
								if (amt.compareTo(BigDecimal.ZERO) > 0 && amt.scale() <= 2) {
									needsInput = false;
								}
							} else {
								sc.nextLine();
							}

						} while (needsInput);
						if (as.makeWithdrawal(currentAccount, amt)) {
							System.out.println("Withdrawing " + amt);
							System.out.println("New balance is " + as.getBalance(currentAccount));
						} else {
							System.out.println("Withdrawal failed: insufficient funds");
						}
						break;
					}
					case "logout":
						System.out.println("logging out " + currentUser.getLegalName());
						currentUser = null;
						currentAccount = null;
					}
				}
			} catch (NoSuchElementException e) {
				if (!e.getMessage().matches(".*No line found.*")) {
					// System.err.println("exiting program\n" + e);
					e.printStackTrace();
				} else {
					exit = true;
					System.out.println("Goodbye.  Have a pleasant day.");
				}
			}
		}
		sc.close();
	}
	/*
	 * X 1) create a user account with a unique email and/or username (must have
	 * multiple users)
	 * 
	 * X 2) secure my account using a password
	 * 
	 * X 3) log in/log out
	 * 
	 * X 4) create a bank account associated with each user
	 * 
	 * X5) deposit money
	 * 
	 * x6) withdraw money (no negative balances!)
	 * 
	 * X7) view account balance
	 * 
	 * use at least one X Statement, X PreparedStatement, and X CallableStatement
	 * 
	 * X Use the DAO design pattern
	 * 
	 * X Use proper coding conventions
	 * 
	 * X Ensure that input is thoroughly validated
	 * 
	 * ~ Include JUnit tests to test application code
	 * 
	 * 
	 * 
	 * Your bank can optionally support:
	 * 
	 * Use log4j to replace System.out.println with logging
	 * 
	 * Multiple user bank accounts (checking & savings)
	 * 
	 * Transfer funds functionality between user accounts
	 * 
	 * Joint accounts (a single account with two separate users having access)
	 * 
	 * Ability to view transaction history
	 * 
	 * X Password encryption for added security
	 * 
	 * Include mocking and/or an H2 database for proper unit testing
	 * 
	 * 
	 */

	private static User login(String username, String password) {
		// UserService us = new UserService();
		if (us.checkPassword(username, password)) {
			// System.err.println("password was correct");
			return us.findUserByUsername(username);
		}
		return null;
	}
}
