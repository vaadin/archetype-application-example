package org.vaadin.mockapp.samples;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.vaadin.mockapp.authentication.AuthenticationHolder;
import org.vaadin.mockapp.samples.charts.SampleChartView;
import org.vaadin.mockapp.samples.form.SampleFormView;
import org.vaadin.mockapp.samples.table.SampleTableView;

/**
 * Content of the UI when the user is logged in.
 *
 * @author petter@vaadin.com
 */
public class MainScreen extends VerticalLayout {

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

        navigator.addView(SampleTableView.VIEW_NAME, new SampleTableView());
        navigator.addView(SampleFormView.VIEW_NAME, SampleFormView.class);
        navigator.addView(SampleChartView.VIEW_NAME, SampleChartView.class);

        navigator.navigateTo(navigator.getState());
    }

    private void createHeader() {
        final HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setMargin(true);
        header.setSpacing(true);
        header.addStyleName(Reindeer.LAYOUT_BLACK);

        final Label currentUser = new Label("Hello, " + AuthenticationHolder.getAuthentication().getName());
        currentUser.addStyleName(Reindeer.LABEL_H2);
        header.addComponent(currentUser);

        addComponent(header);
    }

    private void createMenuBar() {
        final MenuBar menuBar = new MenuBar();
        menuBar.setWidth("100%");
        addComponent(menuBar);

        final MenuBar.MenuItem samples = menuBar.addItem("Samples", null);
        samples.addItem("Table", new ViewNavigationCommand(SampleTableView.VIEW_NAME));
        samples.addItem("Form", new ViewNavigationCommand(SampleFormView.VIEW_NAME));
        samples.addItem("Charts", new ViewNavigationCommand(SampleChartView.VIEW_NAME));

        menuBar.addItem("My Session", null).addItem("Log out", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                VaadinSession.getCurrent().getSession().invalidate();
                Page.getCurrent().reload();
            }
        });

    }

    private class ViewNavigationCommand implements MenuBar.Command {

        private final String viewName;

        private ViewNavigationCommand(String viewName) {
            this.viewName = viewName;
        }

        @Override
        public void menuSelected(MenuBar.MenuItem selectedItem) {
            getUI().getNavigator().navigateTo(viewName);
        }
    }

}
