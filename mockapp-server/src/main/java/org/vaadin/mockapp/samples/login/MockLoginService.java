package org.vaadin.mockapp.samples.login;

import org.vaadin.mockapp.authentication.AuthenticationHolder;
import org.vaadin.mockapp.authentication.LoginService;
import org.vaadin.mockapp.backend.MockAppRoles;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Very simple implementation of {@link org.vaadin.mockapp.authentication.LoginService}. <b>Do not use in real applications!</b>
 *
 * @author petter@vaadin.com
 */
public class MockLoginService implements LoginService {

    @Override
    public boolean login(String username, String password) {
        if ("sales".equals(username) && "p".equals(password)) {
            AuthenticationHolder.setAuthentication(new SimpleAuthentication("sales", MockAppRoles.ROLE_SALESMAN));
            return true;
        } else if ("observer".equals(username) && "p".equals(password)) {
            AuthenticationHolder.setAuthentication(new SimpleAuthentication("observer", MockAppRoles.ROLE_OBSERVER));
            return true;
        }
        Logger.getLogger(MockLoginService.class.getCanonicalName()).log(Level.WARNING, "Login failed for user {0}", username);
        return false;
    }

    @Override
    public void logout() {
        AuthenticationHolder.setAuthentication(null);
    }
}
