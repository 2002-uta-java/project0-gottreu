package com.revature.models;

public class Account {
	//private List<User> users;
	//private List<Transaction> transactions;
	private String description;
	private int id;
	
	public String toString() {
		return description;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
