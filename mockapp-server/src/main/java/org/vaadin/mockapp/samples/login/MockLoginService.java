package org.vaadin.mockapp.samples.login;

import org.vaadin.mockapp.authentication.AuthenticationHolder;
import org.vaadin.mockapp.authentication.LoginService;

/**
 * Very simple implementation of {@link org.vaadin.mockapp.authentication.LoginService}. <b>Do not use in real applications!</b>
 *
 * @author petter@vaadin.com
 */
public class MockLoginService implements LoginService {

    @Override
    public boolean login(String username, String password) {
        if ("p".equals(password)) {
            AuthenticationHolder.setAuthentication(new SimpleAuthentication(username, "USER"));
            return true;
        }
        return false;
    }
}
