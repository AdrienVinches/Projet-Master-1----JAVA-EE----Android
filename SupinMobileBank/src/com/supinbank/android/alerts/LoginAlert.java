package com.supinbank.android.alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class LoginAlert {
	
	 public LoginAlert() {
	    }
	 
	 public static void showLoginAlert(Context context) {
	        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

	        alertDialog.setTitle("Identification failed");
	        alertDialog.setMessage("Your login or password is incorrect");
	        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialogInterface, int whichButton) {
	                dialogInterface.dismiss();
	            }
	        });
	        alertDialog.show();
	    }
	 
	 public static void showErrorConnection(Context context) {
	        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

	        alertDialog.setTitle("Connexion failed");
	        alertDialog.setMessage("Please check if your internet connexion is enable.\n\nPlease try again later...");
	        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialogInterface, int whichButton) {
	                dialogInterface.dismiss();
	            }
	        });
	        alertDialog.show();

	    }


}
