package org.vaadin.mockapp.authentication;

/**
 * Exception thrown then the user attempted to perform an operation without sufficient privileges. This class
 * also contains some static helper methods for enforcing role-based security.
 *
 * @author petter@vaadin.com
 * @see #requireAllOf(String...)
 * @see #requireAnyOf(String...)
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Throws an {@code AccessDeniedException} if the current user does not have any of the specified roles.
     *
     * @param roles the allowed roles.
     */
    public static void requireAnyOf(String... roles) {
        for (String role : roles) {
            if (AuthenticationHolder.getAuthentication().hasRole(role)) {
                return;
            }
        }
        throw new AccessDeniedException();
    }

    /**
     * Throws an {@code AccessDeniedException} if the current user does not have all of the specified roles.
     *
     * @param roles the required roles.
     */
    public static void requireAllOf(String... roles) {
        for (String role : roles) {
            if (!AuthenticationHolder.getAuthentication().hasRole(role)) {
                throw new AccessDeniedException();
            }
        }
    }

}
