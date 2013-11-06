package org.vaadin.mockapp.authentication;

/**
 * Interface defining a simple login service that authenticates users based on a username and password.
 *
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
}
