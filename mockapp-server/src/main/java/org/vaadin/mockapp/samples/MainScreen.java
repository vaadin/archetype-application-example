package org.vaadin.mockapp.samples;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.mockapp.MockAppUI;
import org.vaadin.mockapp.samples.charts.SampleChartView;
import org.vaadin.mockapp.samples.crud.SampleCrudView;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Content of the UI when the user is logged in.
 * 
 * 
 */
public class MainScreen extends VerticalLayout {

	private Map<Button, String> viewButtons = new HashMap<Button, String>();
	private ClickListener viewButtonClickListener = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			getUI().getNavigator().navigateTo(
					viewButtons.get(event.getButton()));
			for (Button button : viewButtons.keySet()) {
				button.removeStyleName("selected-view");
			}
			event.getButton().addStyleName("selected-view");
		}
	};

	public MainScreen(MockAppUI ui) {

		VerticalLayout viewContainer = new VerticalLayout();
		viewContainer.setSizeFull();

		final Navigator navigator = new Navigator(ui, viewContainer);
		navigator.setErrorView(ErrorView.class);
		HorizontalLayout header = createHeader(navigator);
		addComponent(header);
		addComponent(viewContainer);
		setExpandRatio(viewContainer, 1);
		setSizeFull();
	}

	private HorizontalLayout createHeader(Navigator navigator) {
		final HorizontalLayout header = new HorizontalLayout();
		header.setWidth("100%");
		header.setHeight("50px");
		header.setMargin(true);
		header.setSpacing(true);
		header.addStyleName("black");

		final Label headerLabel = new Label(FontAwesome.TABLE.getHtml()
				+ " My CRUD", ContentMode.HTML);
		headerLabel.addStyleName(ValoTheme.LABEL_H3);
		headerLabel.setWidth(null);

		final HorizontalLayout viewLayout = createViewsAndNavigation(navigator);

		Button logoutButton = new Button("Logout", new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				VaadinSession.getCurrent().getSession().invalidate();
				Page.getCurrent().reload();
			}
		});
		logoutButton.setIcon(FontAwesome.SIGN_OUT);
		logoutButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);

		header.addComponent(headerLabel);
		header.addComponent(viewLayout);
		header.addComponent(logoutButton);
		header.setComponentAlignment(headerLabel, Alignment.MIDDLE_CENTER);
		header.setComponentAlignment(viewLayout, Alignment.MIDDLE_CENTER);
		header.setComponentAlignment(logoutButton, Alignment.MIDDLE_CENTER);
		header.setExpandRatio(viewLayout, 1);

		return header;
	}

	private HorizontalLayout createViewsAndNavigation(final Navigator navigator) {
		HorizontalLayout viewLayout = new HorizontalLayout();
		viewLayout.setHeight("100%");

		// Crud view. Use an instance to avoid reinitializing during edit
		// operations
		navigator.addView(SampleCrudView.VIEW_NAME, new SampleCrudView());
		Button crudButton = createViewButton(SampleCrudView.VIEW_NAME, FontAwesome.EDIT);

		// Charts view, reinitialize on every visit
		navigator.addView(SampleChartView.VIEW_NAME, SampleChartView.class);
		Button chartButton = createViewButton(SampleChartView.VIEW_NAME, FontAwesome.INFO_CIRCLE);

		viewLayout.addComponent(crudButton);
		viewLayout.addComponent(chartButton);
		return viewLayout;
	}

	private Button createViewButton(String view, Resource icon) {
		Button button = new Button(view, viewButtonClickListener);
		button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		button.setIcon(icon);
		button.setHeight("100%");
		viewButtons.put(button, view);
		return button;
	}
}
