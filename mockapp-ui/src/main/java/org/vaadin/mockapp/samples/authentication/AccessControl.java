package org.vaadin.mockapp.samples.authentication;

public interface AccessControl {

	public boolean signIn(String username, String password);

	public boolean isUserSignedIn();

	public boolean isUserInRole(String role);

	public String getPrincipalName();
}
