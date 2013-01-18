package com.supinbank.customer.account;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.supinbank.auth.managedbeans.AccessManager;
import com.supinbank.humanresources.Person;
import com.supinbank.session.remote.SupinBankSession;

public class ListAccount extends AccessManager {

	@EJB
	private SupinBankSession session;
	private DataModel accountDataModel;
	private Person person;
	
	public ListAccount() throws IOException {
		this.redirectIfNotLogged();	
	}
	
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public SupinBankSession getSession() {
		return session;
	}

	public void setSession(SupinBankSession session) {
		this.session = session;
	}

	public DataModel getAccountDataModel() {
		if(accountDataModel == null){
			accountDataModel = new  ListDataModel();
			if(getSession().getListAccountByPerson(getPerson()) != null){
				accountDataModel.setWrappedData(getSession().getListAccountByPerson(getPerson()));
			}
		}
			
		return accountDataModel;
	}

	public void setAccountDataModel(DataModel accountDataModel) {
		this.accountDataModel = accountDataModel;
	}
	
	
}
