package org.vaadin.mockapp.backend.services;

import org.joda.time.LocalDate;
import org.vaadin.mockapp.backend.domain.Order;
import org.vaadin.mockapp.backend.domain.OrderState;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
public interface OrderService {

    Order findByUuid(UUID uuid);

    void save(Order entity);

    List<Order> find(LocalDate from, LocalDate to, Set<OrderState> states);

}
