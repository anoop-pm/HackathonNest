package com.example.MTtoTextService.entity;

import org.springframework.stereotype.Component;

@Component
public class MTMessage {
	
	String message;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
