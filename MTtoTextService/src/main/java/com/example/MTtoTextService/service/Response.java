package com.example.MTtoTextService.service;

import org.springframework.stereotype.Component;

@Component
public class Response {
	
	int status;
	String message;

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
