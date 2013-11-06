package org.vaadin.mockapp.samples.login;

import org.vaadin.mockapp.authentication.Authentication;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple implementation of {@link org.vaadin.mockapp.authentication.Authentication} that is used by {@link org.vaadin.mockapp.samples.login.MockLoginService}.
 *
 * @author petter@vaadin.com
 */
public class SimpleAuthentication implements Authentication, HttpSessionBindingListener {

    private final String name;
    private final Set<String> roles;

    public SimpleAuthentication(String name, String... roles) {
        this.name = name;
        this.roles = new HashSet<String>(Arrays.asList(roles));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasRole(String roleName) {
        return roles.contains(roleName);
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("Authentication bound to session " + event.getSession().getId());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("Authentication unbound from session " + event.getSession().getId());
    }
}
