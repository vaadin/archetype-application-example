package com.vaadin.mockapp.backend.impl;

import com.vaadin.mockapp.backend.LoginService;
import com.vaadin.mockapp.backend.Services;
import com.vaadin.mockapp.backend.authentication.AuthenticationHolder;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Very simple implementation of {@link LoginService}. <b>Do not use in real applications!</b>
 *
 * @author petter@vaadin.com
 */
@WebListener
public class MockLoginService implements LoginService, ServletContextListener {

    @Override
    public boolean login(@NotNull String username, @NotNull String password) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            AuthenticationHolder.setAuthentication(new SimpleAuthentication("admin", "ADMIN"));
            return true;
        } else if ("user".equals(username) && "user123".equals(password)) {
            AuthenticationHolder.setAuthentication(new SimpleAuthentication("user", "USER"));
            return true;
        }
        Logger.getLogger(MockLoginService.class.getCanonicalName()).log(Level.WARNING, "Login failed for user {0}", username);
        return false;
    }

    @Override
    public void logout() {
        AuthenticationHolder.setAuthentication(null);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Services.register(this, LoginService.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Services.remove(LoginService.class);
    }
}
