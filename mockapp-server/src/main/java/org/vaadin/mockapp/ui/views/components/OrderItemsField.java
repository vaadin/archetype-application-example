package org.vaadin.mockapp.ui.views.components;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Table;
import org.vaadin.mockapp.backend.domain.OrderItem;

import java.util.List;

/**
 * @author petter@vaadin.com
 */
public class OrderItemsField extends CustomField<List> {

    public interface SelectionCallback {
        void onSelectionChanged(OrderItemsField sender, boolean hasSelection);
    }

    private BeanItemContainer<OrderItem> itemsContainer;
    private Table itemsTable;
    private SelectionCallback selectionCallback;

    public OrderItemsField() {
        itemsContainer = new BeanItemContainer<OrderItem>(OrderItem.class);
        itemsTable = new Table();
        itemsTable.setSizeFull();
        itemsTable.setContainerDataSource(itemsContainer);
        itemsTable.setSelectable(true);
        itemsTable.setImmediate(true);
        itemsTable.setVisibleColumns("description", "qty", "unit", "unitPrice", "taxPercentage", "totalTax", "totalWithoutTax");
        itemsTable.setColumnHeaders("Description", "Qty", "Unit", "Unit Price", "Tax-%", "Total Tax", "Total (excluding tax)");
        itemsTable.setColumnAlignment("qty", Table.Align.RIGHT);
        itemsTable.setColumnAlignment("unitPrice", Table.Align.RIGHT);
        itemsTable.setColumnAlignment("taxPercentage", Table.Align.RIGHT);
        itemsTable.setColumnAlignment("totalTax", Table.Align.RIGHT);
        itemsTable.setColumnAlignment("totalWithoutTax", Table.Align.RIGHT);
        itemsTable.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (selectionCallback != null) {
                    selectionCallback.onSelectionChanged(OrderItemsField.this, event.getProperty().getValue() != null);
                }
            }
        });
    }

    public void filter(String filterString) {

    }

    public SelectionCallback getSelectionCallback() {
        return selectionCallback;
    }

    public void setSelectionCallback(SelectionCallback selectionCallback) {
        this.selectionCallback = selectionCallback;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void setInternalValue(List newValue) {
        super.setInternalValue(newValue);
        itemsContainer.addAll(newValue);
    }

    @Override
    protected Component initContent() {
        return itemsTable;
    }

    @Override
    public Class<? extends List> getType() {
        return List.class;
    }

    public void addItem() {

    }

    public void editSelected() {

    }

    public void deleteSelected() {

    }

    public boolean hasSelection() {
        return itemsTable.getValue() != null;
    }
}
