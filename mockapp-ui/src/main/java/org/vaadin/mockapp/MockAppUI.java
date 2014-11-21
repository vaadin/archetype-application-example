package org.vaadin.mockapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.vaadin.mockapp.samples.MainScreen;
import org.vaadin.mockapp.samples.authentication.AccessControl;
import org.vaadin.mockapp.samples.authentication.BasicAccessControl;
import org.vaadin.mockapp.samples.authentication.LoginScreen;
import org.vaadin.mockapp.samples.authentication.LoginScreen.LoginListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.Responsive;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
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
		Responsive.makeResponsive(this);
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
		@Override
		protected void servletInitialized() throws ServletException {
			super.servletInitialized();
			/*
			 * Configure the viewport meta tags appropriately on mobile devices.
			 * Instead of device based scaling (default), using responsive layouts.
			 * 
			 * If using Vaadin TouchKit, this is done automatically and it is
			 * sufficient to have an empty servlet class extending TouchKitServlet.
			 */
			getService().addSessionInitListener(new ViewPortSessionInitListener());
		}
	}
}
