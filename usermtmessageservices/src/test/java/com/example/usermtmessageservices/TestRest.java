package com.example.usermtmessageservices;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.usermtmessageservices.entity.AccountNumber;
import com.example.usermtmessageservices.entity.Mtmessage;
import com.example.usermtmessageservices.entity.ReceiverBank;
import com.example.usermtmessageservices.entity.User;
import com.example.usermtmessageservices.model.ResponseMXMessage;
import com.example.usermtmessageservices.model.ResponseObject;
import com.example.usermtmessageservices.repository.BankRepository;
import com.example.usermtmessageservices.repository.UserRepository;
import com.example.usermtmessageservices.service.MtService;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TestRest {

	

	@Autowired
	UserRepository userRepository;
	
    @Autowired
    private BankRepository bankRepository;
    
    @Autowired
    MtService service;
	
	@Test
	@Order(1)
	public void testCreate () {
		User p = new User();
		p.setId(1L);
		p.setAccountbalance(0);
		p.setAccountnumber("11111");
		p.setUsername("100");
		userRepository.save(p);
		assertNotNull(userRepository.findById(1L).get());
	}

	
	@Test
	@Order(2)
	public void getbalance(){

		int balance = userRepository.accountBalance("1720364789995554");
		// then - verify the output
		assertThat(balance).isNotNull();
	}
	
	@Test
	@Order(3)
	public void getreceiverbalance(){

		int balance = bankRepository.accountBalance("11225546165621");
		// then - verify the output
		assertThat(balance).isNotNull();
	}
	@Test
	@Order(4)
	public void receiverAccount(){
		int count = bankRepository.accountcount("11225546165621");
		// then - verify the output
		assertThat(count).isNotNull();
	}
	
	@Test
	@Order(5)
	public void serviceone(){
		AccountNumber user =new AccountNumber();
		user.setAccountnumber("1720364789995554");
		Object abcd =service.userAccountBalance(user);
		// then - verify the output
		assertThat(abcd).isNotNull();
	}
	
	@Test
	@Order(6)
	public void servicetwo(){
		AccountNumber user =new AccountNumber();
		user.setAccountnumber("11225546165621");
		Object abcd =service.receiverAccountBalance(user);
		// then - verify the output
		assertThat(abcd).isNotNull();
	}

	
	@Test
	@Order(7)
	public void transfer(){
//		{
//			  "accountnumber": "1720364789995554",
//			  "address": "SBI",
//			  "amount": "1000",
//			  "bankname": "SBI",
//			  "currency": "INR",
//			  "receiver": "ENF43332",
//			  "receiverAccountNo": "11225546165621",
//			  "refernce": "CRED",
//			  "sender": "ENFEESS123"
//			}
		Mtmessage mtm =new Mtmessage();
		mtm.setAccountnumber("1720364789995554");
		mtm.setAddress("SBI");
		mtm.setAmount("100");
		mtm.setBankname("SBI");
		mtm.setReceiver("ENFSSS");
		mtm.setReceiverAccountNo("11225546165621");
		mtm.setRefernce("CRED");
		mtm.setSender("ENFFSS");
	
		Object abcd =service.transfermessage(mtm);
		// then - verify the output
		assertThat(abcd).isNotNull();
	}
	
	@Test
	@Order(8)
	public void receiverbankService() {
		ReceiverBank p = new ReceiverBank();
		p.setId(1L);
		p.setAccountbalance(0);
		p.setAccountnumber("11111");
		p.setBankName("sbi");
		p.setIfsccode("sbinss");
		p.setReceivername("anand");
		Object abcd =service.createReceiver(p);
		// then - verify the output
		assertThat(abcd).isNotNull();
	}
	


}
