package org.vaadin.mockapp.backend;

/**
 * @author petter@vaadin.com
 */
public final class MockAppRoles {

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";
    public static final String ROLE_OBSERVER = "observer";
    public static final String[] ALL_ROLES = {ROLE_ADMIN, ROLE_USER, ROLE_OBSERVER};

    private MockAppRoles() {
    }
}
