package org.vaadin.mockapp.samples.authentication;

public class AllowAllAuthentication implements Authentication {

	@Override
	public boolean validateCredentials(String username, String password) {
		if (username == null || username.isEmpty())
			return false;
		
		return true;
	}

}
