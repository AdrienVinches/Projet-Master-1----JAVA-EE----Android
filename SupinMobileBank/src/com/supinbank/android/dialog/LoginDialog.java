package com.supinbank.android.dialog;

import android.app.ProgressDialog;
import android.content.Context;

public class LoginDialog {
	
	 private static ProgressDialog loginDialog;

	    public static void showLoginDialog(Context context) {

	        if (loginDialog == null) {
	        	loginDialog = new ProgressDialog(context);
	        }
	        
	        loginDialog.setTitle("Connexion");
	        loginDialog.setMessage("Establishing connexion, please Wait...");
	        loginDialog.setCancelable(false);
	        loginDialog.setIndeterminate(true);
	        loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        loginDialog.show();
	    }

	    public static void dismissLoginDialog() {
	    	loginDialog.dismiss();
	    	loginDialog = null;
	    }

}
