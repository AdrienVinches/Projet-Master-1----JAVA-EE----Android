package com.supinbank.advisor.customer;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import com.supinbank.account.Account;
import com.supinbank.account.enums.OperationTypeEnums;
import com.supinbank.auth.managedbeans.AccessManager;
import com.supinbank.humanresources.Customer;
import com.supinbank.operation.Operation;
import com.supinbank.session.remote.SupinBankSession;
import com.supinbank.tools.CalculSumAccount;

public class AddMoneyToCustomer extends AccessManager {
	
	@EJB
	private SupinBankSession session;
	private Account account;
	private Customer customer;
	private float price;
	private String libelle;
	
	
	public AddMoneyToCustomer() throws IOException {
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
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Account getAccount() {
		if(account == null)
			account = new Account();
		
		return account;
	}
	public float getPrice() {
		return price;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public String addMoney(){
		System.out.println("customer : "+getCustomer().getFirstName()+" account "+getAccount().getName()+" price "+getPrice()+" wording "+getLibelle());
		
		Operation operation = new Operation();
		operation.setAccount(getAccount());
		operation.setDate(new Date());
		operation.setLibelle(getLibelle());
		operation.setPrice(getPrice());
		operation.setOperationType(OperationTypeEnums.credit);
		
		System.out.println("operation ok");
		
		operation = getSession().addOperation(operation);
		
		System.out.println("operation persist : "+getAccount().getName());
		
		float amount = CalculSumAccount.calculSum(getSession(),getAccount());
		getAccount().setAmount(amount);
		
		getSession().editAccount(getAccount());
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		Application app = ctx.getApplication();
		CustomerAccount customerAccount = (CustomerAccount)  app.evaluateExpressionGet(ctx, "#{" + "customerAccount" + "}", CustomerAccount.class);
		customerAccount.setCustomer(getCustomer());
		customerAccount.setModelAccount(null);
		
		return "customerAccount";
	}

	
	
	

}
