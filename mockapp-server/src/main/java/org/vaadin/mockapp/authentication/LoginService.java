package org.vaadin.mockapp.authentication;


/**
 * @author petter@vaadin.com
 */
public interface LoginService {

    /**
     * Attempts to login with the specified username and password and returns the result. If the attempt was successful,
     * the {@link org.vaadin.mockapp.authentication.Authentication} token can be retrieved from {@link org.vaadin.mockapp.authentication.AuthenticationHolder}.
     *
     * @return true if login succeeded, false if it failed.
     */
    boolean login(String username, String password);

    /**
     * Logs the user out, clearing the authentication token from {@link org.vaadin.mockapp.authentication.AuthenticationHolder}. The session remains active after this call, though.
     */
    void logout();

}
