package org.vaadin.mockapp.samples;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.mockapp.theme.MockAppTheme;

/**
 * View shown when trying to navigate to a view that does not exist using {@link com.vaadin.navigator.Navigator}.
 *
 * @author petter@vaadin.com
 */
public class ErrorView extends VerticalLayout implements View {

    private Label explanation;

    public ErrorView() {
        setMargin(true);
        setSpacing(true);

        Label header = new Label("The view could not be found");
        header.addStyleName(MockAppTheme.LABEL_H1);
        addComponent(header);
        addComponent(explanation = new Label());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        explanation.setValue(String.format("You tried to navigate to a view ('%s') that does not exist.", event.getViewName()));
    }
}
