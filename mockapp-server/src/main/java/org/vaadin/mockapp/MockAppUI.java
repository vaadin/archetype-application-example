package org.vaadin.mockapp;

import javax.servlet.annotation.WebServlet;

import org.vaadin.mockapp.samples.MainScreen;
import org.vaadin.mockapp.samples.authentication.CurrentUser;
import org.vaadin.mockapp.samples.authentication.LoginScreen;
import org.vaadin.mockapp.samples.authentication.LoginScreen.LoginListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

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
		if (CurrentUser.get().isEmpty()) {
			setContent(new LoginScreen(new LoginListener() {
				@Override
				public void loginSuccessful(String username) {
					CurrentUser.set(username);
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
