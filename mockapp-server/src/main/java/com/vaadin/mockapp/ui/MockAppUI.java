package com.vaadin.mockapp.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.mockapp.ui.components.LoginScreen;
import com.vaadin.mockapp.ui.components.MainScreen;
import com.vaadin.mockapp.ui.theme.MockAppTheme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.ui.UI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author petter@vaadin.com
 */
@Theme(MockAppTheme.THEME_NAME)
@Widgetset("com.vaadin.mockapp.ui.widgetset.MockAppWidgetset")
public class MockAppUI extends UI implements LoginScreen.Callback, MainScreen.Callback {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("Mock App UI");
        if (isLoggedIn()) {
            showMainScreen();
        } else {
            showLoginScreen();
        }
    }

    @Override
    public boolean login(String username, String password) {
        VaadinServletRequest request = (VaadinServletRequest) VaadinService.getCurrentRequest();
        try {
            request.login(username, password);
            showMainScreen();
            return true;
        } catch (ServletException e) {
            Logger.getLogger(getClass().getCanonicalName()).log(Level.WARNING, "Login failed", e);
            return false;
        }
    }

    private boolean isLoggedIn() {
        return VaadinService.getCurrentRequest().getUserPrincipal() != null;
    }

    private void showMainScreen() {
        setContent(new MainScreen(this));
    }

    private void showLoginScreen() {
        setContent(new LoginScreen(this));
    }

    @Override
    public void logout() {
        VaadinServletRequest request = (VaadinServletRequest) VaadinService.getCurrentRequest();
        try {
            request.logout();
        } catch (ServletException e) {
            Logger.getLogger(getClass().getCanonicalName()).log(Level.WARNING, "Logout failed", e);
        }
        getSession().close();
        getPage().reload();
    }

    @WebServlet(urlPatterns = "/*", name = "MockAppUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MockAppUI.class, productionMode = false)
    public static class MockAppUIServlet extends VaadinServlet {
    }
}
