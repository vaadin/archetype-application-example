package org.vaadin.mockapp.backend.domain;

/**
 * @author petter@vaadin.com
 */
public enum ContactMethod {
    PHONE("Phone"),
    EMAIL("E-mail"),
    FAX("Fax"),
    WEB("Web"),
    MAIL("Mail"),
    PERSONALLY("Personally");
    private final String displayName;

    private ContactMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
