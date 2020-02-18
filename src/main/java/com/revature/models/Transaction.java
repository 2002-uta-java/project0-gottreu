package com.revature.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Transaction {
	private OffsetDateTime timestamp;
	private BigDecimal amount;
	private String description;

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
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
