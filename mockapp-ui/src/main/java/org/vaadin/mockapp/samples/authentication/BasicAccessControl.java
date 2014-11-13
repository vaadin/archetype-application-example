package org.vaadin.mockapp.samples.authentication;

public class BasicAccessControl implements AccessControl {

	@Override
	public boolean signIn(String username, String password) {
		if (username == null || username.isEmpty())
			return false;

		CurrentUser.set(username);
		return true;
	}

	@Override
	public boolean isUserSignedIn() {
		return !CurrentUser.get().isEmpty();
	}

	@Override
	public boolean isUserInRole(String role) {
		if ("admin".equals(role)) {
			// Only the "admin" user is in the "admin" role
			return getPrincipalName().equals("admin");
		}

		// All users are in all non-admin roles
		return true;
	}

	@Override
	public String getPrincipalName() {
		return CurrentUser.get();
	}

}
