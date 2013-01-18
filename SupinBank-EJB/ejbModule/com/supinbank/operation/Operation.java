package com.supinbank.operation;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.NotNull;

import com.supinbank.account.Account;
import com.supinbank.account.enums.OperationTypeEnums;

@Entity
@Table(name="operation")
@NamedQueries({
	@NamedQuery(name="getListOperationByAccountCredit",query="SELECT operation FROM Operation AS operation WHERE operation.account = :account AND operation.operationType = :credit"),
	@NamedQuery(name="getListOperationByAccount",query="SELECT operation FROM Operation AS operation WHERE operation.account = :account")

})
public class Operation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private String libelle;
	@NotNull
	private float price;
	@ManyToOne
	private Account account;
	@Enumerated(EnumType.ORDINAL)
	private OperationTypeEnums operationType;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;
	
	
	
	public OperationTypeEnums getOperationType() {
		return operationType;
	}
	public void setOperationType(OperationTypeEnums operationType) {
		this.operationType = operationType;
	}
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getId() {
		return id;
	}
	public String getLibelle() {
		return libelle;
	}
	public float getPrice() {
		return price;
	}
	@XmlTransient
	public Account getAccount() {
		return account;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
	
	

}
