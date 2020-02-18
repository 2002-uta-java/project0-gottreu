package com.revature.models;

import java.util.List;

public class Account {
	private List<User> users;
	private List<Transaction> transactions;
	private String description;
	public List<User> getUser() {
		return users;
	}
	public void setUser(List<User> user) {
		this.users = user;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
