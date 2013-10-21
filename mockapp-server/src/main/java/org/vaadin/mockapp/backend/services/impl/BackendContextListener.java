package org.vaadin.mockapp.backend.services.impl;

import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.services.LoginService;
import org.vaadin.mockapp.backend.services.OrderService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author petter@vaadin.com
 */
@WebListener
public class BackendContextListener implements ServletContextListener {

    private MockLoginService loginService;
    private MockOrderService orderService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        loginService = new MockLoginService();
        Services.register(loginService, LoginService.class);

        orderService = new MockOrderService();
        Services.register(orderService, OrderService.class);
        orderService.createMockData();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Services.remove(OrderService.class);
        Services.remove(LoginService.class);
    }
}
