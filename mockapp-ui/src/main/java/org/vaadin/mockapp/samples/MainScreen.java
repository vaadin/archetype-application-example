package org.vaadin.mockapp.samples;

import java.util.Iterator;
import java.util.Map.Entry;

import org.vaadin.mockapp.MockAppUI;
import org.vaadin.mockapp.samples.charts.SampleChartView;
import org.vaadin.mockapp.samples.crud.SampleCrudView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * Content of the UI when the user is logged in.
 * 
 * 
 */
public class MainScreen extends HorizontalLayout {
	private Menu menu;

	public MainScreen(MockAppUI ui) {

		CssLayout viewContainer = new CssLayout();
		viewContainer.setSizeFull();
		viewContainer.addStyleName("valo-content");

		final Navigator navigator = new Navigator(ui, viewContainer);
		navigator.setErrorView(ErrorView.class);
		menu = new Menu(navigator);
		menu.addView(new SampleCrudView(), SampleCrudView.VIEW_NAME, SampleCrudView.VIEW_NAME, FontAwesome.EDIT);
		menu.addView(SampleChartView.class, SampleChartView.VIEW_NAME, SampleChartView.VIEW_NAME, FontAwesome.INFO_CIRCLE);
		
		navigator.addViewChangeListener(viewChangeListener);
		
		addComponent(menu);
		addComponent(viewContainer);
		setExpandRatio(viewContainer, 1);
		setSizeFull();
	}
	
	ViewChangeListener viewChangeListener = new ViewChangeListener(){

		@Override
		public boolean beforeViewChange(ViewChangeEvent event) {
			 return true;
		}

		@Override
		public void afterViewChange(ViewChangeEvent event) {
			menu.setActiveView(event.getViewName());
		}
		
	};
}
