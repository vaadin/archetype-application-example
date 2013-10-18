package org.vaadin.mockapp.backend.domain;

import org.joda.time.DateTime;
import org.vaadin.mockapp.backend.BaseDomain;

/**
 * @author petter@vaadin.com
 */
public class EvacuationCenter extends BaseDomain {

    private String name;
    private Address address = new Address();
    private DateTime opened;
    private DateTime closed;

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

    public DateTime getOpened() {
        return opened;
    }

    public void setOpened(DateTime opened) {
        this.opened = opened;
    }

    public DateTime getClosed() {
        return closed;
    }

    public boolean isClosed() {
        return closed != null;
    }

    public void setClosed(DateTime closed) {
        this.closed = closed;
    }

    @Override
    public BaseDomain clone() throws CloneNotSupportedException {
        EvacuationCenter clone = (EvacuationCenter) super.clone();
        clone.address = address.clone();
        return clone;
    }
}
