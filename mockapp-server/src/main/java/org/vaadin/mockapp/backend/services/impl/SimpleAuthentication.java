package org.vaadin.mockapp.backend.services.impl;

import org.vaadin.mockapp.backend.authentication.Authentication;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple implementation of {@link org.vaadin.mockapp.backend.authentication.Authentication} that is used by {@link MockLoginService}.
 *
 * @author petter@vaadin.com
 */
public class SimpleAuthentication implements Authentication {

    private final String name;
    private final Set<String> roles;

    public SimpleAuthentication(@NotNull String name, @NotNull String... roles) {
        this.name = name;
        this.roles = new HashSet<String>(Arrays.asList(roles));
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasRole(@NotNull String roleName) {
        return roles.contains(roleName);
    }
}
