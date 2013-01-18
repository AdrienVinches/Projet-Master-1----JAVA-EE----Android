package com.supinbank.advisor.customer;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;


import com.supinbank.auth.managedbeans.AccessManager;
import com.supinbank.customer.account.ListAccount;
import com.supinbank.humanresources.Customer;
import com.supinbank.session.remote.SupinBankSession;

public class ListCustomer extends AccessManager{
	
	@EJB
	private SupinBankSession session;
	
	private DataModel modelListCustomer;
	
	public ListCustomer() throws IOException {
		this.redirectIfNotLogged();	
	}
	
	

	public SupinBankSession getSession() {
		return session;
	}

	public void setSession(SupinBankSession session) {
		this.session = session;
	}

	public DataModel getModelListCustomer() {
		if(modelListCustomer == null){
			modelListCustomer = new ListDataModel();
		}
		
		modelListCustomer.setWrappedData(getSession().getListCustomer());
		return modelListCustomer;
	}

	public void setModelListCustomer(DataModel modelListCustomer) {
		this.modelListCustomer = modelListCustomer;
	}
	
	public String customerDetails(){
		Customer customer = new Customer();
		customer = (Customer) modelListCustomer.getRowData();
			
		
		System.out.println("listCustomer "+customer.getLastName());
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		Application app = ctx.getApplication();
		CustomerAccount customerAccount = (CustomerAccount)  app.evaluateExpressionGet(ctx, "#{" + "customerAccount" + "}", CustomerAccount.class);
		customerAccount.setCustomer(new Customer());
		customerAccount.setCustomer(customer);
		
		System.out.println("ait "+customerAccount.getCustomer().getLastName());
		
		return "customerAccount";
	}
	
	
	

}
