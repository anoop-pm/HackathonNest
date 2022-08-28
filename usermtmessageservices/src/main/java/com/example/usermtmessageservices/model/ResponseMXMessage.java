package com.example.usermtmessageservices.model;

import org.springframework.stereotype.Component;

@Component
public class ResponseMXMessage {
	

	private int status;
	private String message;
	
	private String mtmessage;
	
	private String mxmessage;

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
	public String getMtmessage() {
		return mtmessage;
	}
	public void setMtmessage(String mtmessage) {
		this.mtmessage = mtmessage;
	}
	public String getMxmessage() {
		return mxmessage;
	}
	public void setMxmessage(String mxmessage) {
		this.mxmessage = mxmessage;
	}

}
