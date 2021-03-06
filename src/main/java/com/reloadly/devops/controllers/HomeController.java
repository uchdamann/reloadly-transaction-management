package com.reloadly.devops.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.reloadly.devops.request.dtos.DebitRequestDTO;
import com.reloadly.devops.request.dtos.TransferRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reloadly.devops.response.dtos.ResponseDTO;
import com.reloadly.devops.services.TransactionService;
import com.reloadly.devops.utilities.UtilitiesAndTweaks;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Validated
@RestController
@RequestMapping("api/debit/v1")
@SecurityRequirement(name = "ChannelCode")
@SecurityRequirement(name = "Authorization")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HomeController {
	
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private UtilitiesAndTweaks util;

	@PostMapping("/transact-debit")
	public ResponseDTO<String> debit(@RequestBody @Valid DebitRequestDTO transactionDTO,
									 HttpServletRequest req){
		util.channelCodeHandler(req);

		return transactionService.makeTransaction(transactionDTO);
	}

	@PostMapping("/transact-transfer")
	public ResponseDTO<String> transfer(@RequestBody @Valid TransferRequestDTO transactionDTO,
										HttpServletRequest req){
		util.channelCodeHandler(req);

		return transactionService.makeTransaction(transactionDTO);
	}
}