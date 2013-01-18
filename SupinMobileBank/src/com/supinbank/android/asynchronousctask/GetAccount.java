package com.supinbank.android.asynchronousctask;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

import com.supinbank.activities.ListAccount;
import com.supinbank.android.alerts.LoginAlert;
import com.supinbank.android.dialog.LoginDialog;
import com.supinbank.android.entities.Account;
import com.supinbank.android.webservices.WebService;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class GetAccount extends AsyncTask<Void, Void, Void> {

	 private String login, password;
		private Context context;
	    private SoapObject soapObject;

	    public GetAccount(String login, String password, Context context) {
	        this.login = login;
	        this.password = password;
	        this.context = context;
	    }
	    
	    @Override
	    protected Void doInBackground(Void... voids) {
	        WebService ws = new WebService();
	        soapObject = (SoapObject) ws.getListAccountsByLoginAndPassword(login, password);
	       
	        if (soapObject != null) {    
	        	Account.listAccount = new ArrayList<Account>(); 
	        	
	        	for (int i = 0; i < soapObject.getPropertyCount(); i++) { 
	            	Account account = new Account((SoapObject) soapObject.getProperty(i));
	                Account.listAccount.add(account);
	            }
	        }
	        return null;
	    }
	    
	    @Override
	    protected void onPostExecute(Void aVoid) {
	        LoginDialog.dismissLoginDialog();
	        
	        if (Account.listAccount != null && Account.listAccount.size() > 0) {
	            Intent intent = new Intent(context, ListAccount.class);
	            context.startActivity(intent);
	        } else if (soapObject == null) {
	            LoginAlert.showErrorConnection(context);
	        } else {
	            LoginAlert.showLoginAlert(context);
	        }
	    }
	
}
