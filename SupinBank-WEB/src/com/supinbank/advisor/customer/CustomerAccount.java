package com.supinbank.advisor.customer;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.supinbank.account.Account;
import com.supinbank.auth.managedbeans.AccessManager;
import com.supinbank.humanresources.Customer;
import com.supinbank.session.remote.SupinBankSession;

public class CustomerAccount extends AccessManager {
	@EJB
	private SupinBankSession session;
	private Customer customer;
	private DataModel modelAccount;
	
	public CustomerAccount() throws IOException {
		this.redirectIfNotLogged();	
		}
	
	
	public SupinBankSession getSession() {
		return session;
	}
	public void setSession(SupinBankSession session) {
		this.session = session;
	}
	public Customer getCustomer() {
		if(customer == null)
			customer = new Customer();
		
		return customer;
	}
	public DataModel getModelAccount() {
		if(modelAccount == null){
			modelAccount = new ListDataModel();
			modelAccount.setWrappedData(getSession().getListAccountByPerson(customer));
			
		}
		
		
		return modelAccount;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void setModelAccount(DataModel modelAccount) {
		this.modelAccount = modelAccount;
	} 
	
	public String addAcount(){
		System.out.println("customerAccount "+getCustomer().getLastName());
		
		FacesContext ctx2 = FacesContext.getCurrentInstance();
		Application app2 = ctx2.getApplication();
		AddAccountToCustomer addAccount = (AddAccountToCustomer)  app2.evaluateExpressionGet(ctx2, "#{" + "addAccountToCustomer" + "}", AddAccountToCustomer.class);
		addAccount.setCustomer(getCustomer());
		addAccount.setAccount(new Account());
		
		return "addAccountToCustomer";
	}
	
	public String addMoneyToAccount(){
		Account account = new Account();
		account = (Account) modelAccount.getRowData();
		
		FacesContext ctx2 = FacesContext.getCurrentInstance();
		Application app2 = ctx2.getApplication();
		AddMoneyToCustomer addMoney = (AddMoneyToCustomer)  app2.evaluateExpressionGet(ctx2, "#{" + "addMoneyToCustomer" + "}", AddMoneyToCustomer.class);
		addMoney.setCustomer(getCustomer());
		addMoney.setAccount(account);
		
		System.out.println("customer "+addMoney.getAccount().getName());
		
		return "addMoneyToAccount";
	}
	
	
	

}
