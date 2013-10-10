package com.vaadin.mockapp.backend.authentication;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Interface representing an authentication token that identifies a user and contains information about his or her permissions.
 * The current authentication token can always be accessed by calling {@link com.vaadin.mockapp.backend.authentication.AuthenticationHolder#getAuthentication()} .
 *
 * @author petter@vaadin.com
 */
public interface Authentication extends Serializable {

    /**
     * {@code Authentication} object that indicates that the user is not authenticated. This is basically the Null Pattern
     * but with a different name.
     */
    Authentication ANONYMOUS = new Authentication() {

        @NotNull
        @Override
        public String getName() {
            return "Anonymous";
        }

        @Override
        public boolean hasRole(@NotNull String roleName) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        @Override
        public boolean equals(Object obj) {
            return obj == this;
        }
    };

    /**
     * Returns the name of the authenticated user (never {@code null}).
     */
    @NotNull
    String getName();

    /**
     * Returns whether the authenticated user holds the specified role.
     */
    boolean hasRole(@NotNull String roleName);
}
