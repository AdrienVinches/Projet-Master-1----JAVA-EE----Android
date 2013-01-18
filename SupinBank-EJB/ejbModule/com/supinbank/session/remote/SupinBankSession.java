package com.supinbank.session.remote;

import java.util.List;

import javax.ejb.Remote;
import javax.jws.WebService;

import com.supinbank.account.Account;
import com.supinbank.humanresources.Customer;
import com.supinbank.humanresources.Person;
import com.supinbank.operation.Operation;

@Remote
public interface SupinBankSession {
	
	public Person checkAuthUserAccount(String login, String password);
	
	public List<Account> getListAccountByPerson(Person person);
	public Person addPerson(Person person);
	public Person editPerson(Person person);
	public Customer addCustomer(Customer customer);
	public Customer editCustomer(Customer customer);
	public List<Customer> getListCustomer();
	public Customer getCustomerByAccount(Account account);
	public Operation addOperation(Operation operation);
	public List<Operation> getListOperationByAccountCredit(Account account);
	public List<Operation> getListOperationByAccountDebit(Account account);
	public List<Operation> getListOperationByAccount(Account account);
	public Account editAccount(Account account);
	public Account getAccountById(long id);
	public Account getAccountByPersonAndName(Person person, String name);
	public Account getAccountByBban(String bban);
	
}
