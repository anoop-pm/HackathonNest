package com.example.MTtoTextService.service;

import org.springframework.stereotype.Component;

@Component
public class Response {
	
	int status;
	String message;
	String mxMessage;

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
	public String getMxMessage() {
		return mxMessage;
	}
	public void setMxMessage(String mxMessage) {
		this.mxMessage = mxMessage;
	}
	

}
