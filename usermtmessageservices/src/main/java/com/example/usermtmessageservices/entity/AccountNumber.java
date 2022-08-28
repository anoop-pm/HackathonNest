package com.example.usermtmessageservices.entity;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

public class AccountNumber {
	
	@NotEmpty
	@Column(name = "accountnumber", length = 20)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String accountnumber;

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public AccountNumber() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
