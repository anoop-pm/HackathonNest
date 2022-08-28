package com.example.usermtmessageservices.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@Column(name = "username", length = 20)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String username;

	@NotEmpty
	@Column(name = "accountnumber", length = 20)
	@ApiModelProperty(notes = "The application-specific Account Number")
	private String accountnumber;
	

	@Column(name = "accountbalance", length = 20)
	@ApiModelProperty(notes = "The application-specific Account balance")
	private int accountbalance;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public int getAccountbalance() {
		return accountbalance;
	}

	public void setAccountbalance(int accountbalance) {
		this.accountbalance = accountbalance;
	}
	

}
