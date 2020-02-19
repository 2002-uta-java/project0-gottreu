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
					case "create":
						System.out.println("not implemented");
						break;
					case "login":
						System.out.println("username > ");
						String username = sc.nextLine();
						System.out.println("password > ");
						String password = sc.nextLine();
						currentUser = login(username, password);
						if (currentUser == null) {
							System.out.println("Login failed.");
						} else {
							System.out.println(
									"Welcome " + currentUser.getLegalName());
							// AccountService as = new AccountService();
							List<Account> accounts = as
									.getAccountsForUser(currentUser);
							if (accounts != null && accounts.size() > 0) {
								currentAccount = accounts.get(0);
								System.out.println(
										"Using account " + currentAccount);
							} else {
								System.err.println("no account exists");
								System.err.println(
										"account creation unimplemented");
							}
						}
						break;
					}
				} else {
					System.out
							.println("[balance | deposit | withdraw | logout]");
					s = sc.nextLine();
					switch (s) {
					case "balance":
						BigDecimal bal;
						bal = as.getBalance(currentAccount);
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
								if (depAmt.compareTo(BigDecimal.ZERO) > 0
										&& depAmt.scale() <= 2) {
									needsInput = false;
								}
							} else {
								// System.err.println("invalid ");
								sc.nextLine();
							}

						} while (needsInput);
						as.addDeposit(currentAccount, depAmt);
						System.out.println("Depositing " + depAmt);
						System.out.println("New balance is "
								+ as.getBalance(currentAccount));
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
								if (amt.compareTo(BigDecimal.ZERO) > 0
										&& amt.scale() <= 2) {
									needsInput = false;
								}
							} else {
								sc.nextLine();
							}

						} while (needsInput);
						as.makeWithdrawal(currentAccount, amt);
						System.out.println("Withdrawing " + amt);
						System.out.println("New balance is "
								+ as.getBalance(currentAccount));
						break;
					}
					case "logout":
						System.out.println(
								"logging out " + currentUser.getLegalName());
						currentUser = null;
						currentAccount = null;
					}
				}
			} catch (NoSuchElementException e) {
				System.err.println("exiting program\n" + e);
				exit = true;
			}
		}
		sc.close();
	}
	/*
	 * 1) create a user account with a unique email and/or username (must have
	 * multiple users)
	 * 
	 * 2) secure my account using a password
	 * 
	 * X3) log in/log out
	 * 
	 * 4) create a bank account associated with each user
	 * 
	 * 5) deposit money
	 * 
	 * 6) withdraw money (no negative balances!)
	 * 
	 * X7) view account balance
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
