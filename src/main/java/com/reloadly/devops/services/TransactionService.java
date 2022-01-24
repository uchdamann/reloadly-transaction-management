package com.reloadly.devops.services;

import com.reloadly.devops.request.dtos.TransactionDTO;
import com.reloadly.devops.response.dtos.ResponseDTO;

public interface TransactionService extends CustomerService{
	public <T extends TransactionDTO> ResponseDTO<String> makeTransaction(T transactionReq);
	
//	public ResponseDTO<TransactionUpdateDTO> creditAccount(CreditRequestDTO creditRequestDTO);
//	public ResponseDTO<TransactionUpdateDTO> debitAccount(DebitRequestDTO debitRequestDTO);
//	public ResponseDTO<List<TransactionUpdateDTO>> makeTransfer(TransferRequestDTO transferRequest);
}