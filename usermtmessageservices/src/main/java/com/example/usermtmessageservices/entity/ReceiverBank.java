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
@Table(name = "receiverbank")
public class ReceiverBank {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@Column(name = "receivername", length = 20)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String receivername;

	@NotEmpty
	@Column(name = "accountnumber", length = 20)
	@ApiModelProperty(notes = "The application-specific Account Number")
	private String accountnumber;
	
	@NotEmpty
	@Column(name = "bankname", length = 20)
	@ApiModelProperty(notes = "The application-specific Account Number")
	private String bankName;
	
	@NotEmpty
	@Column(name = "ifsccode", length = 20)
	@ApiModelProperty(notes = "The application-specific Account Number")
	private String ifsccode;
	
	
	@Column(name = "accountbalance", length = 20)
	@ApiModelProperty(notes = "The application-specific Account Number")
	private int accountbalance;


	public ReceiverBank() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}





	public String getReceivername() {
		return receivername;
	}


	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}


	public String getAccountnumber() {
		return accountnumber;
	}


	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}


	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	public String getIfsccode() {
		return ifsccode;
	}


	public void setIfsccode(String ifsccode) {
		this.ifsccode = ifsccode;
	}


	public int getAccountbalance() {
		return accountbalance;
	}


	public void setAccountbalance(int accountbalance) {
		this.accountbalance = accountbalance;
	}
	

}
