package org.vaadin.mockapp.samples.client;

import org.vaadin.mockapp.samples.MyNameComponent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;

// Connector binds client-side widget class to server-side component class
// Connector lives in the client and the @Connect annotation specifies the
// corresponding server-side component
@Connect(MyNameComponent.class)
public class MyNameComponentConnector extends AbstractComponentConnector {

	@Override
	protected Widget createWidget() {
		return GWT.create(MyNameComponentWidget.class);
	}

	@Override
	public MyNameComponentWidget getWidget() {
		return (MyNameComponentWidget) super.getWidget();
	}

	@Override
	public MyNameComponentState getState() {
		return (MyNameComponentState) super.getState();
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);
	}

}
