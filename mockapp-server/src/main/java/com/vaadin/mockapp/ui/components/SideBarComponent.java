package com.vaadin.mockapp.ui.components;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;

/**
 * @author petter@vaadin.com
 */
public class SideBarComponent extends CustomComponent {

    private Label title;
    private Label currentUser;
    private NativeButton logout;
    private Accordion navigationBar;

    public SideBarComponent() {
    }

}
