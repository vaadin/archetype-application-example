package org.vaadin.mockapp.backend.authentication;

/**
 * @author petter@vaadin.com
 */
public final class Roles {

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_DOCTOR = "doctor";
    public static final String ROLE_RECEPTIONIST = "receptionist";
    public static final String[] ALL_ROLES = {ROLE_ADMIN, ROLE_DOCTOR, ROLE_RECEPTIONIST};

    private Roles() {
    }
}
