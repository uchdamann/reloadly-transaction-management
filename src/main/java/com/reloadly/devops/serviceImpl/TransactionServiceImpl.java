package com.reloadly.devops.serviceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import static com.reloadly.devops.constants.TransactionType.*;
import static com.reloadly.devops.constants.ResponseMessages.*;

import static com.reloadly.devops.constants.NotificationType.*;
import com.reloadly.devops.exceptions.AppException;
import com.reloadly.devops.exceptions.InvalidAccountNumberException;
import com.reloadly.devops.request.dtos.CreditRequestDTO;
import com.reloadly.devops.request.dtos.DebitRequestDTO;
import com.reloadly.devops.request.dtos.TransactionDTO;
import com.reloadly.devops.request.dtos.TransactionNotificationDTO;
import com.reloadly.devops.request.dtos.UpdateNotificationDTO;
import com.reloadly.devops.response.dtos.ResponseDTO;
import com.reloadly.devops.services.NotificationService;
import com.reloadly.devops.services.TransactionService;
import com.reloadly.devops.utilities.ExternalCalls;

import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
	private final ExternalCalls extCalls;
	private final NotificationService notification;

	@Override
	public <T extends TransactionDTO> ResponseDTO<String> makeTransaction(T transactionReq) {
		TransactionNotificationDTO transactionNotificationDTO = new TransactionNotificationDTO();
		transactionNotificationDTO.setNotificationType(TRANSACTION);
		
		ResponseDTO<UpdateNotificationDTO> response = null;
		BigDecimal balance;
		BigDecimal newBalance;
		
		Map<String, Object> validAccount = extCalls.validateAccountNumberExternally(transactionReq.getAccountNumber())
				.orElseThrow(() -> new AppException("Error validating account number: " + transactionReq.getAccountNumber()));
				
		if(!(Boolean) validAccount.get("valid")) {
			throw new InvalidAccountNumberException();
		}
		
		switch (transactionReq.getTransactionType()) {
		
		case CREDIT:
			CreditRequestDTO creditRequestDTO = (CreditRequestDTO) transactionReq;
			balance = (BigDecimal)validAccount.get("balance");
			newBalance = balance.add(creditRequestDTO.getAmount());

			response = extCalls.updateBalanceExt(transactionReq.getAccountNumber(), newBalance);
			
			if(response.getCode() != "00") {
				throw new AppException("Error updating account balance");
			}
			
			transactionNotificationDTO.setCreatedOn(new Date());
			transactionNotificationDTO.setFirstname(response.getData().getFirstName());
			transactionNotificationDTO.setUsername(response.getData().getUsername());
			transactionNotificationDTO.setInitialBalance(balance);
			transactionNotificationDTO.setTransactionAmount(creditRequestDTO.getAmount());
			transactionNotificationDTO.setTransactionType(CREDIT);

			notification.notifyUser(transactionNotificationDTO);
		
		case DEBIT:
			DebitRequestDTO debitRequestDTO = (DebitRequestDTO) transactionReq;
			balance = (BigDecimal)validAccount.get("balance");
			
			if (debitRequestDTO.getAmount().doubleValue() > balance.doubleValue()){
				throw new AppException("You have an insufficient account balance");
			}
			newBalance = balance.subtract(debitRequestDTO.getAmount());		

			response = extCalls.updateBalanceExt(transactionReq.getAccountNumber(), newBalance);
			
			if(response.getCode() != "00") {
				throw new AppException("Error updating account balance");
			}
			
			transactionNotificationDTO.setCreatedOn(new Date());
			transactionNotificationDTO.setFirstname(response.getData().getFirstName());
			transactionNotificationDTO.setUsername(response.getData().getUsername());
			transactionNotificationDTO.setInitialBalance(balance);
			transactionNotificationDTO.setTransactionAmount(debitRequestDTO.getAmount());
			transactionNotificationDTO.setTransactionType(CREDIT);
			
			notification.notifyUser(transactionNotificationDTO);
			break;
		default:
			
			break;
		}
		
		return new ResponseDTO<String>(SUCCESS.getCode(), SUCCESS.getMessage(),
				"Transaction was successful and notification sent");
				
	}
	
	
}