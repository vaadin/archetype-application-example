package org.vaadin.mockapp.samples.data;

import java.io.Serializable;

/**
 * @author petter@vaadin.com
 */
public class SampleDetail implements Serializable, Cloneable {

    private String stringProperty;
    private Integer integerProperty;

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public Integer getIntegerProperty() {
        return integerProperty;
    }

    public void setIntegerProperty(Integer integerProperty) {
        this.integerProperty = integerProperty;
    }

    @Override
    public SampleDetail clone() throws CloneNotSupportedException {
        return (SampleDetail) super.clone();
    }
}
