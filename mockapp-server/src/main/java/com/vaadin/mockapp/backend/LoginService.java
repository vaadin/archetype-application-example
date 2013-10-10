package com.vaadin.mockapp.backend;

import javax.annotation.Nonnull;

/**
 * @author petter@vaadin.com
 */
public interface LoginService {

    /**
     * Attempts to login with the specified username and password and returns the result. If the attempt was successful,
     * the {@link com.vaadin.mockapp.backend.authentication.Authentication} token can be retrieved from {@link com.vaadin.mockapp.backend.authentication.AuthenticationHolder}.
     *
     * @return true if login succeeded, false if it failed.
     */
    boolean login(@Nonnull String username, @Nonnull String password);

    /**
     * Logs the user out, clearing the authentication token from {@link com.vaadin.mockapp.backend.authentication.AuthenticationHolder}. The session remains active after this call, though.
     */
    void logout();

}
