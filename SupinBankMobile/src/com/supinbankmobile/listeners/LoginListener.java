package com.supinbankmobile.listeners;

import com.supinbankmobile.asynchrounoustask.AsyncGetAccount;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginListener implements View.OnClickListener {
	private EditText emailEditText, passwordEditText;

    public LoginListener(EditText emailEditText, EditText passwordEditText) {
        this.emailEditText = emailEditText;
        this.passwordEditText = passwordEditText;
        System.out.println("initialize");
        
    }

    @Override
    public void onClick(View button) {

//        Boolean validEmail = checkInputValue(emailEditText);
//        Boolean validPassword = checkInputValue(passwordEditText);
//
//        if (validEmail != null && validEmail && validPassword) {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            System.out.println("arwood "+email + " password "+password );
//            LoginProgressDialog.showLoginProgressBar(button.getContext());
            
            AsyncGetAccount asyncGetAccount = new AsyncGetAccount(email, password, button.getContext());
            asyncGetAccount.execute();
//
//        } else if (validEmail != null) {
//            LoginAlert.showLoginAlert(button.getContext());
//        }
    }

    private Boolean checkInputValue(EditText editText) {
//        if (editText.getTag() != null && editText.getTag().equals("email")) {
//            if (!isEmailValid(editText.getText().toString())) {
//                LoginAlert.showEmailMalformedAlert(editText.getContext());
//                return null;
//            }
//            return true;
//        } else if (editText.getText() != null && !editText.getText().toString().equals("")) {
//            return true;
//        }
//        return false;
//    }
//
//    private boolean isEmailValid(String email) {
//        if (email != null && !email.equals("")) {
//            if (email.contains("@") && email.contains(".")) {
//                return true;
//            }
//        }
        return false;
    }
}
