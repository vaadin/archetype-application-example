package org.vaadin.mockapp.samples.about;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

public class AboutView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "About";
	
	public AboutView() {
		CssLayout aboutContent = new CssLayout();
		aboutContent.setStyleName("about-content");

		// this could also be a single long HTML Label
		aboutContent.addComponent(new Label("<h1>About</h1>", ContentMode.HTML));
		aboutContent.addComponent(new Label(FontAwesome.INFO_CIRCLE.getHtml() + "Text area width should not be over 500px", ContentMode.HTML));
		aboutContent.addComponent(new Label("Lorem ipsum ..."));
		aboutContent.addComponent(new Link("More information", new ExternalResource("http://vaadin.com/")));
		
		setSizeFull();
		setStyleName("about-view");
		addComponent(aboutContent);
		setComponentAlignment(aboutContent, Alignment.MIDDLE_CENTER);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}

}
