package com.reloadly.devops.request.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TransferRequestDTO extends TransactionDTO {
	private String destinationAccountNumber;

}
