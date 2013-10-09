package com.vaadin.mockapp.ui.components;

import com.vaadin.mockapp.ui.theme.MockAppTheme;
import com.vaadin.mockapp.ui.views.ErrorView;
import com.vaadin.mockapp.ui.views.SampleFormView;
import com.vaadin.mockapp.ui.views.SampleListView;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.*;

import java.io.Serializable;

/**
 * @author petter@vaadin.com
 */
public class MainScreen extends CustomComponent {

    private final Callback callback;
    private Panel viewContainer;

    public MainScreen(Callback callback) {
        this.callback = callback;
        init();
    }

    private void init() {
        addStyleName("main-screen");
        setSizeFull();

        HorizontalSplitPanel root = new HorizontalSplitPanel();
        root.setSizeFull();
        setCompositionRoot(root);

        VerticalLayout sideBar = new VerticalLayout();
        sideBar.addStyleName("side-bar");
        root.setFirstComponent(sideBar);

        viewContainer = new Panel();
        viewContainer.addStyleName(MockAppTheme.PANEL_LIGHT);
        viewContainer.setSizeFull();
        root.setSecondComponent(viewContainer);

        Navigator navigator = new Navigator(UI.getCurrent(), viewContainer);
        initNavigator(navigator);
    }

    private void initNavigator(Navigator navigator) {
        navigator.setErrorView(ErrorView.class);
        navigator.addView("list", SampleListView.class);
        navigator.addView("form", SampleFormView.class);
    }

    public interface Callback extends Serializable {
        void logout();
    }
}
