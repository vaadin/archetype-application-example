package org.vaadin.mockapp.backend.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author petter@vaadin.com
 */
public class OrderItem implements Serializable, Cloneable {

    private String description;
    private BigDecimal qty = BigDecimal.ZERO;
    private String unit;
    private BigDecimal unitPrice = BigDecimal.ZERO;
    private BigDecimal taxPercentage = BigDecimal.ZERO;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        if (qty == null) {
            qty = BigDecimal.ZERO;
        }
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null) {
            unitPrice = BigDecimal.ZERO;
        }
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(BigDecimal taxPercentage) {
        if (taxPercentage == null) {
            taxPercentage = BigDecimal.ZERO;
        }
        this.taxPercentage = taxPercentage;
    }

    public BigDecimal getTotalWithoutTax() {
        return qty.multiply(unitPrice);
    }

    public BigDecimal getTotalTax() {
        return taxPercentage.multiply(getTotalWithoutTax());
    }

    @Override
    public OrderItem clone() throws CloneNotSupportedException {
        return (OrderItem) super.clone();
    }
}
