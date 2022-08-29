package com.example.usermtmessageservices.service;

import java.time.Instant;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.usermtmessageservices.config.ProducerConfigbank;
import com.example.usermtmessageservices.entity.AccountNumber;
import com.example.usermtmessageservices.entity.Mtmessage;
import com.example.usermtmessageservices.entity.ReceiverBank;
import com.example.usermtmessageservices.entity.User;
import com.example.usermtmessageservices.model.ResponseMXMessage;
import com.example.usermtmessageservices.model.ResponseObject;
import com.example.usermtmessageservices.repository.BankRepository;
import com.example.usermtmessageservices.repository.MxRepository;
import com.example.usermtmessageservices.repository.UserRepository;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field23B;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.field.Field50A;
import com.prowidesoftware.swift.model.field.Field59;
import com.prowidesoftware.swift.model.field.Field71A;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

@Service
@Transactional
public class MtService {
	
	@Autowired
	private ResponseObject response;
	
	@Autowired
	private ResponseMXMessage responseMX;

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private BankRepository bankRepository;
	
	@Autowired
	private ProducerConfigbank producers;

	String MXMessage=null;
	
	
	@KafkaListener(topics = "mxmessage")
	public void consume(String message) {
		JSONObject json = new JSONObject(message);
		System.out.println(json.get("MXmessage").toString());
		MXMessage=json.get("MXmessage").toString();
		
	}
	
	public ResponseObject createUser(User user) {
		
		int accountNoCount=userRepository.accountcount(user.getAccountnumber());
		if(accountNoCount == 0) {
		System.out.println("count is"+accountNoCount);
		user.setUsername(user.getUsername());
		user.setAccountnumber(user.getAccountnumber());
		user.setAccountbalance(0);
		userRepository.save(user);
		response.setStatus(200);
		response.setMessage("New User Added");
		}
		else {
			response.setStatus(404);
			response.setMessage("Account already exists");
		}
		return response;
	}
	
