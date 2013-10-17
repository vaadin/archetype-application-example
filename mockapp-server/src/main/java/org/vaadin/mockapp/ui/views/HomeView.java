package org.vaadin.mockapp.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.ui.theme.MockAppTheme;

/**
 * @author petter@vaadin.com
 */
@ViewDefinition(name = HomeView.VIEW_NAME,
        caption = "Home",
        iconThemeResource = "icons/HomeView.png",
        allowedRoles = {MockAppRoles.ROLE_ADMIN, MockAppRoles.ROLE_DOCTOR, MockAppRoles.ROLE_RECEPTIONIST})
public class HomeView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "";

    public HomeView() {
        setMargin(true);

        Label lbl = new Label("Home View");
        lbl.addStyleName(MockAppTheme.LABEL_H1);
        addComponent(lbl);

        addComponent(new Label("This view is not implemented in this project stub :-)"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
