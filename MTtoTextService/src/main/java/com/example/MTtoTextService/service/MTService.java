package com.example.MTtoTextService.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MTtoTextService.entity.MTMessage;
import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field;



@Service
public class MTService {
	@Autowired
	private Response response;
	
public Response mttotext(MTMessage user) throws IOException {
	  ConversionService srv = new ConversionService();
      String fin = user.getMessage();
      String xml = srv.getXml(fin);
      System.out.println(xml);
        response.setStatus(200);
        response.setMessage(xml);
   
	return response;

 }
	
}
