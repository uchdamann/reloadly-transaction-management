package com.reloadly.devops.serviceImpl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.reloadly.devops.request.dtos.NotificationDTO;
import com.reloadly.devops.services.NotificationService;
import com.reloadly.devops.utilities.ExternalCalls;

import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
	private final ExternalCalls extCalls;

	@Async
	@Override
	public <T extends NotificationDTO> void notifyUser(T notificationDTO) {
		extCalls.notify(notificationDTO);
	}
	
}