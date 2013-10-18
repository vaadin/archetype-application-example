package org.vaadin.mockapp.backend.domain;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.vaadin.mockapp.backend.BaseDomain;

/**
 * @author petter@vaadin.com
 */
public class Evacuee extends BaseDomain {

    private EvacuationCenter evacuationCenter;
    private DateTime arrived;
    private DateTime left;
    private Integer number;
    private String familyName;
    private String givenNames;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Address evacuationAddress = new Address();
    private Address homeAddress = new Address();
    private String pets;
    private String cellPhone;
    private String notes;

    // TODO Tags

    public EvacuationCenter getEvacuationCenter() {
        return evacuationCenter;
    }

    public void setEvacuationCenter(EvacuationCenter evacuationCenter) {
        this.evacuationCenter = evacuationCenter;
    }

    public DateTime getArrived() {
        return arrived;
    }

    public void setArrived(DateTime arrived) {
        this.arrived = arrived;
    }

    public DateTime getLeft() {
        return left;
    }

    public void setLeft(DateTime left) {
        this.left = left;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenNames() {
        return givenNames;
    }

    public void setGivenNames(String givenNames) {
        this.givenNames = givenNames;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getEvacuationAddress() {
        return evacuationAddress;
    }

    public void setEvacuationAddress(Address evacuationAddress) {
        if (evacuationAddress == null) {
            evacuationAddress = new Address();
        }
        this.evacuationAddress = evacuationAddress;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        if (homeAddress == null) {
            homeAddress = new Address();
        }
        this.homeAddress = homeAddress;
    }

    public String getPets() {
        return pets;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public BaseDomain clone() throws CloneNotSupportedException {
        Evacuee clone = (Evacuee) super.clone();
        clone.homeAddress = homeAddress.clone();
        clone.evacuationAddress = evacuationAddress.clone();
        if (evacuationCenter != null) {
            clone.evacuationCenter = (EvacuationCenter) evacuationCenter.clone();
        }
        return clone;
    }
}
