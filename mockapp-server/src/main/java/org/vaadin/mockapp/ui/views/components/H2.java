package org.vaadin.mockapp.ui.views.components;

import com.vaadin.ui.Label;
import org.vaadin.mockapp.ui.theme.MockAppTheme;

/**
 * @author petter@vaadin.com
 */
public class H2 extends Label {

    public H2() {
        addStyleName(MockAppTheme.LABEL_H2);
        setSizeUndefined();
    }

    public H2(String content) {
        super(content);
        addStyleName(MockAppTheme.LABEL_H2);
        setSizeUndefined();
    }
}
