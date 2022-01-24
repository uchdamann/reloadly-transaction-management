package com.reloadly.devops.services;

import java.util.List;

import com.reloadly.devops.request.dtos.CreditRequestDTO;
import com.reloadly.devops.request.dtos.DebitRequestDTO;
import com.reloadly.devops.request.dtos.TransferRequestDTO;
import com.reloadly.devops.response.dtos.ResponseDTO;
import com.reloadly.devops.response.dtos.TransactionUpdateDTO;

public interface TransactionService {
	public ResponseDTO<TransactionUpdateDTO> creditAccount(CreditRequestDTO creditRequestDTO);
	public ResponseDTO<TransactionUpdateDTO> debitAccount(DebitRequestDTO debitRequestDTO);
	public ResponseDTO<List<TransactionUpdateDTO>> makeTransfer(TransferRequestDTO transferRequest);
}