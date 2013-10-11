package org.vaadin.mockapp.backend.domain;

import org.vaadin.mockapp.backend.SoftDeletableBaseDomain;

/**
 * @author petter@vaadin.com
 */
public class Doctor extends SoftDeletableBaseDomain {

    private String firstName;
    private String lastName;
    private String speciality;
    private String code;
    private boolean deleted;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public boolean isDeleted() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDeleted(boolean deleted) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
