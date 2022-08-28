package com.example.usermxmessageservices.service;


import org.json.JSONObject;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.usermxmessageservices.entity.MxMessage;
import com.example.usermxmessageservices.repository.MxRepository;
import com.prowidesoftware.swift.io.ConversionService;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;


@Service
public class MessageConsumeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private MxRepository mxrepository;
   
	
	@KafkaListener(topics = "mtmessage")
	public void consume(String message) {

		JSONObject json = new JSONObject(message);
		String mtmessage = json.get("MTMessage").toString();
		String accountNumber=json.get("SenderAccountnumber").toString();
		System.out.println(mtmessage);
	
		 ConversionService srv = new ConversionService();
	        String fin = mtmessage;
	        String xml = srv.getXml(fin);
	        System.out.println(xml.toString());
	        Map<String, String> hm= new HashMap<String, String>();
	        hm.put("MXmessage", xml);
	        JSONObject newjson = new JSONObject(hm);
	    
	        String newMessage=newjson.toString();
	      
	        sendMessage(newMessage);
	        
	
			}
	 public void sendMessage(String message){

	        LOGGER.info(String.format("Message sent -> %s", message));
	        kafkaTemplate.send("mxmessage", message);
	        
	      
	        
	    }

}


