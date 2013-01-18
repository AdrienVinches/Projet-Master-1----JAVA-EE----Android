package com.supinbank.android.listeners;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.supinbank.android.alerts.LoginAlert;
import com.supinbank.android.asynchronousctask.GetAccount;
import com.supinbank.android.dialog.LoginDialog;

import android.view.View;
import android.widget.EditText;


public class LoginListener implements View.OnClickListener {

    private EditText loginEditText, passwordEditText;

    public LoginListener(EditText loginEditText, EditText passwordEditText) {
        this.loginEditText = loginEditText;
        this.passwordEditText = passwordEditText;
    }
    public static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

    @Override
    public void onClick(View button) {

        if (loginEditText != null && passwordEditText != null) {
            String login = loginEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String passwordMd5 ="";
           try {
			passwordMd5 =  convertToHex(MessageDigest.getInstance("MD5").digest(password.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		System.out.println("A ouai arwoooooooooooooood "+passwordMd5);
		
            LoginDialog.showLoginDialog(button.getContext());

            GetAccount account = new GetAccount(login, passwordMd5, button.getContext());
            account.execute();

        } else {
            LoginAlert.showLoginAlert(button.getContext());
        }
    }
}
