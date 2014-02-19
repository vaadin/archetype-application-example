package org.vaadin.mockapp.samples;

import org.vaadin.mockapp.samples.charts.SampleChartView;
import org.vaadin.mockapp.samples.crud.SampleCrudView;

import com.vaadin.navigator.Navigator;
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

	private MenuBar menuBar;

	public MainScreen(UI ui) {
		setSizeFull();
		createHeader();
		final Panel viewContainer = new Panel();
		viewContainer.setSizeFull();
		viewContainer.addStyleName(Reindeer.PANEL_LIGHT);

		final Navigator navigator = new Navigator(ui, viewContainer);
		navigator.setErrorView(ErrorView.class);

		createViewsAndNavigation(navigator);

		addComponent(menuBar);
		addComponent(viewContainer);
		setExpandRatio(viewContainer, 1);
	}

	private void createHeader() {
		final HorizontalLayout header = new HorizontalLayout();
		header.setWidth("100%");
		header.setMargin(true);
		header.setSpacing(true);
		header.addStyleName(Reindeer.LAYOUT_BLACK);

		final Label currentUser = new Label("Hello");
		currentUser.addStyleName(Reindeer.LABEL_H2);
		header.addComponent(currentUser);

		addComponent(header);
	}

	public class NavigateCommand implements Command {
		private String fragment;

		public NavigateCommand(String fragment) {
			this.fragment = fragment;
		}

		@Override
		public void menuSelected(MenuItem selectedItem) {
			getUI().getNavigator().navigateTo(fragment);
		}
	}

	private void createViewsAndNavigation(Navigator navigator) {
		menuBar = new MenuBar();
		menuBar.setWidth("100%");
		addComponent(menuBar);

		// Crud view. Use an instance to avoid reinitializing during edit
		// operations
		navigator.addView(SampleCrudView.VIEW_NAME, new SampleCrudView());
		menuBar.addItem(SampleCrudView.VIEW_NAME, new NavigateCommand(
				SampleCrudView.VIEW_NAME));

		// Charts view, reinitialize on every visit
		navigator.addView(SampleChartView.VIEW_NAME, SampleChartView.class);
		menuBar.addItem(SampleChartView.VIEW_NAME, new NavigateCommand(
				SampleChartView.VIEW_NAME));

		menuBar.addItem("Log out", new MenuBar.Command() {
			@Override
			public void menuSelected(MenuBar.MenuItem selectedItem) {
				VaadinSession.getCurrent().getSession().invalidate();
				Page.getCurrent().reload();
			}
		});

	}

}
