package com.supinbank.customer.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.supinbank.account.Account;
import com.supinbank.account.enums.OperationTypeEnums;
import com.supinbank.auth.managedbeans.AccessManager;
import com.supinbank.auth.managedbeans.SessionUser;
import com.supinbank.operation.Operation;
import com.supinbank.session.remote.SupinBankSession;
import com.supinbank.tools.CalculSumAccount;

public class PerformTransfert extends AccessManager {
	
	@EJB
	private SupinBankSession session;
	private List<SelectItem> listSelectItemAccount;
	private SessionUser sessionUser;
	private Account account;
	private boolean internal;
	private Account internalAccount;
	private long price;
	private String description;
	private String establishmentCode;
	private String branchCode;
	private String accountNumber;
	private String key;
	
	public PerformTransfert() throws IOException {
		this.redirectIfNotLogged();	
	}
	
	public String getEstablishmentCode() {
		return establishmentCode;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public String getKey() {
		return key;
	}
	public void setEstablishmentCode(String establishmentCode) {
		this.establishmentCode = establishmentCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public long getPrice() {
		return price;
	}
	public String getDescription() {
		if(description == null)
			description = "";
		
		return description;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Account getInternalAccount() {
		if(internalAccount == null)
			internalAccount = new Account();
		
		return internalAccount;
	}
	public void setInternalAccount(Account internalAccount) {
		this.internalAccount = internalAccount;
	}
	public boolean isInternal() {
		return internal;
	}
	public void setInternal(boolean internal) {
		this.internal = internal;
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
					return listSelectItemAccount;
				}				
			return listSelectItemAccount;
		}
		return listSelectItemAccount;
	}
	public void setSession(SupinBankSession session) {
		this.session = session;
	}
	public void setListSelectItemAccount(List<SelectItem> listSelectItemAccount) {
		this.listSelectItemAccount = listSelectItemAccount;
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
	
	public String performTransfert(){
	//	System.out.println(this.getAccountNumber());
	//	System.out.println(this.getBranchCode());
		System.out.println(getEstablishmentCode());
	//	System.out.println(this.getDescription());
	//	System.out.println(this.getKey());
//		System.out.println(getPrice());
//		System.out.println(getAccount().getId());
//		System.out.println(getInternalAccount().getId());
//		System.out.println(isInternal());
		
	
		
		if(isInternal()){
			Operation operationCredit = new Operation();
			Operation operationDebit = new Operation();
			
			operationDebit.setAccount(getAccount());
			operationDebit.setDate(new Date());
			operationDebit.setLibelle(getDescription());
			operationDebit.setOperationType(OperationTypeEnums.debit);
			operationDebit.setPrice(getPrice());
			
			operationCredit.setAccount(getInternalAccount());
			operationCredit.setDate(new Date());
			operationCredit.setLibelle(getDescription());
			operationCredit.setOperationType(OperationTypeEnums.credit);
			operationCredit.setPrice(getPrice());
			
			getSession().addOperation(operationDebit);
			getSession().addOperation(operationCredit);
			
			float amount = CalculSumAccount.calculSum(getSession(),getAccount());
			getAccount().setAmount(amount);
			getSession().editAccount(getAccount());
			
			float amount2 = CalculSumAccount.calculSum(getSession(),getInternalAccount());
			getInternalAccount().setAmount(amount2);
			getSession().editAccount(getInternalAccount());
			
			
	
		}else if(getEstablishmentCode().equalsIgnoreCase("75689")){
			String bbanToCompare = getEstablishmentCode()+"/"+getBranchCode()+"/"+getAccountNumber()+"/"+getKey();
			System.out.println(bbanToCompare);
			Account accountToAddMoney = new Account();
			try {
				 accountToAddMoney = session.getAccountByBban(bbanToCompare);
				 
			} catch (Exception e) {
				FacesMessage myFacesMessage = new FacesMessage();
				myFacesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
				myFacesMessage.setSummary(String.format("Aucun compte ne correspond"));
				myFacesMessage.setDetail("");
				
				FacesContext myFacesContext = FacesContext.getCurrentInstance();
				myFacesContext.addMessage(null, myFacesMessage);
				return "";
			}
			
			if(accountToAddMoney == null){
				FacesMessage myFacesMessage = new FacesMessage();
				myFacesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
				myFacesMessage.setSummary(String.format("Aucun compte ne correspond"));
				myFacesMessage.setDetail("");
				
				FacesContext myFacesContext = FacesContext.getCurrentInstance();
				myFacesContext.addMessage(null, myFacesMessage);
				return "";
			}
						
			
			Operation operationCredit = new Operation();
			Operation operationDebit = new Operation();
			
			operationDebit.setAccount(getAccount());
			operationDebit.setDate(new Date());
			operationDebit.setLibelle(getDescription());
			operationDebit.setOperationType(OperationTypeEnums.debit);
			operationDebit.setPrice(getPrice());
			
			operationCredit.setAccount(accountToAddMoney);
			operationCredit.setDate(new Date());
			operationCredit.setLibelle(getDescription());
			operationCredit.setOperationType(OperationTypeEnums.credit);
			operationCredit.setPrice(getPrice());
			
			getSession().addOperation(operationDebit);
			getSession().addOperation(operationCredit);
			
			float amount = CalculSumAccount.calculSum(getSession(),getAccount());
			getAccount().setAmount(amount);
			getSession().editAccount(getAccount());
			
			float amount2 = CalculSumAccount.calculSum(getSession(),accountToAddMoney);
			accountToAddMoney.setAmount(amount2);
			getSession().editAccount(accountToAddMoney);
		
		}
		else if(getEstablishmentCode() != "75689"){
			Operation operationDebit = new Operation();
			
			operationDebit.setAccount(getAccount());
			operationDebit.setDate(new Date());
			operationDebit.setLibelle(getDescription());
			operationDebit.setOperationType(OperationTypeEnums.debit);
			operationDebit.setPrice(getPrice());
			
			
			getSession().addOperation(operationDebit);
			
			float amount = CalculSumAccount.calculSum(getSession(),getAccount());
			getAccount().setAmount(amount);
			getSession().editAccount(getAccount());
			
		}
		FacesContext ctx2 = FacesContext.getCurrentInstance();
		Application app2 = ctx2.getApplication();
		ListAccount listAccount = (ListAccount) app2.evaluateExpressionGet(ctx2, "#{" + "listAccount" + "}", ListAccount.class);
		
		listAccount.setPerson(sessionUser.getPerson());
		listAccount.setAccountDataModel(null);
		return "welcome_page_customer";
	}
	

}
