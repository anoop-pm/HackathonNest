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
@Table(name = "mtmessage")
public class Mtmessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name = "sender", length = 20)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String sender;
	
	@NotEmpty
	@Column(name = "receiver", length = 20)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String receiver;
	
	@NotEmpty
	@Column(name = "currency", length = 20)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String currency;
	
	@NotEmpty
	@Column(name = "amount", length = 20)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String amount;
	
	@NotEmpty
	@Column(name = "refernce", length = 20)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String refernce;
	
	@NotEmpty
	@Column(name = "accountnumber", length = 20)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String accountnumber;
	
	@NotEmpty
	@Column(name = "receiveraccountnumber", length = 20)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String receiverAccountNo;
	
	@NotEmpty
	@Column(name = "bankname", length = 20)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String bankname;
	
	@NotEmpty
	@Column(name = "address", length = 20)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String address;
	

	public Mtmessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRefernce() {
		return refernce;
	}

	public void setRefernce(String refernce) {
		this.refernce = refernce;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReceiverAccountNo() {
		return receiverAccountNo;
	}

	public void setReceiverAccountNo(String receiverAccountNo) {
		this.receiverAccountNo = receiverAccountNo;
	}



	


}
