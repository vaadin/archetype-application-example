package org.vaadin.mockapp.samples.crud;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class ProductFormDesign extends CssLayout {

    TextField productName;
    TextField price;
    NumberField stockCount;
    ComboBox availability;
    CategoryField category;
    Button save;
    Button cancel;
    Button delete;

    public ProductFormDesign() {
        Design.read(this);
    }
}
