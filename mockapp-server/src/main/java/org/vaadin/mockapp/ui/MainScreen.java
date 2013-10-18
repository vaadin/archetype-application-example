package org.vaadin.mockapp.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalSplitPanel;
import org.jetbrains.annotations.NotNull;
import org.vaadin.mockapp.ui.theme.MockAppTheme;

/**
 * @author petter@vaadin.com
 */
public class MainScreen extends CustomComponent implements ViewDisplay {

    private HorizontalSplitPanel root;

    public MainScreen() {
        init();
    }

    private void init() {
        addStyleName("main-screen");
        setSizeFull();

        root = new HorizontalSplitPanel();
        root.setSizeFull();
        root.setSplitPosition(150, Unit.PIXELS);
        root.addStyleName(MockAppTheme.SPLITPANEL_SMALL);
        setCompositionRoot(root);

        SideBar sideBar = new SideBar();
        root.setFirstComponent(sideBar);
    }

    @Override
    public void showView(@NotNull View view) {
        root.setSecondComponent((Component) view);
    }

}
