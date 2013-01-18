package com.supinbank.humanresources;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import com.supinbank.account.Account;

@Entity
@Table(name="customer")
@NamedQueries({
	@NamedQuery(name="getListCustomer",query="SELECT customer FROM Customer AS customer"),
	@NamedQuery(name="getCustomerByAccount",query="SELECT customer FROM Customer AS customer, IN(customer.listAccount) listCustomerAccount WHERE listCustomerAccount = :account")
})
public class Customer extends Person implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="customer",fetch=FetchType.EAGER,cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private List<Account> listAccount;

	@XmlTransient
	public List<Account> getListAccount() {
		return listAccount;
	}

	public void setListAccount(List<Account> listAccount) {
		this.listAccount = listAccount;
	}
	
	
	
	

}
