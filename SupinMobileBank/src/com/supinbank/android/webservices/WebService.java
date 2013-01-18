package com.supinbank.android.webservices;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class WebService {
	
	 final String NAMESPACE = "http://webservices.supinbank.com/";
     final String URL = "http://10.29.16.46:8080/SupinBank-SupinBank-EJB/SupinBankWebServicesBean?wsdl";
     final String METHOD_NAME = "getAllAccountsByEmailAndPassword";
     final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

	
	public Object getListAccountsByLoginAndPassword(String login, String password) {

       SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
       SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

       PropertyInfo infoLogin = new PropertyInfo();
       infoLogin.setName("arg0");
       infoLogin.setValue(login);

       PropertyInfo infoPassword = new PropertyInfo();
       infoPassword.setName("arg1");
       infoPassword.setValue(password);

       request.addProperty(infoLogin);
       request.addProperty(infoPassword);

       MarshalDate marshalDate = new MarshalDate();
       marshalDate.register(envelope);

       envelope.setOutputSoapObject(request);
       HttpTransportSE transportUnsecured = new HttpTransportSE(URL);

       transportUnsecured.debug = true;
       try {
           transportUnsecured.call(SOAP_ACTION, envelope);
       } catch (IOException e) {
           e.printStackTrace();
           return null;
       } catch (XmlPullParserException e) {
           e.printStackTrace();
           return null;
       }
       return envelope.bodyIn;
   }

}
