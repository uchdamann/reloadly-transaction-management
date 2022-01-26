package com.reloadly.devops.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.reloadly.devops.request.dtos.CreditRequestDTO;
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
@RequestMapping("api/credit/v1")
@SecurityRequirement(name = "ChannelCode")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CreditController {
	
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private UtilitiesAndTweaks util;
	
	@PostMapping("/transact-credit")
	public ResponseDTO<String> credit(@RequestBody @Valid CreditRequestDTO transactionDTO,
			HttpServletRequest req){
		util.channelCodeHandler(req);
		
		return transactionService.makeTransaction(transactionDTO);
	}
}