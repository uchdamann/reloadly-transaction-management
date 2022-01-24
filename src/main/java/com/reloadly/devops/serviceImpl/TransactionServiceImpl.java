package com.reloadly.devops.serviceImpl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reloadly.devops.exceptions.AppException;
import com.reloadly.devops.exceptions.InvalidAccountNumberException;
import com.reloadly.devops.request.dtos.CreditRequestDTO;
import com.reloadly.devops.request.dtos.TransactionDTO;
import com.reloadly.devops.response.dtos.ResponseDTO;
import com.reloadly.devops.services.TransactionService;
import com.reloadly.devops.utilities.ExternalCalls;
import static com.reloadly.devops.constants.ResponseMessages.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private ExternalCalls extCalls;

	@Override
	public <T extends TransactionDTO> ResponseDTO<String> makeTransaction(T transactionReq) {
						
		Map<String, Object> validAccount = extCalls.validateAccountNumberExternally(transactionReq.getAccountNumber())
				.orElseThrow(() -> new AppException("Error validating account number: " + transactionReq.getAccountNumber()));
				
		if(!(Boolean) validAccount.get("valid")) {
			throw new InvalidAccountNumberException();
		}
		
		switch (transactionReq.getTransactionType()) {
		
		case CREDIT:
			CreditRequestDTO creditRequestDTO = (CreditRequestDTO) transactionReq;
			BigDecimal balance = (BigDecimal)validAccount.get("balance");
			BigDecimal newBalance = balance.add(creditRequestDTO.getAmount());
			
			return new ResponseDTO<>(SUCCESS.getCode(), SUCCESS.getMessage(), 
					extCalls.updateBalanceExt(transactionReq.getAccountNumber(), newBalance));
		
		case DEBIT:
			
			break;
		default:
			
			break;
		}
		
		
		
		
			
			
//			
//			log.info("--->> User validated successfully");
//			notificationRequest.setFirstname((String) validUser.get("firstname"));
//
//			switch (notificationRequest.getNotificationType()) {
//			case LOGIN:
//				log.info("--->> Sending notification email for login");
//				
//				LoginNotificationDTO loginNotificationDTO = (LoginNotificationDTO) notificationRequest;
//				templateConfigurer.loginNotification(loginNotificationDTO);
//				break;
//
//			case SIGNUP:
//				log.info("--->> Sending notification email for successful user registration");
//
//				AccountCreationNotificationDTO accountCreationNotificationDTO = (AccountCreationNotificationDTO) notificationRequest;
//				templateConfigurer.signupNotification(accountCreationNotificationDTO);
//				break;
//
//			default:
//				log.info("--->> Sending notification email for transaction");
//
//				TransactionNotificationDTO transactionNotificationDTO = (TransactionNotificationDTO) notificationRequest;
//				templateConfigurer.transactionNotification(transactionNotificationDTO);
//				break;
//			}
		
		return null;
	}
	
	
}