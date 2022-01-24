package com.reloadly.devops.request.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DebitRequestDTO {
	private String accountNumber;
	private String creditorName;
	private BigDecimal amount;
}
