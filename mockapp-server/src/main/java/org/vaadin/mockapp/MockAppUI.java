package org.vaadin.mockapp;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.vaadin.mockapp.authentication.AuthenticationHolder;
import org.vaadin.mockapp.authentication.LoginScreen;
import org.vaadin.mockapp.samples.MainScreen;

import javax.servlet.annotation.WebServlet;

/**
 * @author petter@vaadin.com
 */
@Theme("mockapp")
@Widgetset("org.vaadin.mockapp.widgetset.MockAppWidgetset")
public class MockAppUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("MockApp");
        if (AuthenticationHolder.isAnonymous()) {
            setContent(new LoginScreen(new LoginScreen.Callback() {
                @Override
                public void loginSuccessful() {
                    setContent(new MainScreen(MockAppUI.this));
                }
            }));
        } else {
            setContent(new MainScreen(this));
        }
    }

    @WebServlet(urlPatterns = "/*", name = "MockAppUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MockAppUI.class, productionMode = false)
    public static class MockAppUIServlet extends VaadinServlet {
    }
}
