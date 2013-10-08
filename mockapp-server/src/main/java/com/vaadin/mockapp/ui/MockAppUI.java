package com.vaadin.mockapp.ui;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.mockapp.ui.theme.MockAppTheme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import javax.servlet.annotation.WebServlet;

/**
 * @author petter@vaadin.com
 */
@Theme(MockAppTheme.THEME_NAME)
@Widgetset("com.vaadin.mockapp.ui.widgetset.MockAppWidgetset")
@Push
public class MockAppUI extends UI {

    @WebServlet(urlPatterns = "/*", name = "MockAppUIServlet")
    @VaadinServletConfiguration(ui = MockAppUI.class, productionMode = false)
    public static class MockAppUIServlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
