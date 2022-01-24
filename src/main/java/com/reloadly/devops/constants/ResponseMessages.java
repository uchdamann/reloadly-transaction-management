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
	ACCOUNT_CREATED(200, "Account was created successfully"),
	INVALIDFIELDS(81, "Selected field(s) has/have invalid value(s) in payload"),
	USERNAMEMISMATCH(73, "User mismatch"),
	DATAINTEGRITYERROR(30, "Data integrity exception"),
	OPTIMISTICLOCK(419, "Concurrent update on record"),
	EXTERNAL_SERVER_UNAVAILABLE(420, "External server cannot be reached");
	
	private int code;
	private String message;
}