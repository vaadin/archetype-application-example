package org.vaadin.mockapp.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import org.vaadin.mockapp.backend.authentication.Roles;

/**
 * @author petter@vaadin.com
 */
@ViewDefinition(name = "sampleList",
        caption = "Sample List View",
        iconThemeResource = "icons/SampleListView.png",
        order = 1,
        allowedRoles = {Roles.ROLE_ADMIN, Roles.ROLE_USER})
public class SampleListView extends CustomComponent implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // TODO Implement SampleListView
    }
}
