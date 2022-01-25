package com.reloadly.devops.request.dtos;

import java.math.BigDecimal;
import java.util.Date;

import com.reloadly.devops.constants.TransactionType;

import lombok.Data;

@Data
public class TransactionDTO {
	private String accountNumber;
	private Date transactionDate;
	private TransactionType transactionType;
	private BigDecimal amount;
	private String username;

}
