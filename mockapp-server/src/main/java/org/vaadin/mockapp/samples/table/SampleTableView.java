package org.vaadin.mockapp.samples.table;

import org.vaadin.mockapp.samples.ResetButtonForTextField;
import org.vaadin.mockapp.samples.data.MockData;
import org.vaadin.mockapp.samples.data.Product;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.mockapp.samples.filtering.FilterField;

public class SampleTableView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "sampleTableView";
	private ProductTable table;
	private FilterField filter = new FilterField();
	private Button goToFormView;

	public SampleTableView() {
		setSpacing(true);
		setMargin(true);
		setSizeFull();

        filter.addFilterListener(new FilterField.FilterListener() {
            @Override
            public void filter(FilterField.FilterEvent event) {
                BeanItemContainer<Product> container = table.getContainerDataSource();
                container.removeAllContainerFilters();
                container.addContainerFilter(event.getContainerFilter());
            }
        });
		addComponent(filter);
		setComponentAlignment(filter, Alignment.TOP_RIGHT);

		table = new ProductTable();
		table.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				updateButtonState();
			}
		});
		addComponent(table);
		setExpandRatio(table, 1);

		goToFormView = new Button("Open Form View", new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				goToFormView();
			}
		});
		addComponent(goToFormView);
		setComponentAlignment(goToFormView, Alignment.BOTTOM_RIGHT);

		updateButtonState();
	}

	private void goToFormView() {
		Product selection = table.getValue();
//		if (selection != null) {
//			getUI().getNavigator().navigateTo(
//					SampleFormView.VIEW_NAME + "/" + selection.getId());
//		}
	}

	private void updateButtonState() {
		Product selection = table.getValue();
		goToFormView.setEnabled(selection != null);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		refreshTable();
	}

	private void refreshTable() {
		Product oldSelection = table.getValue();
		BeanItemContainer<Product> container = table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(MockData.getInstance().getAllProducts());
		table.setValue(oldSelection);

	}
}
