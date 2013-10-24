package org.vaadin.mockapp.backend.domain;

import org.joda.time.DateTime;
import org.vaadin.mockapp.backend.BaseDomain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author petter@vaadin.com
 */
public class Order extends BaseDomain {

    private Integer orderNo;
    private DateTime orderReceived;
    private String orderReceivedBy;
    private ContactMethod orderReceivedVia;
    private String customerName;
    private Address customerAddress = new Address();
    private String customerEmail;
    private String customerPhone;
    private Address billingAddress = new Address();
    private OrderState state = OrderState.RECEIVED;
    private List<OrderItem> items = new ArrayList<OrderItem>();

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public DateTime getOrderReceived() {
        return orderReceived;
    }

    public void setOrderReceived(DateTime orderReceived) {
        this.orderReceived = orderReceived;
    }

    public String getOrderReceivedBy() {
        return orderReceivedBy;
    }

    public void setOrderReceivedBy(String orderReceivedBy) {
        this.orderReceivedBy = orderReceivedBy;
    }

    public ContactMethod getOrderReceivedVia() {
        return orderReceivedVia;
    }

    public void setOrderReceivedVia(ContactMethod orderReceivedVia) {
        this.orderReceivedVia = orderReceivedVia;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress == null ? new Address() : ObjectUtils.nullSafeClone(customerAddress);
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress == null ? new Address() : ObjectUtils.nullSafeClone(billingAddress);
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = new ArrayList<OrderItem>(items.size());
        for (OrderItem originalItem : items) {
            this.items.add(ObjectUtils.nullSafeClone(originalItem));
        }
    }

    public BigDecimal getTotalWithoutTax() {
        BigDecimal sum = BigDecimal.ZERO;
        for (OrderItem item : items) {
            sum = sum.add(item.getTotalWithoutTax());
        }
        return sum;
    }

    public BigDecimal getTotalTax() {
        BigDecimal sum = BigDecimal.ZERO;
        for (OrderItem item : items) {
            sum = sum.add(item.getTotalTax());
        }
        return sum;
    }

    public BigDecimal getTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (OrderItem item : items) {
            sum = sum.add(item.getTotalTax()).add(item.getTotalWithoutTax());
        }
        return sum;
    }

    @Override
    public BaseDomain clone() throws CloneNotSupportedException {
        Order clone = (Order) super.clone();
        clone.setCustomerAddress(customerAddress);
        clone.setBillingAddress(billingAddress);
        clone.setItems(items);
        return clone;
    }
}
