package org.vaadin.mockapp.backend.services.impl;

import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.services.EvacuationCenterService;
import org.vaadin.mockapp.backend.services.EvacueeService;
import org.vaadin.mockapp.backend.services.LoginService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author petter@vaadin.com
 */
@WebListener
public class BackendContextListener implements ServletContextListener {

    private MockLoginService loginService;
    private MockEvacuationCenterService evacuationCenterService;
    private MockEvacueeService evacueeService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        loginService = new MockLoginService();
        Services.register(loginService, LoginService.class);

        evacuationCenterService = new MockEvacuationCenterService();
        Services.register(evacuationCenterService, EvacuationCenterService.class);
        evacuationCenterService.createMockData();

        evacueeService = new MockEvacueeService();
        Services.register(evacueeService, EvacueeService.class);
        evacueeService.createMockData();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Services.remove(EvacueeService.class);
        Services.remove(EvacuationCenterService.class);
        Services.remove(LoginService.class);
    }
}
