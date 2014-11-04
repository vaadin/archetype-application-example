package org.vaadin.mockapp;

import javax.servlet.annotation.WebServlet;

import org.vaadin.mockapp.samples.MainScreen;
import org.vaadin.mockapp.samples.authentication.AccessControl;
import org.vaadin.mockapp.samples.authentication.BasicAccessControl;
import org.vaadin.mockapp.samples.authentication.LoginScreen;
import org.vaadin.mockapp.samples.authentication.LoginScreen.LoginListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * 
 */
@Theme("mockapp")
@Widgetset("org.vaadin.mockapp.MockAppWidgetset")
public class MockAppUI extends UI {

	private AccessControl accessControl = new BasicAccessControl();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		setLocale(vaadinRequest.getLocale());
		getPage().setTitle("MockApp");
		if (!accessControl.isUserSignedIn()) {
			setContent(new LoginScreen(accessControl, new LoginListener() {
				@Override
				public void loginSuccessful() {
					showMainView();
				}
			}));
		} else {
			showMainView();
		}
	}

	protected void showMainView() {
		setContent(new MainScreen(MockAppUI.this));
		// Ensure we navigate to the intial fragment which login view ignores
		getNavigator().navigateTo(getNavigator().getState());

	}

	public static MockAppUI get() {
		return (MockAppUI) UI.getCurrent();
	}

	public AccessControl getAccessControl() {
		return accessControl;
	}

	@WebServlet(urlPatterns = "/*", name = "MockAppUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MockAppUI.class, productionMode = false)
	public static class MockAppUIServlet extends VaadinServlet {
	}
}
