package com.supinbankmobile.asynchrounoustask;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

import com.supinbankmobile.activities.ListAccount;
import com.supinbankmobile.entities.Account;
import com.supinbankmobile.webservices.WebService;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class AsyncGetAccount extends AsyncTask<Void, Void, Void> {
	private String email, password;
    private Context context;
    private SoapObject soapObject;
    
    public AsyncGetAccount(String email, String password, Context context) {
        this.email = email;
        this.password = password;
        this.context = context;
    }
    
    @Override
    protected Void doInBackground(Void... voids) {
    	 System.out.println(">>>>>>>>>>>>>>>>>>>< "+email + " password "+password );
       WebService ws = new WebService();
        soapObject = (SoapObject) ws.CallMethod(email, password);
        System.out.println("<<<<<<>>>>>>><<<<<<>>>>< ca passe");

        if (soapObject != null) {
            Account.listAccount = new ArrayList<Account>();

            for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                Account account = new Account((SoapObject) soapObject.getProperty(i));
                Account.listAccount.add(account);
                System.out.println(">>>>>>>>>>>>>>>>> account "+i);
            }
////            if(Account.listAccount != null && Account.listAccount.size() > 0) {
////                System.out.println(" Hell yeah !! account is not null ! : " + Account.listAccount.size());
////            } else {
////                System.out.println("fuck !! account is null !!");
////            }
        }
        return null;
    }
    
    @Override
    protected void onPostExecute(Void aVoid) {
      // LoginProgressDialog.dismissLoginProgressDialog();
        //if (Account.listAccount != null && Account.listAccount.size() > 0) {
    	 System.out.println("111111111111111111111 "+email + " password "+password + " " + context);
            Intent intent = new Intent(context, ListAccount.class); 
            System.out.println("22222222222222222 "+email + " password "+password );
            context.startActivity(intent);
       // } else if (soapObject == null) {
           // LoginAlert.showServerDownAlert(context);
       // } else {
            //LoginAlert.showLoginAlert(context);
       // }
    }

}
