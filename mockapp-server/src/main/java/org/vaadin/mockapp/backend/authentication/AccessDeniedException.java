package org.vaadin.mockapp.backend.authentication;

/**
 * @author petter@vaadin.com
 */
public class AccessDeniedException extends RuntimeException {

    public static void requireAnyOf(String... roles) throws AccessDeniedException {
        for (String role : roles) {
            if (AuthenticationHolder.getAuthentication().hasRole(role)) {
                return;
            }
        }
        throw new AccessDeniedException();
    }

    public static void requireAllOf(String... roles) throws AccessDeniedException {
        for (String role : roles) {
            if (!AuthenticationHolder.getAuthentication().hasRole(role)) {
                throw new AccessDeniedException();
            }
        }
    }

}
