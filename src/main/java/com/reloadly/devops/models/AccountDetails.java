package com.reloadly.devops.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.reloadly.devops.constants.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountDetails extends CommonFields implements Serializable{
	private static final long serialVersionUID = 8679286295229398154L;
	@Column(unique = true)
	private String accountNumber;
	private BigDecimal balance;
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	@OneToMany(mappedBy = "accounDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Transaction> transactions;
	private String username;
}