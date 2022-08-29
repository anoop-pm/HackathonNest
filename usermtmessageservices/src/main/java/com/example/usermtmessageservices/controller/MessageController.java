package com.example.usermtmessageservices.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermtmessageservices.entity.AccountNumber;
import com.example.usermtmessageservices.entity.Mtmessage;
import com.example.usermtmessageservices.entity.ReceiverBank;
import com.example.usermtmessageservices.entity.User;
import com.example.usermtmessageservices.model.ResponseMXMessage;
import com.example.usermtmessageservices.model.ResponseObject;
import com.example.usermtmessageservices.service.MtService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/bankapi")
public class MessageController {
	
	
	
	@Autowired
	private  MtService service;
	
	
	
	@PostMapping("/usercreate")
	@ApiOperation(value = "Register A User")
	public ResponseEntity<ResponseObject> Createuser(
			@ApiParam(value = "Register User object store in database table", required = true) @Valid @RequestBody User user) {
		ResponseObject messagenew = service.createUser(user);
		return new ResponseEntity<ResponseObject>(messagenew, HttpStatus.CREATED);
	}
	
	@PostMapping("/addreceiveraccount")
	@ApiOperation(value = "Register A Receiver User")
	public ResponseEntity<ResponseObject> CreateReceiveruser(
			@ApiParam(value = "Register User object store in database table", required = true) @Valid @RequestBody ReceiverBank receiver) {
		ResponseObject messagenew = service.createReceiver(receiver);
		return new ResponseEntity<ResponseObject>(messagenew, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/deposit")
	@ApiOperation(value = "Deposit to user account")
	public ResponseEntity<ResponseObject> deposit(
			@ApiParam(value = "Deposit to user account", required = true) @Valid @RequestBody User user) {
		ResponseObject messagenew = service.deposit(user);
		return new ResponseEntity<ResponseObject>(messagenew, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/userbalance")
	@ApiOperation(value = "Check user account balance")
	public ResponseEntity<ResponseObject> userBalance(
			@ApiParam(value = "Check user account balance", required = true) @Valid @RequestBody AccountNumber user) {
		ResponseObject messagenew = service.userAccountBalance(user);
		return new ResponseEntity<ResponseObject>(messagenew, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/receiverbalance")
	@ApiOperation(value = "Register A User")
	public ResponseEntity<ResponseObject> Balance(
			@ApiParam(value = "Register User object store in database table", required = true) @Valid @RequestBody AccountNumber user) {
		ResponseObject messagenew = service.receiverAccountBalance(user);
		return new ResponseEntity<ResponseObject>(messagenew, HttpStatus.CREATED);
	}
	
	
	@PostMapping("/transfermessage")
	@ApiOperation(value = "Transfer amount")
	public ResponseEntity<ResponseMXMessage> CreateMessage(
			@ApiParam(value = "Convert Message To Mt103 and send then take response from mxservice", required = true) @Valid @RequestBody Mtmessage message) {

		ResponseMXMessage messagenew = service.transfermessage(message);
		return new ResponseEntity<ResponseMXMessage>(messagenew, HttpStatus.ACCEPTED);
	}

}
