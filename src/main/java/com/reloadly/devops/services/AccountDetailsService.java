package com.reloadly.devops.services;

import java.util.List;

import com.reloadly.devops.response.dtos.AccountDetailsDTO;
import com.reloadly.devops.response.dtos.ResponseDTO;
import com.reloadly.devops.response.dtos.TransactionUpdateDTO;

public interface AccountDetailsService {
	public ResponseDTO<AccountDetailsDTO> checkAccountBalance();
	public ResponseDTO<List<TransactionUpdateDTO>> getAccountStatement();

}