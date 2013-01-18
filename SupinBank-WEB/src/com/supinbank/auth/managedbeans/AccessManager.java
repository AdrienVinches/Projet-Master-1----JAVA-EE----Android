package com.supinbank.auth.managedbeans;

import java.io.IOException;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

public class AccessManager {
	
	private SessionUser sessionUser;

	
	public AccessManager() { }
	
	public SessionUser getSessionUser() { 
		if (this.sessionUser == null) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			Application app = ctx.getApplication();
			this.sessionUser = (SessionUser) app.evaluateExpressionGet(ctx, "#{" + "sessionUser" + "}", SessionUser.class);
		}
		return sessionUser;
	}

	public void setSessionUser(SessionUser sessionUser) {
		this.sessionUser = sessionUser;
	}


	public Boolean isLogged() throws IOException {
		/* Check if user is logged */
		if ( this.getSessionUser().getPerson() == null ) {
			return false;
		}
		return true;
	}
	
	public void redirectIfNotLogged() throws IOException {
		if (!this.isLogged()) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/SupinBank-WEB/auth/main.jsf");
		}
	}
	

}
