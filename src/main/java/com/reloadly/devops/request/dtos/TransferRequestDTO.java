package com.reloadly.devops.request.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransferRequestDTO {
	private String sourceAccountNumber;
	private String destinationAccountNumber;
	private BigDecimal amount;
	private String authoriserUsername;
	private String authoriserFirstName;

}
