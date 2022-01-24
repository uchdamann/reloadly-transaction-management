package com.reloadly.devops.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseMessages {
	ERROR(99, "Sorry an error occurred"), 
	SUCCESS(100, "Transaction Successful"),
	MINIMUM_OPENING_BALANCE(112, "Your initial deposit cannot be less than "),
	ACCOUNT_CREATED(200, "Account was created successfully");
	
	private int code;
	private String message;
}