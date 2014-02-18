package org.vaadin.mockapp.samples.components;

import java.util.EnumSet;

import com.vaadin.ui.NativeSelect;

public class EnumSelect<E extends Enum<E>> extends NativeSelect {

    public EnumSelect(String caption, Class<E> enumClass) {
        super(caption);

        EnumSet<E> set = EnumSet.allOf(enumClass);
        for (Enum<E> e : set) {
            addItem(e);
        }
    }
}
