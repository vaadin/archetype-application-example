package com.vaadin.mockapp.backend.impl;

import com.vaadin.mockapp.backend.authentication.Authentication;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple implementation of {@link Authentication} that is used by {@link MockLoginService}.
 *
 * @author petter@vaadin.com
 */
public class SimpleAuthentication implements Authentication {

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
}
