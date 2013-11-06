package org.vaadin.mockapp.samples.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
public class SampleMaster implements Serializable, Cloneable {

    private UUID uuid;
    private String stringProperty;
    private Integer integerProperty;
    private BigDecimal bigDecimalProperty;
    private Boolean booleanProperty;
    private SampleEmbedded embeddedProperty;
    private Set<SampleDetail> details;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

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

    public BigDecimal getBigDecimalProperty() {
        return bigDecimalProperty;
    }

    public void setBigDecimalProperty(BigDecimal bigDecimalProperty) {
        this.bigDecimalProperty = bigDecimalProperty;
    }

    public Boolean getBooleanProperty() {
        return booleanProperty;
    }

    public void setBooleanProperty(Boolean booleanProperty) {
        this.booleanProperty = booleanProperty;
    }

    public SampleEmbedded getEmbeddedProperty() {
        return embeddedProperty;
    }

    public void setEmbeddedProperty(SampleEmbedded embeddedProperty) {
        this.embeddedProperty = embeddedProperty;
    }

    public Set<SampleDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<SampleDetail> details) {
        this.details = details;
    }

    @Override
    public SampleMaster clone() throws CloneNotSupportedException {
        SampleMaster clone = (SampleMaster) super.clone();
        if (embeddedProperty != null) {
            clone.embeddedProperty = embeddedProperty.clone();
        }
        if (details != null) {
            clone.details = new HashSet<SampleDetail>();
            for (SampleDetail originalDetail : details) {
                clone.details.add(originalDetail.clone());
            }
        }
        return clone;
    }
}
