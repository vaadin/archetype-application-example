package org.vaadin.mockapp.backend;

/**
 * @author petter@vaadin.com
 */
public final class MockAppRoles {

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_DOCTOR = "doctor";
    public static final String ROLE_RECEPTIONIST = "receptionist";
    public static final String[] ALL_ROLES = {ROLE_ADMIN, ROLE_DOCTOR, ROLE_RECEPTIONIST};

    private MockAppRoles() {
    }
}
