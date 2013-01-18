package com.supinbank.account;

import java.io.Serializable;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.NotNull;

import com.supinbank.account.enums.AccountTransfertEnums;
import com.supinbank.humanresources.Customer;
import com.supinbank.operation.Operation;

@Entity
@Table(name="account")
@NamedQueries({
	@NamedQuery(name="getListAccountByPerson",query="SELECT account FROM Account AS account WHERE account.customer = :person"),
	@NamedQuery(name="getAccountById",query="SELECT account FROM Account AS account WHERE account.id = :id"),
	@NamedQuery(name="getAccountByPersonAndName",query="SELECT account FROM Account AS account WHERE account.customer = :customer AND account.name = :name"),
	@NamedQuery(name="getAccountByBban",query="SELECT account FROM Account AS account WHERE account.bBan = :bban"),
	@NamedQuery(name="getAllAccountsByEmailAndPassword",query="SELECT account FROM Account AS account WHERE account.customer.login = :login AND account.customer.password = :password "),
})
public class Account implements Serializable {
 
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private String name;
	@Enumerated(EnumType.ORDINAL)
	private AccountTransfertEnums accountTransfertEnums;
	@ManyToOne
	private Customer customer;
	private float amount;
	@OneToMany(mappedBy="account")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Operation> listOperation;
	private String bBan;
	
	
	
	
	
	
	public String getbBan() {
		return bBan;
	}
	public void setbBan(String bBan) {
		this.bBan = bBan;
	}
	public List<Operation> getListOperation() {
		return listOperation;
	}
	public void setListOperation(List<Operation> listOperation) {
		this.listOperation = listOperation;
	}
	public float getAmount() {
		return amount;
	}

	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public AccountTransfertEnums getAccountTransfertEnums() {
		return accountTransfertEnums;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAccountTransfertEnums(AccountTransfertEnums accountTransfertEnums) {
		this.accountTransfertEnums = accountTransfertEnums;
	}
	
	
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((accountTransfertEnums == null) ? 0 : accountTransfertEnums
						.hashCode());
		result = prime * result + Float.floatToIntBits(amount);
		result = prime * result + ((bBan == null) ? 0 : bBan.hashCode());
		result = prime * result
				+ ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountTransfertEnums != other.accountTransfertEnums)
			return false;
		if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
			return false;
		if (bBan == null) {
			if (other.bBan != null)
				return false;
		} else if (!bBan.equals(other.bBan))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
	
	
	
	

}
