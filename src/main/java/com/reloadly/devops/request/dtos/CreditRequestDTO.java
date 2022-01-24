package com.reloadly.devops.request.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreditRequestDTO extends TransactionDTO {
	private String creditorName;

}
