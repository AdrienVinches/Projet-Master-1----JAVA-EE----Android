package com.supinbank.converter;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.supinbank.account.Account;
import com.supinbank.advisor.customer.AddAccount;
import com.supinbank.auth.managedbeans.SessionUser;
import com.supinbank.customer.account.ListOperation;
import com.supinbank.customer.account.PerformTransfert;
import com.supinbank.session.remote.SupinBankSession;

public class ConvertAccountTransfert implements Converter {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(arg2 != null && arg2 != ""){
			String finalValue = arg2.substring(0,arg2.lastIndexOf("-"));
			
			Account account = new Account();
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			Application app = ctx.getApplication();
			PerformTransfert performTransfert = (PerformTransfert) app.evaluateExpressionGet(ctx, "#{" + "performTransfert" + "}", PerformTransfert.class);
			account = performTransfert.getSession().getAccountById(Long.valueOf(finalValue));
			performTransfert.setAccount(account);
//			
//			System.out.println("account converter "+account.getName() );
			return account;
		}
		return null;
	}
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 != null)
        {
            if(arg2 instanceof Account)
            {
            	Account account = (Account) arg2;
                return String.valueOf(account.getId()+"-"+account.getName().toUpperCase());
            }
        }
        return null;
	}

		

	}

