package com.reloadly.devops.response.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UpdateBalanceDTO {
	private String accountNumber;
	private BigDecimal amount;

}
