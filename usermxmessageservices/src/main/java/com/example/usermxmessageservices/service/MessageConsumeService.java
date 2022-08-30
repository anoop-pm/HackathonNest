package com.example.usermxmessageservices.service;


import org.json.JSONObject;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import com.example.usermxmessageservices.entity.MxMessage;
import com.example.usermxmessageservices.repository.MxRepository;
import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.mx.MxPacs00800108;
import com.prowidesoftware.swift.model.mx.MxWriteConfiguration;
import com.prowidesoftware.swift.model.mx.dic.AccountIdentification4Choice;
import com.prowidesoftware.swift.model.mx.dic.ActiveCurrencyAndAmount;
import com.prowidesoftware.swift.model.mx.dic.BranchAndFinancialInstitutionIdentification6;
import com.prowidesoftware.swift.model.mx.dic.CashAccount38;
import com.prowidesoftware.swift.model.mx.dic.ChargeBearerType1Code;
import com.prowidesoftware.swift.model.mx.dic.CreditTransferTransaction39;
import com.prowidesoftware.swift.model.mx.dic.FIToFICustomerCreditTransferV08;
import com.prowidesoftware.swift.model.mx.dic.FinancialInstitutionIdentification18;
import com.prowidesoftware.swift.model.mx.dic.GenericAccountIdentification1;
import com.prowidesoftware.swift.model.mx.dic.GroupHeader93;
import com.prowidesoftware.swift.model.mx.dic.PartyIdentification135;
import com.prowidesoftware.swift.model.mx.dic.PaymentIdentification7;
import com.prowidesoftware.swift.model.mx.dic.PostalAddress24;
import com.prowidesoftware.swift.model.mx.dic.SettlementInstruction7;
import com.prowidesoftware.swift.model.mx.dic.SettlementMethod1Code;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
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
   
	String mxXML=null;
	@KafkaListener(topics = "mtmessage")
	public void consume(String message) throws IOException {

		JSONObject json = new JSONObject(message);
		String mtmessage = json.get("MTMessage").toString();
		String accountNumber=json.get("SenderAccountnumber").toString();
		System.out.println(mtmessage);
	
		 ConversionService srv = new ConversionService();
	        String fin = mtmessage;
	        String xml = srv.getXml(fin);
	        System.out.println(xml.toString());
	        Map<String, String> hm= new HashMap<String, String>();
	        mxXML=mxMessage(fin);
	        hm.put("MXmessage", mxXML +" XML : "+xml.toString());
	        JSONObject newjson = new JSONObject(hm);
	    
	        String newMessage=newjson.toString();
	      
	        
	        sendMessage(newMessage);
	     
	
			}
	
	public String mxMessage(String mt) throws IOException {
		
		  
        Locale locale = Locale.getDefault();
        SwiftMessage sm = SwiftMessage.parse(mt);

        /*
         * With single value per field
         */
        System.out.println("Sender: " + sm.getSender());
        System.out.println("Receiver: " + sm.getReceiver() + "\n");
        Dictionary<String, String> datas = new Hashtable<String, String>();
        for (Tag tag : sm.getBlock4().getTags()) {
            Field field = tag.asField();
            System.out.println(Field.getLabel(field.getName(), "103", null, locale));
            System.out.println(field.getValueDisplay(locale) + "\n");
            datas.put(Field.getLabel(field.getName(), "103", null, locale), field.getValueDisplay(locale));
        }

        System.out.println("END OF LOOP"+"\n");
        /*
         * With values per component
         */
     
        for (Tag tag : sm.getBlock4().getTags()) {
            Field field = tag.asField();
            System.out.println("\n" + Field.getLabel(field.getName(), "103", null, locale));
            for (int component = 1; component <= field.componentsSize(); component++) {
                if (field.getComponent(component) != null) {
                    System.out.print(field.getComponentLabel(component) + ": ");
                    System.out.println(field.getValueDisplay(component, locale));
                  
                  
                    
                    
                }
            }
        }
System.out.println("MyVAlues"+datas);

System.out.println("MyVAluesnew"+datas.get("Sender to Receiver Information"));
        

        MxPacs00800108 mx = new MxPacs00800108();

              mx.setFIToFICstmrCdtTrf(new FIToFICustomerCreditTransferV08().setGrpHdr(new GroupHeader93()));

        /*
         * General Information
         */
        mx.getFIToFICstmrCdtTrf().getGrpHdr().setMsgId(datas.get("Sender's Reference")); //20 Sender's Reference
        mx.getFIToFICstmrCdtTrf().getGrpHdr().setCreDtTm(getXMLGregorianCalendarNow());
        mx.getFIToFICstmrCdtTrf().getGrpHdr().setNbOfTxs("1");

        /*
         * Settlement Information
         */
        mx.getFIToFICstmrCdtTrf().getGrpHdr().setSttlmInf(new SettlementInstruction7());
        mx.getFIToFICstmrCdtTrf().getGrpHdr().getSttlmInf().setSttlmMtd(SettlementMethod1Code.INDA);
        mx.getFIToFICstmrCdtTrf().getGrpHdr().getSttlmInf().setSttlmAcct(
                (new CashAccount38()).setId(
                        (new AccountIdentification4Choice()).setOthr(
                                (new GenericAccountIdentification1()).setId("00010013800002001234"))));

        /*
         * Instructing Agent
         */
        mx.getFIToFICstmrCdtTrf().getGrpHdr().setInstgAgt(
                (new BranchAndFinancialInstitutionIdentification6()).setFinInstnId(
                        (new FinancialInstitutionIdentification18()).setBICFI(datas.get("Sender's Correspondent")))); // Ordering Institution

        /*
         * Instructed Agent
         */
        mx.getFIToFICstmrCdtTrf().getGrpHdr().setInstdAgt(
                (new BranchAndFinancialInstitutionIdentification6()).setFinInstnId(
                        (new FinancialInstitutionIdentification18()).setBICFI(datas.get("Account With Institution")))); // Sender's Correspondent

        /*
         * Payment Transaction Information
         */
        CreditTransferTransaction39 cti = new CreditTransferTransaction39();

        /*
         * Transaction Identification
         */
        cti.setPmtId(new PaymentIdentification7());
        cti.getPmtId().setInstrId(datas.get("Sender's Reference")); //20 Sender's Reference
        cti.getPmtId().setEndToEndId(datas.get("Sender's Reference"));
        cti.getPmtId().setTxId(datas.get("Sender's Reference"));

        /*
         * Transaction Amount
         */
		String transaction =datas.get("Value Date/Currency/Interbank Settled Amount");
		String[] currencies = transaction.split(" ");
		
		
		List<String> items = Arrays.asList(Arrays.toString(currencies).split("\\s*,\\s*"));
		

		System.out.println("output string: " + Arrays.toString(currencies)+items.get(2).toString().replace("[", "").replace("]", ""));
		
		String currencyType=items.get(1).toString().replace("[", "").replace("]", "");
		String amounts=items.get(2).toString().replace("[", "").replace("]", "");
        ActiveCurrencyAndAmount amount = new ActiveCurrencyAndAmount();
        amount.setCcy(currencyType);
        amount.setValue(new BigDecimal(amounts));
        cti.setIntrBkSttlmAmt(amount);

        /*
         * Transaction Value Date
         */
        cti.setIntrBkSttlmDt(getXMLGregorianCalendarNow());

        /*
         * Transaction Charges
         */
        cti.setChrgBr(ChargeBearerType1Code.DEBT);

        /*
         * Orderer Name & Address
         */

        String ordering =datas.get("Ordering Customer");
		String[] orderings = ordering.split(" ");
		
		
		List<String> itemsCustomer = Arrays.asList(Arrays.toString(orderings).split("\\s*,\\s*"));
		

		System.out.println("output string: " + Arrays.toString(currencies)+itemsCustomer.get(0).toString().replace("[", "").replace("]", "")+itemsCustomer.size());
		
		String customeraccno=itemsCustomer.get(0).toString().replace("[", "").replace("]", "");
        String addresslast=itemsCustomer.get(itemsCustomer.size()-1).toString().replace("[", "").replace("]", "");
       
        cti.setDbtr(new PartyIdentification135());
        cti.getDbtr().setNm("JOE DOE");
        cti.getDbtr().setPstlAdr((new PostalAddress24()).addAdrLine(addresslast)); // Ordering Customer

        /*
         * Orderer Account
         */
       
        
        cti.setDbtrAcct(
                (new CashAccount38()).setId(
                        (new AccountIdentification4Choice()).setOthr(
                                (new GenericAccountIdentification1()).setId(customeraccno))));
        /*
         * Order Financial Institution
         */
        
   
      	
        cti.setDbtrAgt(
                (new BranchAndFinancialInstitutionIdentification6()).setFinInstnId(
                        (new FinancialInstitutionIdentification18()).setBICFI("FOOENXM"))); // Ordering Institution

        /*
         * Beneficiary Institution
         */
        cti.setCdtrAgt((new BranchAndFinancialInstitutionIdentification6()).setFinInstnId(
                (new FinancialInstitutionIdentification18()).setBICFI(datas.get("Ordering Customer")))); // Account With Institution

        /*
         * Beneficiary Name & Address
         */
        String benefi =datas.get("Beneficiary Customer");
  		String[] benefiaa = benefi.split(" ");
  		
  		
  		List<String> itemsbene = Arrays.asList(Arrays.toString(benefiaa).split("\\s*,\\s*"));
  		
  		String aone=itemsbene.get(itemsbene.size()-1).toString().replace("[", "").replace("]", "");
  
  		
        cti.setCdtr(new PartyIdentification135());
        cti.getCdtr().setNm("TEST CORP");
        cti.getCdtr().setPstlAdr((new PostalAddress24().addAdrLine( "LAS " + aone))); // Beneficiary Customer FOO AGENTES DE BOLSA ASOC

        /*
         * Beneficiary Account
         */
        
        
        cti.setCdtrAcct(
                (new CashAccount38()).setId(
                        (new AccountIdentification4Choice()).setOthr(
                                (new GenericAccountIdentification1()).setId(itemsbene.get(0).toString().replace("[", "").replace("]", ""))))); //59 Beneficiary Customer

        mx.getFIToFICstmrCdtTrf().addCdtTrfTxInf(cti);

        /*
         * Print the generated message in its XML format (without prefix)
         */
        MxWriteConfiguration conf = new MxWriteConfiguration();
        conf.documentPrefix = null;
        System.out.println(mx.message(conf));
		return mx.message(conf).toString();
		
	}
	
	 public void sendMessage(String message){

	        LOGGER.info(String.format("Message sent -> %s", message));
	        kafkaTemplate.send("mxmessage", message);
	        
	      
	        
	    }
	    public static XMLGregorianCalendar getXMLGregorianCalendarNow() {
	        GregorianCalendar gregorianCalendar = new GregorianCalendar();
	        DatatypeFactory datatypeFactory = null;
	        try {
	            datatypeFactory = DatatypeFactory.newInstance();
	        } catch (DatatypeConfigurationException e) {
	            e.printStackTrace();
	        }
	        XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
	        return now;
	    }

}


