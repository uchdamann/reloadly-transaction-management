package com.reloadly.devops.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reloadly.devops.request.dtos.TransactionDTO;
import com.reloadly.devops.response.dtos.ResponseDTO;
import com.reloadly.devops.services.TransactionService;
import com.reloadly.devops.utilities.UtilitiesAndTweaks;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Validated
@RestController
@RequestMapping("api/transaction-management/v1")
@SecurityRequirement(name = "ChannelCode")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HomeController {
	
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private UtilitiesAndTweaks util;
	
	@PostMapping("/transaction")
	public ResponseDTO<String> makeTransaction(@RequestBody @Valid TransactionDTO transactionDTO, 
			HttpServletRequest req){
		util.channelCodeHandler(req);
		
		return transactionService.makeTransaction(transactionDTO);
	}

}