package org.vaadin.mockapp.samples;

import org.vaadin.mockapp.client.samples.MyNameComponentState;

// This is the server-side UI component that provides public API 
// for MyComponent
public class MyNameComponent extends com.vaadin.ui.AbstractComponent {

	public MyNameComponent() {
	}

	// We must override getState() to cast the state to MyComponentState
	@Override
	public MyNameComponentState getState() {
		return (MyNameComponentState) super.getState();
	}
}
