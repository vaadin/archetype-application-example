package org.vaadin.mockapp.backend.services;

import org.jetbrains.annotations.NotNull;

/**
 * @author petter@vaadin.com
 */
public interface LoginService {

    /**
     * Attempts to login with the specified username and password and returns the result. If the attempt was successful,
     * the {@link org.vaadin.mockapp.backend.authentication.Authentication} token can be retrieved from {@link org.vaadin.mockapp.backend.authentication.AuthenticationHolder}.
     *
     * @return true if login succeeded, false if it failed.
     */
    boolean login(@NotNull String username, @NotNull String password);

    /**
     * Logs the user out, clearing the authentication token from {@link org.vaadin.mockapp.backend.authentication.AuthenticationHolder}. The session remains active after this call, though.
     */
    void logout();

}
