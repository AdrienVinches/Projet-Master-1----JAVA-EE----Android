package com.supinbank.advisor.customer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.supinbank.account.Account;
import com.supinbank.account.enums.AccountTransfertEnums;
import com.supinbank.auth.managedbeans.AccessManager;
import com.supinbank.humanresources.Customer;
import com.supinbank.session.remote.SupinBankSession;

public class AddAccountToCustomer extends AccessManager {

	
	@EJB
	private SupinBankSession session;
	private Customer customer;
	private Account account;
	private List<SelectItem> listSelectItemAccountTransfert;
	
	public AddAccountToCustomer() throws IOException {
		this.redirectIfNotLogged();	
		}
	
	public List<SelectItem> getListSelectItemAccountTransfert() {
		listSelectItemAccountTransfert = new ArrayList<SelectItem>();
		for (AccountTransfertEnums ate : AccountTransfertEnums.values()) {
			this.listSelectItemAccountTransfert.add(new SelectItem(ate, ate.name()));
		}
		return listSelectItemAccountTransfert;
	}
	
	public void setListSelectItemAccountTransfert(
			List<SelectItem> listSelectItemAccountTransfert) {
		this.listSelectItemAccountTransfert = listSelectItemAccountTransfert;
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
	public Account getAccount() {
		if(account == null)
			account = new Account();
		
		return account;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public String addAccountCustomer(){
		
		System.out.println("customer "+getCustomer().getId());
		getAccount().setAmount(0);
		getAccount().setCustomer(getCustomer());
		
		if(getCustomer().getListAccount() == null){
			getCustomer().setListAccount(new ArrayList<Account>());
		}
		getCustomer().getListAccount().add(getAccount());
		
		customer = session.editCustomer(getCustomer());
		System.out.println("getAccountName "+getAccount().getName());
		
		Account accountWithId = session.getAccountByPersonAndName(getCustomer(), getAccount().getName());
		
		
		System.out.println("id account : "+accountWithId.getId());
		String bban = "75689/00000/"; 
		long bbanCalcul = 7568900000L;
		String idAccount =	accountWithId.getId().toString();
		if(idAccount.length() == 1){
			idAccount = "0000000000"+idAccount;
		}
		else if(idAccount.length() == 2){
			idAccount = "000000000"+idAccount;
		}
		else if(idAccount.length() == 3){
			idAccount = "00000000"+idAccount;
		}
		
		System.out.println("idAccount "+idAccount);
		bban = bban+idAccount;
		System.out.println("bban "+bban);
		bbanCalcul = Long.parseLong(String.valueOf(bbanCalcul)+idAccount.substring(5));
		System.out.println("bbanCalcul "+bbanCalcul);
		long key = 0;
		key = 97-(bbanCalcul*100%97);
		System.out.println("97-("+bbanCalcul+"*100%97)");
		System.out.println("key "+key);
		bban = bban+"/"+String.valueOf(key);
		System.out.println("final"+bban);
		
		
		accountWithId.setbBan(bban);
		
		session.editAccount(accountWithId);
		
		
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		Application app = ctx.getApplication();
		CustomerAccount customerAccount = (CustomerAccount)  app.evaluateExpressionGet(ctx, "#{" + "customerAccount" + "}", CustomerAccount.class);
		customerAccount.setCustomer(new Customer());
		customerAccount.setCustomer(getCustomer());
		customerAccount.setModelAccount(null);
		
		return "customerAccount";
		
		
	}
	
}
