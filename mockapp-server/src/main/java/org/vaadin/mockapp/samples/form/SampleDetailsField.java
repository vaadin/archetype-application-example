package org.vaadin.mockapp.samples.form;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

import java.util.Set;

/**
 * @author petter@vaadin.com
 */
public class SampleDetailsField extends CustomField<Set> {

    @Override
    protected Component initContent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Class<? extends Set> getType() {
        return Set.class;
    }
}
