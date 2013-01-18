package com.supinbankmobile.webservices;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class WebService {
	 public Object CallMethod(String email, String password) {
		  
	    	
	        final String NAMESPACE = "http://webservices.supinbank.com/";
	        final String URL = "http://192.168.1.35:8080/SupinBank-SupinBank-EJB/SupinBankWebServicesBean?wsdl";
	        final String METHOD_NAME = "getAllAccountsByEmailAndPassword";
	        final String SOAP_ACTION = "\"SupinBank-SupinBank-EJB\""; // NAMESPACE + method name

	        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        System.out.println("<<<<<<<<<<<<<<<<<<Ca passe");
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

	        PropertyInfo infoEmail = new PropertyInfo();
	        infoEmail.setName("arg0");
	        infoEmail.setValue(email);

	        PropertyInfo infoPassword = new PropertyInfo();
	        infoPassword.setName("arg1");
	        infoPassword.setValue(password);

	        request.addProperty(infoEmail);
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
