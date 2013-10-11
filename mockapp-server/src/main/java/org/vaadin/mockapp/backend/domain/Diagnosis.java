package org.vaadin.mockapp.backend.domain;

import org.vaadin.mockapp.backend.SoftDeletableBaseDomain;

/**
 * @author petter@vaadin.com
 */
public class Diagnosis extends SoftDeletableBaseDomain {

    private String icdCode;
    private String name;

    public String getIcdCode() {
        return icdCode;
    }

    public void setIcdCode(String icdCode) {
        this.icdCode = icdCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
