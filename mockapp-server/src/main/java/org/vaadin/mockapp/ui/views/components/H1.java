package org.vaadin.mockapp.ui.views.components;

import com.vaadin.ui.Label;
import org.vaadin.mockapp.ui.theme.MockAppTheme;

/**
 * @author petter@vaadin.com
 */
public class H1 extends Label {

    public H1() {
        addStyleName(MockAppTheme.LABEL_H1);
        setSizeUndefined();
    }

    public H1(String content) {
        super(content);
        addStyleName(MockAppTheme.LABEL_H1);
        setSizeUndefined();
    }
}
