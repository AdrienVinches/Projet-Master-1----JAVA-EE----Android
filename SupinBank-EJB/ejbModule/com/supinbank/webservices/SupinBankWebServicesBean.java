package com.supinbank.webservices;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.RemoteBinding;

import com.supinbank.account.Account;
import com.supinbank.account.enums.OperationTypeEnums;
import com.supinbank.auth.tools.HexadecimalConverter;
import com.supinbank.humanresources.Customer;
import com.supinbank.humanresources.Person;
import com.supinbank.operation.Operation;
import com.supinbank.session.remote.SupinBankSession;
@Stateless
@WebService
@RemoteBinding(jndiBinding = "supinbank/remote/SupinBankWebServicesBean")
public class SupinBankWebServicesBean implements SupinBankWebServices {

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

	@Override
	public List<Account> getAllAccountsByEmailAndPassword(String email,
			String password) {
		System.out.println("getAllAccountsByEmailAndPasswordgetAllAccountsByEmailAndPasswordgetAllAccountsByEmailAndPassword "+email+" "+password );
		if (email!= "" && password != "") {
			Query query = em.createNamedQuery("getAllAccountsByEmailAndPassword");
			query.setParameter("login", email);
			query.setParameter("password", password);
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


	
}
