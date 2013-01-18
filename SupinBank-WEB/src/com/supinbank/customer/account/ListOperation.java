package com.supinbank.customer.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import com.supinbank.account.Account;
import com.supinbank.auth.managedbeans.AccessManager;
import com.supinbank.auth.managedbeans.SessionUser;
import com.supinbank.humanresources.Customer;
import com.supinbank.session.remote.SupinBankSession;

public class ListOperation extends AccessManager {

	@EJB
	private SupinBankSession session;
	private List<SelectItem> listSelectItemAccount;
	private DataModel modelOperation;
	private Customer customer;
	private Account account;
	private SessionUser sessionUser;
	
	
	public ListOperation() throws IOException {
		this.redirectIfNotLogged();	
	}
	
	public SessionUser getSessionUser() {
		if(sessionUser == null){
			FacesContext ctx2 = FacesContext.getCurrentInstance();
			Application app2 = ctx2.getApplication();
			sessionUser = (SessionUser)  app2.evaluateExpressionGet(ctx2, "#{" + "sessionUser" + "}", SessionUser.class);
			
			}
		return sessionUser;
	}
	public void setSessionUser(SessionUser sessionUser) {
		this.sessionUser = sessionUser;
	}
	public Customer getCustomer() {
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
	public void setAccount(Account account) {
		this.account = account;
	}
	public SupinBankSession getSession() {
		return session;
	}
	public List<SelectItem> getListSelectItemAccount() {
		if(listSelectItemAccount == null){
		listSelectItemAccount = new ArrayList<SelectItem>();
					
			List<Account> listAccount = new ArrayList<Account>();
			listAccount = getSession().getListAccountByPerson(this.getSessionUser().getPerson());
			if(listAccount != null){
				for (Account account : listAccount) {
					this.listSelectItemAccount.add(new SelectItem(account, String.valueOf(account.getId()+"-"+account.getName().toUpperCase())));
				}
				if(getSession().getListOperationByAccount(listAccount.get(0)) != null){		
					if(modelOperation == null){
					modelOperation = new ListDataModel();
					modelOperation.setWrappedData(getSession().getListOperationByAccount(listAccount.get(0)));
					setAccount(listAccount.get(0));
					}
				}else{
					modelOperation = new ListDataModel();
				}
			}
		}
			
			return listSelectItemAccount;
	}
	public DataModel getModelOperation() {
		if(modelOperation == null)
			modelOperation = new ListDataModel();
		
		return modelOperation;
	}
	public void setSession(SupinBankSession session) {
		this.session = session;
	}
	public void setListSelectItemAccount(List<SelectItem> listSelectItemAccount) {
		this.listSelectItemAccount = listSelectItemAccount;
	}
	public void setModelOperation(DataModel modelOperation) {
		this.modelOperation = modelOperation;
	}
	
	public void fillAccount(){
		System.out.println("getAccount() : "+getAccount());
		modelOperation.setWrappedData(getSession().getListOperationByAccount(getAccount()));
	}
	
	
	
}
