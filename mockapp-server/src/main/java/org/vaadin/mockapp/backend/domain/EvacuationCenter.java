package org.vaadin.mockapp.backend.domain;

import org.joda.time.DateTime;
import org.vaadin.mockapp.backend.BaseDomain;

/**
 * @author petter@vaadin.com
 */
public class EvacuationCenter extends BaseDomain {

    private String name;
    private Address address = new Address();
    private DateTime openDate;
    private DateTime closeDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if (address == null) {
            address = new Address();
        }
        this.address = address;
    }

    public DateTime getOpenDate() {
        return openDate;
    }

    public void setOpenDate(DateTime opened) {
        this.openDate = opened;
    }

    public DateTime getCloseDate() {
        return closeDate;
    }

    public boolean isClosed() {
        return closeDate != null;
    }

    public void setCloseDate(DateTime closed) {
        this.closeDate = closed;
    }

    @Override
    public BaseDomain clone() throws CloneNotSupportedException {
        EvacuationCenter clone = (EvacuationCenter) super.clone();
        clone.address = address.clone();
        return clone;
    }
}
