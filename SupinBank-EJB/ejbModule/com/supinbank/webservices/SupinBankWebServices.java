package com.supinbank.webservices;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.jws.WebService;

import com.supinbank.account.Account;
import com.supinbank.humanresources.Customer;
import com.supinbank.humanresources.Person;
import com.supinbank.operation.Operation;
@Remote
public interface SupinBankWebServices {

	public Person checkAuthUserAccount(String login, String password);
	public List<Account> getListAccountByPerson(Person person);
	public List<Account> getAllAccountsByEmailAndPassword(String email, String password);
}
