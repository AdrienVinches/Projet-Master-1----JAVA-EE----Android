package com.supinbank.advisor.customer;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import com.supinbank.account.Account;
import com.supinbank.auth.managedbeans.AccessManager;
import com.supinbank.customer.account.ListAccount;
import com.supinbank.humanresources.Customer;
import com.supinbank.session.remote.SupinBankSession;

public class AddCustomer extends AccessManager {
	
	@EJB
	private SupinBankSession session;
	private Customer customer;
	
	public AddCustomer() throws IOException {
		this.redirectIfNotLogged();	
	}
	
	public SupinBankSession getSession() {
		return session;
	}
	public Customer getCustomer() {
		if(customer == null)
			customer = new Customer();
		
		return customer;
	}
	public void setSession(SupinBankSession session) {
		this.session = session;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public String submitFormAddCustomer(){
		
		FacesContext ctx2 = FacesContext.getCurrentInstance();
		Application app2 = ctx2.getApplication();
		AddAccount addAccount = (AddAccount)  app2.evaluateExpressionGet(ctx2, "#{" + "addAccount" + "}", AddAccount.class);
		addAccount.setCustomer(new Customer());
		addAccount.getCustomer().setFirstName(getCustomer().getFirstName());
		addAccount.getCustomer().setLastName(getCustomer().getLastName());
		addAccount.getCustomer().setEmail(getCustomer().getEmail());
		addAccount.getCustomer().setAdresse(getCustomer().getAdresse());
		addAccount.getCustomer().setZipCode(getCustomer().getZipCode());
		addAccount.getCustomer().setMobilePhone(getCustomer().getMobilePhone());
		addAccount.getCustomer().setCity(getCustomer().getCity());
		addAccount.getCustomer().setListAccount(new ArrayList<Account>());
		
		addAccount.setAccount(new Account());
		
		
		//addAccount.setCustomer(getCustomer());
		return "addAccount";
	}

}
