package com.supinbank.advisor.customer;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import com.supinbank.account.Account;
import com.supinbank.account.enums.AccountTransfertEnums;
import com.supinbank.auth.managedbeans.AccessManager;
import com.supinbank.auth.tools.HexadecimalConverter;
import com.supinbank.humanresources.Customer;
import com.supinbank.session.remote.SupinBankSession;
import com.supinbank.tools.SendMail;

public class AddAccount extends AccessManager {
	
	@EJB
	private SupinBankSession session;
	private Customer customer;
	private Account account;
	private List<SelectItem> listSelectItemAccountTransfert;
	
	private static final String charset = "0123456789abcdefghijklmnopqrstuvwxyz";
	
	public AddAccount() throws IOException {
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
	
	public String AddAccountCustomer() throws NoSuchAlgorithmException{
		getAccount().setAmount(0);
		getAccount().setCustomer(getCustomer());
		
		if(getCustomer().getListAccount() == null){
			getCustomer().setListAccount(new ArrayList<Account>());
		}
		getCustomer().getListAccount().add(getAccount());
		getCustomer().setLogin(getCustomer().getEmail());
		
		String password = getRandomString(6);
		String passwordMd5= HexadecimalConverter.convertToHex(MessageDigest.getInstance("MD5").digest(password.getBytes()));
		
		getCustomer().setPassword(passwordMd5);
		
		customer = session.addCustomer(getCustomer());
		
		for (Account account : customer.getListAccount()) {
			if(account.getName().equals(getAccount().getName())){
				setAccount(account);
			}
		}
		
		System.out.println("id account : "+getAccount().getId());
		String bban = "75689/00000/"; 
		long bbanCalcul = 7568900000L;
		String idAccount =	getAccount().getId().toString();
		if(idAccount.length() == 1){
			idAccount = "0000000000"+idAccount;
		}
		else if(idAccount.length() == 2){
			idAccount = "000000000"+idAccount;
		}
		else if(idAccount.length() == 3){
			idAccount = "00000000"+idAccount;
		}else if(idAccount.length() == 4){
			idAccount = "0000000"+idAccount;
		}
		
		bban = bban+idAccount;
		bbanCalcul = Long.parseLong(String.valueOf(bbanCalcul)+idAccount.substring(5));
		long key = 0;
		key = 97-(bbanCalcul*100%97);
		if(String.valueOf(key).length() == 1)
			bban = bban+"/0"+String.valueOf(key);
		else
			bban = bban+"/"+String.valueOf(key);
		
		getAccount().setbBan(bban);
		
		
		session.editAccount(getAccount());
		
		SendMail.sendMail(customer.getEmail(),"Supinbank : your account informations",
				"bonjour "+customer.getFirstName()+" "+customer.getLastName().toUpperCase()+", <br /> voici vos identifiants <br />---------------------------------<br />  login : "+customer.getLogin()+" <br /> password : "+password+"<br />---------------------------------<br /><br />Cordialement<br /><br />Supinbank  ", new Date());
		
		
		return "listCustomer";
		
		
	}
	
	 public static String getRandomString(int length) {
	        Random rand = new Random(System.currentTimeMillis());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < length; i++) {
	            int pos = rand.nextInt(charset.length());
	            sb.append(charset.charAt(pos));
	        }
	        return sb.toString();
	    }
	

}
