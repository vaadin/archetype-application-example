package org.vaadin.mockapp;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;

/**
 * Vaadin session initialization listener that configure the viewport meta tags
 * appropriately for mobile devices. Instead of using device based scaling
 * (default), this supports the use of responsive layouts.
 * 
 * If using Vaadin TouchKit, this is done automatically and it is sufficient to
 * have an empty servlet class extending TouchKitServlet.
 */
public class ViewPortSessionInitListener implements
		SessionInitListener {
	@Override
	public void sessionInit(SessionInitEvent event) throws ServiceException {
		event.getSession().addBootstrapListener(new BootstrapListener() {

			@Override
			public void modifyBootstrapFragment(
					BootstrapFragmentResponse response) {
			}

			@Override
			public void modifyBootstrapPage(BootstrapPageResponse response) {
				// <meta name="viewport"
				// content="user-scalable=no,initial-scale=1.0">
				Document d = response.getDocument();
				Element el = d.createElement("meta");
				el.attr("name", "viewport");
				el.attr("content", getViewPortConfiguration(response));
				d.getElementsByTag("head").get(0).appendChild(el);
			}

		});

	}

	protected String getViewPortConfiguration(BootstrapPageResponse response) {
		return "user-scalable=no,initial-scale=1.0";
	}
}