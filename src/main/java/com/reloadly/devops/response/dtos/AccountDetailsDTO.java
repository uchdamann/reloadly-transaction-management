package com.reloadly.devops.response.dtos;

import java.math.BigDecimal;


import lombok.Data;

@Data
public class AccountDetailsDTO {
	private String accountHolderName;
	private String accountNumber;
	private BigDecimal balance;

}
