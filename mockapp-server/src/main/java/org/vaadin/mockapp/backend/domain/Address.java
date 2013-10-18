package org.vaadin.mockapp.backend.domain;

import java.io.Serializable;

/**
 * @author petter@vaadin.com
 */
public class Address implements Serializable, Cloneable {

    private String street;
    private String postalCode;
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public Address clone() throws CloneNotSupportedException {
        return (Address) super.clone();
    }
}
