package org.vaadin.mockapp.samples;

import org.vaadin.mockapp.samples.authentication.CurrentUser;
import org.vaadin.mockapp.samples.charts.SampleChartView;
import org.vaadin.mockapp.samples.table.SampleTableView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * Content of the UI when the user is logged in.
 * 
 * @author petter@vaadin.com
 */
public class MainScreen extends VerticalLayout {

	private static final Class<? extends View>[] viewClasses = new Class[] {
			SampleTableView.class, SampleChartView.class };

	public MainScreen(UI ui) {
		setSizeFull();
		createHeader();
		createMenuBar();
		final Panel viewContainer = new Panel();
		viewContainer.setSizeFull();
		viewContainer.addStyleName(Reindeer.PANEL_LIGHT);
		addComponent(viewContainer);
		setExpandRatio(viewContainer, 1);

		final Navigator navigator = new Navigator(ui, viewContainer);
		navigator.setErrorView(ErrorView.class);

		for (Class<? extends View> viewClass : viewClasses) {
			navigator.addView(getViewName(viewClass), viewClass);
		}

		navigator.navigateTo(navigator.getState());
	}

	private void createHeader() {
		final HorizontalLayout header = new HorizontalLayout();
		header.setWidth("100%");
		header.setMargin(true);
		header.setSpacing(true);
		header.addStyleName(Reindeer.LAYOUT_BLACK);

		final Label currentUser = new Label("Hello, " + CurrentUser.get());
		currentUser.addStyleName(Reindeer.LABEL_H2);
		header.addComponent(currentUser);

		addComponent(header);
	}

	private void createMenuBar() {
		final MenuBar menuBar = new MenuBar();
		menuBar.setWidth("100%");
		addComponent(menuBar);

		for (Class<? extends View> viewClass : viewClasses) {
			final String viewName = getViewName(viewClass);
			menuBar.addItem(viewName, new Command() {
				@Override
				public void menuSelected(MenuItem selectedItem) {
					getUI().getNavigator().navigateTo(viewName);
				}
			});
		}

		menuBar.addItem("Log out", new MenuBar.Command() {
			@Override
			public void menuSelected(MenuBar.MenuItem selectedItem) {
				VaadinSession.getCurrent().getSession().invalidate();
				Page.getCurrent().reload();
			}
		});

	}

	private String getViewName(Class<? extends View> viewClass) {
		try {
			return (String) viewClass.getField("VIEW_NAME").get(null);
		} catch (Exception e) {
			return "VIEW_NAME field not found in " + viewClass.getName();
		}
	}

}
