package org.vaadin.mockapp.backend.domain;

/**
 * @author petter@vaadin.com
 */
public enum OrderState {
    RECEIVED,
    WAITING_FOR_ITEMS,
    COLLECTING_ITEMS,
    PACKAGING,
    WAITING_FOR_SHIPMENT,
    SHIPPING,
    SHIPPED,
    CANCELLED
}
