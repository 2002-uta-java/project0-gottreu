package com.revature.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
	private Timestamp timestamp;
	private BigDecimal amount;
	private String description;
	public String toString() {
		return timestamp + " " + amount + " " + description;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
