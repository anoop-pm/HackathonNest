package com.example.MTtoTextService.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MTtoTextService.entity.MTMessage;
import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.mx.MxPacs00800108;
import com.prowidesoftware.swift.model.mx.MxWriteConfiguration;
import com.prowidesoftware.swift.model.mx.dic.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.Hashtable;

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
        
        SwiftParser parser = new SwiftParser(fin);
        SwiftMessage mt = parser.message();

//        System.out.println(mt.toJson());
        
        
        /*
         *{
  "timestamp": "2022-08-30T08:16:09Z",
  "version": 2,
  "data": {
    "block1": {
      "applicationId": "F",
      "serviceId": "01",
      "logicalTerminal": "BICFOOYYAXXX",
      "sessionNumber": "8683",
      "sequenceNumber": "497519"
    },
    "block2": {
      "senderInputTime": "1535",
      "MIRDate": "051028",
      "MIRLogicalTerminal": "ESPBESMMAXXX",
      "MIRSessionNumber": "5423",
      "MIRSequenceNumber": "752247",
      "receiverOutputDate": "051028",
      "receiverOutputTime": "1535",
      "messagePriority": "N",
      "messageType": "103",
      "blockType": "O",
      "direction": "O"
    },
    "block3": {
      "tags": [
        {
          "name": "113",
          "value": "ROMF"
        },
        {
          "name": "108",
          "value": "0510280182794665"
        },
        {
          "name": "119",
          "value": "STP"
        }
      ]
    },
    "block4": {
      "tags": [
        {
          "name": "20",
          "value": "0061350113089908"
        },
        {
          "name": "13C",
          "value": "/RNCTIME/1534+0000"
        },
        {
          "name": "23B",
          "value": "CRED"
        },
        {
          "name": "23E",
          "value": "SDVA"
        },
        {
          "name": "32A",
          "value": "061028EUR100000,"
        },
        {
          "name": "33A",
          "value": "081029EUR120000,"
        },
        {
          "name": "33B",
          "value": "EUR100000,"
        },
        {
          "name": "50K",
          "value": "/12345678\r\nAGENTES DE BOLSA FOO AGENCIA\r\nAV XXXXX 123 BIS 9 PL\r\n12345 BARCELONA"
        },
        {
          "name": "52A",
          "value": "/2337\r\nFOOAESMMXXX"
        },
        {
          "name": "53A",
          "value": "FOOAESMMXXX"
        },
        {
          "name": "57A",
          "value": "BICFOOYYXXX"
        },
        {
          "name": "59",
          "value": "/ES0123456789012345671234\r\nFOO AGENTES DE BOLSA ASOC"
        },
        {
          "name": "71A",
          "value": "OUR"
        },
        {
          "name": "72",
          "value": "/BNF/TRANSF. BCO. FOO"
        }
      ]
    },
    "block5": {
      "tags": [
        {
          "name": "MAC",
          "value": "88B4F929"
        },
        {
          "name": "CHK",
          "value": "22EF370A4073"
        }
      ]
    }
  }
}
         */
        
        
        
        Locale locale = Locale.getDefault();
        SwiftMessage sm = SwiftMessage.parse(fin);

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
        
        String orderingfinance =datas.get("Ordering Institution");
      		String[] orderingsfinance = orderingfinance.split(" ");
      		
      		
      		List<String> itemsCustomerdinance = Arrays.asList(Arrays.toString(orderingsfinance).split("\\s*,\\s*"));
      		

      		System.out.println("output string: " + Arrays.toString(currencies)+itemsCustomerdinance.get(1).toString().replace("[", "").replace("]", "")+itemsCustomerdinance.size());
      		String foobar=itemsCustomerdinance.get(1).toString().replace("[", "").replace("]", "");
        cti.setDbtrAgt(
                (new BranchAndFinancialInstitutionIdentification6()).setFinInstnId(
                        (new FinancialInstitutionIdentification18()).setBICFI(foobar))); // Ordering Institution

        /*
         * Beneficiary Institution
         */
        cti.setCdtrAgt((new BranchAndFinancialInstitutionIdentification6()).setFinInstnId(
                (new FinancialInstitutionIdentification18()).setBICFI(datas.get("Account With Institution")))); // Account With Institution

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
        response.setMxMessage(mx.message(conf).toString());
		return response;
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
	

