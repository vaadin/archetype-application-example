package org.vaadin.mockapp.backend.services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

    @Nullable
    Order findByUuid(@NotNull UUID uuid);

    void save(@NotNull Order entity);

    @NotNull
    List<Order> find(@NotNull LocalDate from, @NotNull LocalDate to, @NotNull Set<OrderState> states);

}
