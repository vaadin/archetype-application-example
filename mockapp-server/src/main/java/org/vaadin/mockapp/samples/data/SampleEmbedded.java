package org.vaadin.mockapp.samples.data;

import java.io.Serializable;

/**
 * @author petter@vaadin.com
 */
public class SampleEmbedded implements Serializable, Cloneable {

    private SampleEnum enumProperty;

    public SampleEnum getEnumProperty() {
        return enumProperty;
    }

    public void setEnumProperty(SampleEnum enumProperty) {
        this.enumProperty = enumProperty;
    }

    @Override
    public SampleEmbedded clone() throws CloneNotSupportedException {
        return (SampleEmbedded) super.clone();
    }
}
