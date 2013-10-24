package org.vaadin.mockapp.backend.domain;

/**
 * @author petter@vaadin.com
 */
public enum OrderState {
    RECEIVED("Received"),
    WAITING_FOR_ITEMS("Waiting for items"),
    COLLECTING_ITEMS("Collecting items"),
    PACKAGING("Packaging"),
    WAITING_FOR_SHIPMENT("Waiting for shipment"),
    SHIPPING("Shipping"),
    SHIPPED("Shipped"),
    CANCELLED("Cancelled");
    private final String displayName;

    private OrderState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
