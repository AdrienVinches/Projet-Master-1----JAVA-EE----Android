package com.supinbankmobile.activities;

import com.supinbankmobile.listeners.LoginListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class SupinBankMobile extends Activity {
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        EditText emailEditText = (EditText) findViewById(R.id.email);
        EditText passwordEditText = (EditText) findViewById(R.id.password);

        Button buttonLogin = (Button) findViewById(R.id.login);
        buttonLogin.setOnClickListener(new LoginListener(emailEditText, passwordEditText));
    }
}