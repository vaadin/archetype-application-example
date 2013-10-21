package org.vaadin.mockapp.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.mockapp.ui.views.components.H1;

/**
 * @author petter@vaadin.com
 */
public class ErrorView extends VerticalLayout implements View {

    private Label explanation;

    public ErrorView() {
        init();
    }

    private void init() {
        setMargin(true);
        setSpacing(true);

        addComponent(new H1("The view could not be found"));
        addComponent(explanation = new Label());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        explanation.setValue(String.format("You tried to navigate to a view ('%s') that does not exist.", event.getViewName()));
    }
}
