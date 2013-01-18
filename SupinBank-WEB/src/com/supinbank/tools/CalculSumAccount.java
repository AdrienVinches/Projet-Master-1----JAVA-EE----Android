package com.supinbank.tools;

import java.util.List;

import javax.ejb.EJB;

import com.supinbank.account.Account;
import com.supinbank.operation.Operation;
import com.supinbank.session.remote.SupinBankSession;

public class CalculSumAccount {
	
	
	public static float calculSum(SupinBankSession session, Account account){
		
		float credit = 0;
		float debit = 0;
		float amount = 0;
		
		List<Operation> listCredit = session.getListOperationByAccountCredit(account);
		if(listCredit != null){
			for (Operation operation : listCredit) {
				credit += operation.getPrice();
			}
		}else{
			credit = 0;
		}
		
		List<Operation> listDebit = session.getListOperationByAccountDebit(account);
		if(listDebit != null){
			for (Operation operation : listDebit) {
				debit += operation.getPrice();
			}
		}else{
			debit = 0;
		}
		
		amount = credit - debit;
		System.out.println("credit : "+credit+" - debit : "+debit);
		
		return amount;
	}

}
