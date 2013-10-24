package org.vaadin.mockapp.backend.services.impl;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.backend.authentication.AccessDeniedException;
import org.vaadin.mockapp.backend.authentication.AuthenticationHolder;
import org.vaadin.mockapp.backend.domain.ContactMethod;
import org.vaadin.mockapp.backend.domain.ObjectUtils;
import org.vaadin.mockapp.backend.domain.Order;
import org.vaadin.mockapp.backend.domain.OrderState;
import org.vaadin.mockapp.backend.services.OrderService;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author petter@vaadin.com
 */
public class MockOrderService implements OrderService {

    private final AtomicInteger nextFreeOrderNo = new AtomicInteger(1);
    private final Map<UUID, Order> entityMap = new HashMap<UUID, Order>();

    void createMockData() {
        for (int i = 0; i < 2000; ++i) {
            Order order = new Order();
            order.setUuid(UUID.randomUUID());
            order.setCreateTimestamp(new DateTime());
            order.setCreateUserName("root");
            order.setOrderNo(nextFreeOrderNo.getAndIncrement());
            order.setState(MockDataUtils.selectRandom(OrderState.values()));
            order.setOrderReceived(MockDataUtils.getRandomDateTime(1990, new YearMonth().getYear()));
            order.setOrderReceivedVia(MockDataUtils.selectRandom(ContactMethod.values()));
            order.setOrderReceivedBy(MockDataUtils.getRandomName());
            order.setCustomerName(MockDataUtils.getRandomName());
            order.setCustomerAddress(MockDataUtils.getRandomStreetAddress());
            order.setCustomerPhone(MockDataUtils.getRandomPhoneNumber());
            // TODO E-mail
            // TODO Billing address
            // TODO Items
            entityMap.put(order.getUuid(), order);
        }
    }

    @Override
    public synchronized Order findByUuid(UUID uuid) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ALL_ROLES);
        return ObjectUtils.nullSafeClone(entityMap.get(uuid));
    }

    @Override
    public synchronized void save(Order order) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_SALESMAN);
        if (order.getUuid() == null || !entityMap.containsKey(order.getUuid())) {
            order.setUuid(UUID.randomUUID());
            order.setCreateTimestamp(new DateTime());
            order.setCreateUserName(AuthenticationHolder.getAuthentication().getName());
            order.setUpdateTimestamp(null);
            order.setUpdateUserName(null);
        } else {
            order.setUpdateTimestamp(new DateTime());
            order.setUpdateUserName(AuthenticationHolder.getAuthentication().getName());
        }
        if (order.getOrderNo() == null) {
            order.setOrderNo(nextFreeOrderNo.getAndIncrement());
        }
        entityMap.put(order.getUuid(), ObjectUtils.nullSafeClone(order));
    }

    @Override
    public synchronized List<Order> find(LocalDate from, LocalDate to, Set<OrderState> states) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ALL_ROLES);
        final List<Order> result = new ArrayList<Order>();

        Interval interval = new Interval(from.toDateTimeAtStartOfDay(), to.plusDays(1).toDateTimeAtStartOfDay());

        for (Order order : entityMap.values()) {
            if (interval.contains(order.getOrderReceived()) && states.contains(order.getState())) {
                result.add(ObjectUtils.nullSafeClone(order));
            }
        }

        Collections.sort(result, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                int o = o1.getOrderReceived().compareTo(o2.getOrderReceived());
                if (o == 0) {
                    o = o1.getState().compareTo(o2.getState());
                }
                return o;
            }
        });
        return result;
    }
}
