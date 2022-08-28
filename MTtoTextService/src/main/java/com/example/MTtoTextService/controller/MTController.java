package com.example.MTtoTextService.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MTtoTextService.entity.MTMessage;
import com.example.MTtoTextService.service.MTService;
import com.example.MTtoTextService.service.Response;




@RestController
@RequestMapping("/mtmicro")
public class MTController {
	
	
	
	@Autowired
	private  MTService service;
	
	
	
	@GetMapping("/mttovalue")
	public ResponseEntity<Response> userBalance( @RequestBody MTMessage user) throws IOException {
		Response messagenew = service.mttotext(user);
		return new ResponseEntity<Response>(messagenew, HttpStatus.CREATED);
	}

}
