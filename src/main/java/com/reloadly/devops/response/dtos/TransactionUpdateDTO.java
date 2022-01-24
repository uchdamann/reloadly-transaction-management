package com.reloadly.devops.response.dtos;

import java.math.BigDecimal;
import java.util.Date;

import com.reloadly.devops.constants.TransactionType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionUpdateDTO {
	private String firstName;
	private String username;
	private BigDecimal amount;
	private String accountNumber;
	private BigDecimal accountBalance;
	private String narration;
	private Date transactionDate;
	private TransactionType transactionType;	

}
