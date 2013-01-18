package com.supinbank.session.remote;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.supinbank.account.Account;
import com.supinbank.account.enums.OperationTypeEnums;
import com.supinbank.auth.tools.HexadecimalConverter;
import com.supinbank.humanresources.Customer;
import com.supinbank.humanresources.Person;
import com.supinbank.operation.Operation;

@Stateless
public class SunpinBankSessionBean implements SupinBankSession {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public Person checkAuthUserAccount(String login, String password) {
		if (!login.equals("") && !password.equals("")) {
			Person person = null;
			String md5Password;
			try {
				md5Password = HexadecimalConverter.convertToHex(MessageDigest
						.getInstance("MD5").digest(password.getBytes()));
				System.out.println("login : >>" + login + "<<");
				System.out.println("password : >>" + md5Password + "<<");
				System.out.println("Tentative de connexion > LOGIN : " + login
						+ " - MDP : " + md5Password);
				Query query = em.createNamedQuery("checkAuthPerson");
				query.setParameter("login", login);
				query.setParameter("password", md5Password);
				person = (Person) query.getSingleResult();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return person;
		}
		return null;
	}

	public List<Account> getListAccountByPerson(Person person) {
		if (person != null) {
			Query query = em.createNamedQuery("getListAccountByPerson");
			query.setParameter("person", person);
			List<Account> listaccount = new ArrayList<Account>();
			try {
				listaccount = (List<Account>) query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return listaccount;
		}
		return null;
	}

	public Person addPerson(Person person) {
		if (person != null) {
			em.persist(person);
			return person;
		}
		return person;
	}

	public Person editPerson(Person person) {
		if (person != null) {
			em.merge(person);
			return person;
		}
		return person;
	}

	public Customer addCustomer(Customer customer) {
		if (customer != null) {
			em.persist(customer);
			return customer;
		}
		return customer;
	}

	public Customer editCustomer(Customer customer) {
		if (customer != null) {
			em.merge(customer);
			return customer;
		}
		return customer;
	}

	@Override
	public List<Customer> getListCustomer() {
		Query query = em.createNamedQuery("getListCustomer");
		List<Customer> listCustomer = new ArrayList<Customer>();
		try {
			listCustomer = (List<Customer>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return listCustomer;
	}

	@Override
	public Customer getCustomerByAccount(Account account) {
		if (account != null) {
			Query query = em.createNamedQuery("getCustomerByAccount");
			query.setParameter("account", account);
			Customer customer = new Customer();
			try {
				customer = (Customer) query.getSingleResult();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return customer;
		}
		return null;
	}

	@Override
	public Operation addOperation(Operation operation) {
		if (operation != null) {
			em.persist(operation);
			return operation;
		}
		return operation;
	}
	
	@Override
	public List<Operation> getListOperationByAccountCredit(Account account){
		if(account != null){
			Query query = em.createNamedQuery("getListOperationByAccountCredit");
			query.setParameter("credit", OperationTypeEnums.credit);
			query.setParameter("account", account);
			List<Operation> listOperation = new ArrayList<Operation>();
			try {
				listOperation = (List<Operation>) query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return listOperation;
	}
		return null;
	}
	
	@Override
	public List<Operation> getListOperationByAccountDebit(Account account){
		if(account != null){
			Query query = em.createNamedQuery("getListOperationByAccountCredit");
			query.setParameter("credit", OperationTypeEnums.debit);
			query.setParameter("account", account);
			List<Operation> listOperation = new ArrayList<Operation>();
			try {
				listOperation = (List<Operation>) query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return listOperation;
	}
		return null;
	}

	@Override
	public Account editAccount(Account account) {
		if (account != null) {
			em.merge(account);
			return account;
		}
		return account;
	}

	@Override
	public List<Operation> getListOperationByAccount(Account account) {
		if(account != null){
			Query query = em.createNamedQuery("getListOperationByAccount");
			query.setParameter("account", account);
			List<Operation> listOperation = new ArrayList<Operation>();
			try {
				listOperation = (List<Operation>) query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return listOperation;
	}
		return null;
	}

	@Override
	public Account getAccountById(long id) {
			Query query = em.createNamedQuery("getAccountById");
			query.setParameter("id", id);
			Account account = new Account();
			try {
				account = (Account) query.getSingleResult();
				System.out.println("account name "+account.getName());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return account;
	}

	@Override
	public Account getAccountByPersonAndName(Person person, String name) {
		if(person != null && name != ""){
			Query query = em.createNamedQuery("getAccountByPersonAndName");
			query.setParameter("customer", person);
			query.setParameter("name", name);
			Account account = new Account();
			try {
				account = (Account) query.getSingleResult();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return account;
	}
		return null;
	}

	@Override
	public Account getAccountByBban(String bban) {
		if(bban != null && bban != ""){
			Query query = em.createNamedQuery("getAccountByBban");
			query.setParameter("bban", bban);
			Account account = new Account();
			try {
				account = (Account) query.getSingleResult();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return account;
	}
		return null;
	}

	

}
