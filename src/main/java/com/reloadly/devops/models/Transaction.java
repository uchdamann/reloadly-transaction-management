package com.reloadly.devops.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.reloadly.devops.constants.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Transaction extends CommonFields implements Serializable {
	
	private static final long serialVersionUID = 4428712658080384378L;
	private String narration;
	private BigDecimal amount;
	private String accountNumber;
	private Date transactionDate;
	private BigDecimal accountBalance;
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
}
