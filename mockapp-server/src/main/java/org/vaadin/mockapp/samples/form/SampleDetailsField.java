package org.vaadin.mockapp.samples.form;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Table;
import org.vaadin.mockapp.samples.data.SampleDetail;

import java.util.HashSet;
import java.util.Set;

/**
 * @author petter@vaadin.com
 */
public class SampleDetailsField extends CustomField<Set> {

    private Table table;
    private BeanItemContainer<SampleDetail> container = new BeanItemContainer<SampleDetail>(SampleDetail.class);

    @Override
    protected Component initContent() {
        table = new Table();
        table.setSizeFull();
        table.setContainerDataSource(container);
        table.setSortContainerPropertyId("stringProperty");
        table.setEditable(true);
        return table;
    }

    @Override
    public Class<? extends Set> getType() {
        return Set.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Set getInternalValue() {
        return clone(super.getInternalValue());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void setInternalValue(Set newValue) {
        final Set<SampleDetail> clone = clone(newValue);
        super.setInternalValue(clone);
        container.removeAllItems();
        container.addAll(clone);
        if (table != null) {
            table.sort();
        }
    }

    private Set<SampleDetail> clone(Set<SampleDetail> original) {
        try {
            Set<SampleDetail> set = new HashSet<SampleDetail>();
            if (original != null) {
                for (SampleDetail originalDetail : original) {
                    set.add(originalDetail.clone());
                }
            }
            return set;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
