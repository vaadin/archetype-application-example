package org.vaadin.mockapp.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import org.vaadin.mockapp.backend.authentication.Roles;

/**
 * @author petter@vaadin.com
 */
@ViewDefinition(name = "",
        caption = "Home",
        iconThemeResource = "icons/HomeView.png",
        allowedRoles = {Roles.ROLE_ADMIN, Roles.ROLE_USER})
public class HomeView extends CustomComponent implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // TODO Implement HomeView
    }
}
