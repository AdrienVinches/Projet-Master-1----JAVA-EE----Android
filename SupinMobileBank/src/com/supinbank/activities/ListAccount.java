package com.supinbank.activities;


import com.supinbank.android.entities.Account;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ListAccount extends Activity {
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.listaccounts);
	    }
	 
	 @Override
	    protected void onResume() {
	        super.onResume();

	        TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
	        TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1F);

	        if (Account.listAccount != null && Account.listAccount.size() > 0) {
	            for (Account account : Account.listAccount) {
	                TableRow row = new TableRow(this);
	                row.setPadding(10, 5, 10, 5);
	                row.addView(createTextViewByAccountName(account.getName()));
	                row.addView(createTextViewByAccountBalance(account.getBalance()));
	                tableLayout.addView(row, rowLp);
	                ((ViewGroup.MarginLayoutParams) row.getLayoutParams()).setMargins(0, 0, 0, 10);
	            }
	        } else {
	            Intent intent = new Intent(this, Login.class);
	            this.startActivity(intent);
	        }
	    }
	 
	 @Override
	    protected void onPause() {
	        super.onPause();
	        Account.listAccount = null;
	    }

	    public TextView createTextViewByAccountName(String accountName) {
	        TextView textView = new TextView((this));
	        textView.setTextSize(18f);
	        textView.setText(accountName);
	        textView.setGravity(Gravity.LEFT);
	        textView.setTextColor(Color.WHITE);
	        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
	                LinearLayout.LayoutParams.MATCH_PARENT, 1F));

	        return textView;
	    }

	    public TextView createTextViewByAccountBalance(Float balance) {

	        TextView textView = new TextView((this));
	        textView.setText(balance.toString() + " €");
	        textView.setTextSize(18f);
	        textView.setTextColor(Color.WHITE);
	        if (balance < 0F) {
	            textView.setTextColor(Color.RED);
	        }
	        textView.setGravity(Gravity.RIGHT);
	        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
	        LinearLayout.LayoutParams.MATCH_PARENT, 1F));

	        return textView;
	    }

}