	public ResponseObject createReceiver(ReceiverBank receiverUser) {
		
		
		int accountNoCount=bankRepository.accountcount(receiverUser.getAccountnumber());
		if(accountNoCount == 0) {
		receiverUser.setReceivername(receiverUser.getReceivername());
		receiverUser.setBankName(receiverUser.getBankName());
		receiverUser.setAccountnumber(receiverUser.getAccountnumber());
		receiverUser.setIfsccode(receiverUser.getIfsccode());
		receiverUser.setAccountbalance(0);
		bankRepository.save(receiverUser);
		
		response.setStatus(200);
		response.setMessage("New User Added");
		}

		else {
			response.setStatus(404);
			response.setMessage("Account already exists");
		}
		return response;
	}
	
	
	
public ResponseObject receiverAccountBalance(AccountNumber receiverUser) {
		
		
		int accountNoCount=bankRepository.accountcount(receiverUser.getAccountnumber());
		if(accountNoCount == 0) {
	
		response.setStatus(404);
		response.setMessage("Account Not exists");
		}

		else {
			int balance=bankRepository.accountBalance(receiverUser.getAccountnumber());
			
			response.setStatus(200);
			response.setMessage("Account Balance is ="+balance);
		}
		return response;
	}

public ResponseObject userAccountBalance(AccountNumber user) {
	
	
	int accountNoCount=userRepository.accountcount(user.getAccountnumber());
	if(accountNoCount == 0) {

	response.setStatus(404);
	response.setMessage("Account Not exists");
	}

	else {
		int balance=userRepository.accountBalance(user.getAccountnumber());
		
		response.setStatus(200);
		response.setMessage("Account Balance is ="+balance);
	}
	return response;
}
	
	
	public ResponseObject deposit(User user) {
		
		int accountNoCount=userRepository.accountcount(user.getAccountnumber());
		int amount;
		if(accountNoCount >= 1) {
		int accountbalance;
		accountbalance=userRepository.accountBalance(user.getAccountnumber());
		amount=accountbalance+user.getAccountbalance();
		System.out.println(accountbalance+"count is"+amount );
		userRepository.updatebalance(amount, user.getAccountnumber());
		
		response.setStatus(200);
		response.setMessage("account balance is : "+amount);
		}
		else {
			response.setStatus(404);
			response.setMessage("Account not exists");
		}
		return response;
	}
	
	
	public ResponseMXMessage transfermessage(Mtmessage message) {


		
		int accountNoCount=0;
		int userBalance=0;
		int receiverAccountNumber=0;
		int receiverBalance=0;
	
		try {
		 accountNoCount=userRepository.accountcount(message.getAccountnumber());
		 userBalance=userRepository.accountBalance(message.getAccountnumber());
		 receiverAccountNumber=bankRepository.accountcount(message.getReceiverAccountNo());
		 receiverBalance=bankRepository.accountBalance(message.getReceiverAccountNo());
		}
		catch (Exception e) {
			
			
		}
		
		int transferAmout=Integer.parseInt(message.getAmount()) ;
		System.out.println("details"+accountNoCount+"ssssss"+userBalance+"ss"+transferAmout);
		
		
		if(accountNoCount ==0  ) {
			
			responseMX.setMessage("AccountNumer does not match");
			responseMX.setStatus(400);
			responseMX.setMtmessage("NULL");
			responseMX.setMxmessage("NULL");
		}
		else if(userBalance < transferAmout) {
			responseMX.setMessage("Inefficent balance");
			responseMX.setStatus(400);
			responseMX.setMtmessage("NULL");
			responseMX.setMxmessage("NULL");
		}
		
		else {
			
		int newamount =	userBalance-transferAmout;
		int receiverbalance=receiverBalance+transferAmout;
		
		userRepository.updatebalance(newamount, message.getAccountnumber());
		bankRepository.updatebalance(receiverbalance, message.getReceiverAccountNo());
		final MT103 m = new MT103();

        /*
         * Set sender and receiver BIC codes
         */
        m.setSender(message.getSender());
        m.setReceiver(message.getReceiver());

        /*
         * Start adding the message's fields in correct order
         */
        m.addField(new Field20("REFERENCE"));
        m.addField(new Field23B(message.getRefernce()));

        /*
         * Add a field using comprehensive setters API
         */
        Field32A f32A = new Field32A()
                .setDate(Calendar.getInstance())
                .setCurrency(message.getCurrency())
                .setAmount(message.getAmount());
        m.addField(f32A);

        /*
         * Add the orderer field
         */
        Field50A f50A = new Field50A()
                .setAccount(message.getAccountnumber())
                .setBIC(message.getBankname());
        m.addField(f50A);

        /*
         * Add the beneficiary field
         */
        Field59 f59 = new Field59()
                .setAccount(message.getReceiverAccountNo())
                .setNameAndAddress(message.getAddress());
        m.addField(f59);

        /*
         * Add the commission indication
         */
        m.addField(new Field71A("OUR"));

        /*
         * Create and print out the SWIFT FIN message string
         */
        System.out.println(m.message());
  
    
	
		Producer<String, String> producer = new KafkaProducer<>(producers.kafkaproducer());
		try {
			producer.send(bankTransaction(message.getAccountnumber(),m.message() ));
			Thread.sleep(100);

		} catch (InterruptedException e) {

		}

		producer.close();
		
		try {
			TimeUnit.SECONDS.sleep(5);
			System.out.println("Working"+MXMessage+"null");
			responseMX.setMessage("Amount Transfered Successfully");
			responseMX.setStatus(200);
			responseMX.setMtmessage(m.message());
			responseMX.setMxmessage(MXMessage);
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		return responseMX;	
	}
	

	public static ProducerRecord<String, String> bankTransaction(String accno, String mtMessage) {

		// creates an empty json {}
		ObjectNode transaction = JsonNodeFactory.instance.objectNode();

		// Instant.now() is to get the current time using Java 8
		Instant now = Instant.now();

		// we write the data to the json document
		transaction.put("SenderAccountnumber", accno);
		transaction.put("MTMessage", mtMessage);
		transaction.put("time", now.toString());
		return new ProducerRecord<>("mtmessage", accno, transaction.toString());
	}
	
	

}
