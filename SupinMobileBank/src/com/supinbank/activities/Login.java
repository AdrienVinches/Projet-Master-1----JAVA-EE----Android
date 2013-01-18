package com.supinbank.activities;



import com.supinbank.activities.R;
import com.supinbank.android.listeners.LoginListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
    /** Called when the activity is first created. */
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);

	        EditText loginEditText = (EditText) findViewById(R.id.login);
	        EditText passwordEditText = (EditText) findViewById(R.id.password);

	        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
	        buttonLogin.setOnClickListener(new LoginListener(loginEditText, passwordEditText));
	    }
}