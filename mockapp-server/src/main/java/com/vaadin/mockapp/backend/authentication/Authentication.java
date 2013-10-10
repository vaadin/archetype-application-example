package com.vaadin.mockapp.backend.authentication;

import javax.annotation.Nonnull;
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

        @Override
        public String getName() {
            return "Anonymous";
        }

        @Override
        public boolean hasRole(String roleName) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this;
        }
    };

    /**
     * Returns the name of the authenticated user (never {@code null}).
     */
    @Nonnull
    String getName();

    /**
     * Returns whether the authenticated user holds the specified role.
     */
    boolean hasRole(@Nonnull String roleName);
}
