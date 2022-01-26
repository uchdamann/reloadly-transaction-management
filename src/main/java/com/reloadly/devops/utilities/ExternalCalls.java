package com.reloadly.devops.utilities;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.reloadly.devops.exceptions.AppException;
import com.reloadly.devops.request.dtos.BalanceUpdateDTO;
import com.reloadly.devops.request.dtos.NotificationDTO;
import com.reloadly.devops.request.dtos.TransactionNotificationDTO;
import com.reloadly.devops.request.dtos.UpdateNotificationDTO;
import com.reloadly.devops.response.dtos.OauthDTO;
import com.reloadly.devops.response.dtos.ResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.type.TypeReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalCalls {
	private final AppProperties props;
	private final RestTemplate restTemplate;

//	get client credential-based token from auth-server
	public OauthDTO generateAuthServeTokenClientCredentialsGrantType() {
		OauthDTO oauthDTO = null;
		String basicAuth = props.getClientId() + ":" + props.getClientSecret();

		try {
			HttpHeaders requestHeader = new HttpHeaders();
			requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			requestHeader.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(basicAuth.getBytes()));

			log.info("---->>> Initiating process to get oauth token from auth-server");

			MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

			requestBody.put("grant_type", Arrays.asList(props.getGrantTypeClientCredentials()));

			log.info("Auth-server request: {}", requestBody);

			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, requestHeader);
			ResponseEntity<String> response = restTemplate.postForEntity(props.getAuthServerUrl(), requestEntity,
					String.class);
			System.out.println(response.getBody());

			if (response != null) {
				oauthDTO = JsonBuilder.toClass(response.getBody(), OauthDTO.class);
				log.info("---->>> OauthDTO: {}", oauthDTO);
			} else {
				log.info("---->>> No Response from authorization server");
				throw new AppException("---->>> No Response from authorization server");
			}
		}
		catch(RestClientException e) {
			log.info(e.getLocalizedMessage());
		}

		return oauthDTO;
	}

	public Optional<Map<String, Object>> validateAccountNumberExternally(String accountNumber) {
		log.info("---->>> Initiating process to externally validate Account Number:, {}", accountNumber);
		Map<String, Object> responseDTO = null;

		HttpHeaders requestHeader = new HttpHeaders();
		final String URL = props.getAccountManagementUrl() + "getbalance/" + accountNumber;

		HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeader);

		requestHeader.setContentType(MediaType.APPLICATION_JSON);
		requestHeader.add("ChannelCode", props.getWebChannelCode());
		requestHeader.add("Authorization", "Bearer "+generateAuthServeTokenClientCredentialsGrantType().getAccessToken());

		ResponseEntity<String> response = restTemplate.postForEntity(URL, requestEntity, String.class);

		if (response != null) {
			responseDTO = JsonBuilder.toClassTypeReference(response.getBody(),
					new TypeReference<Map<String, Object>>() {});

			return Optional.of(responseDTO);

		} else {
			log.info("---->>> No Response from account management server");
			throw new AppException("---->>> No Response from account management server");
		}
	}

	public ResponseDTO<UpdateNotificationDTO> updateBalanceExt(String accountNumber, BigDecimal amount) {
		ResponseDTO<UpdateNotificationDTO> responseDTO = null;
		
		try {
			BalanceUpdateDTO requestBody = new BalanceUpdateDTO();
			requestBody.setAccountNumber(accountNumber);
			requestBody.setAmount(amount);
			
			final String URL = props.getAccountManagementUrl() + "/update-balance";
			HttpHeaders requestHeader = new HttpHeaders();
			requestHeader.setContentType(MediaType.APPLICATION_JSON);
			requestHeader.add("Authorization",
					"Bearer " + generateAuthServeTokenClientCredentialsGrantType().getAccessToken());
			requestHeader.add("ChannelCode", props.getWebChannelCode());

			HttpEntity<BalanceUpdateDTO> requestEntity = new HttpEntity<>(requestBody, requestHeader);
			
			ResponseEntity<String> response = restTemplate.postForEntity(URL, requestEntity,
					String.class);

			if (response == null) {
				throw new AppException("---->>>");
			}
			
			responseDTO = JsonBuilder.toClassTypeReference(response.getBody(),
					new TypeReference<ResponseDTO<UpdateNotificationDTO>>() {
					});
			
			return responseDTO;
			
		} catch (RestClientException e) {
			log.info(e.getLocalizedMessage());
			throw new AppException(e.getMessage());
		}
	}

	public void notify(NotificationDTO notificationDTO) {
		String URL = props.getMailNotificationUrl();
		HttpEntity<NotificationDTO> requestEntity = null;

		HttpHeaders requestHeader = new HttpHeaders();
		requestHeader.setContentType(MediaType.APPLICATION_JSON);
		requestHeader.add("Authorization",
				"Bearer " + generateAuthServeTokenClientCredentialsGrantType().getAccessToken());
		requestHeader.add("ChannelCode", props.getWebChannelCode());

		try {
			switch (notificationDTO.getNotificationType()) {

			case TRANSACTION:
				URL = URL + "send-transact";
				TransactionNotificationDTO transactionNotificationDTO = (TransactionNotificationDTO) notificationDTO;
				requestEntity = new HttpEntity<>(transactionNotificationDTO, requestHeader);

				break;
			default:
				
				break;
			}

			restTemplate.postForEntity(URL, requestEntity, Void.class);
		} catch (RestClientException e) {
			log.info(e.getLocalizedMessage());
		}
	}
}