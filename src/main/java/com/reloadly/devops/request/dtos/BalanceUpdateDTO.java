package com.reloadly.devops.request.dtos;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BalanceUpdateDTO {
	private String accountNumber;
	private BigDecimal amount;

}
