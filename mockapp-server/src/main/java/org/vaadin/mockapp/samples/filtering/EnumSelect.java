package org.vaadin.mockapp.samples.filtering;

import com.vaadin.ui.ComboBox;

import java.util.EnumSet;

public class EnumSelect<E extends Enum<E>> extends ComboBox {

    public EnumSelect(String caption, Class<E> enumClass) {
        super(caption);

        EnumSet<E> set = EnumSet.allOf(enumClass);
        for (Enum<E> e : set) {
            addItem(e);
        }
    }
}
