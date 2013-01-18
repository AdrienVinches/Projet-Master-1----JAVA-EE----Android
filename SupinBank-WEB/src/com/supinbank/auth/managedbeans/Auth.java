package com.supinbank.auth.managedbeans;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.supinbank.customer.account.ListAccount;
import com.supinbank.humanresources.Advisor;
import com.supinbank.humanresources.Customer;
import com.supinbank.humanresources.Person;
import com.supinbank.session.remote.SupinBankSession;


public class Auth {

	private String formLogin;
	private String formPassword;
	
	
	
	 
	
	public String getFormLogin() {
		return formLogin;
	}


	public String getFormPassword() {
		return formPassword;
	}


	public SupinBankSession getSession() {
		return session;
	}


	public void setFormLogin(String formLogin) {
		this.formLogin = formLogin;
	}


	public void setFormPassword(String formPassword) {
		this.formPassword = formPassword;
	}


	public void setSession(SupinBankSession session) {
		this.session = session;
	}


	@EJB
	private SupinBankSession session;

	public String goToWelcomePageCustomer(){
		FacesContext ctx = FacesContext.getCurrentInstance();
		Application app = ctx.getApplication();
		SessionUser sessionUser = (SessionUser) app.evaluateExpressionGet(ctx, "#{" + "sessionUser" + "}", SessionUser.class);
		Person person = sessionUser.getPerson();
		
		FacesContext ctx2 = FacesContext.getCurrentInstance();
		Application app2 = ctx2.getApplication();
		ListAccount listAccount = (ListAccount) app2.evaluateExpressionGet(ctx2, "#{" + "listAccount" + "}", ListAccount.class);
		
		listAccount.setPerson(person);
		listAccount.setAccountDataModel(null);
		
		return "welcome_page_customer";
	}		

	
	
public String submitLogOut(){
	FacesContext ctx = FacesContext.getCurrentInstance();
	Application app = ctx.getApplication();
	SessionUser sessionUser = (SessionUser) app.evaluateExpressionGet(ctx, "#{" + "sessionUser" + "}", SessionUser.class);
	sessionUser.setPerson(null);
	return "main";
}		

	
public String submitLogIn() {
		
		Person person = getSession().checkAuthUserAccount(formLogin, formPassword);
		
		if (person != null) {
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			Application app = ctx.getApplication();
			SessionUser sessionUser = (SessionUser) app.evaluateExpressionGet(ctx, "#{" + "sessionUser" + "}", SessionUser.class);
			sessionUser.setPerson(person);
			if(person.getClass().equals(Customer.class)){
				FacesContext ctx2 = FacesContext.getCurrentInstance();
				Application app2 = ctx2.getApplication();
				ListAccount listAccount = (ListAccount)  app2.evaluateExpressionGet(ctx2, "#{" + "listAccount" + "}", ListAccount.class);
				listAccount.setPerson(person);
				System.out.println(person.getLastName());
				
				return "welcome_page_customer";
			}
			else if(person.getClass().equals(Advisor.class)){
				return "listCustomer";
			}
			else{
				FacesMessage myFacesMessage = new FacesMessage();
				myFacesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
				myFacesMessage.setSummary(String.format("Login ou password incorrect"));
				myFacesMessage.setDetail("");
				
				FacesContext myFacesContext = FacesContext.getCurrentInstance();
				myFacesContext.addMessage(null, myFacesMessage);
				return "";
			}
		} else {
			FacesMessage myFacesMessage = new FacesMessage();
			myFacesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			myFacesMessage.setSummary(String.format("Login ou password incorrect"));
			myFacesMessage.setDetail("");
			
			FacesContext myFacesContext = FacesContext.getCurrentInstance();
			myFacesContext.addMessage(null, myFacesMessage);
			return "";
		}
	}

}
