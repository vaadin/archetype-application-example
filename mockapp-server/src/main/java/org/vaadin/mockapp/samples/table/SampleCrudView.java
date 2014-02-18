package org.vaadin.mockapp.samples.table;

import org.vaadin.mockapp.samples.backend.DataService;
import org.vaadin.mockapp.samples.data.Product;
import org.vaadin.mockapp.samples.filtering.FilterField;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class SampleCrudView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "Editor";
	ProductTable table;
	ProductForm form;
	private FilterField filter = new FilterField();

	public SampleCrudView() {
		setSpacing(true);
		setMargin(true);
		setSizeFull();

		filter.addFilterListener(new FilterField.FilterListener() {
			@Override
			public void filter(FilterField.FilterEvent event) {
				BeanItemContainer<Product> container = table
						.getContainerDataSource();
				container.removeAllContainerFilters();
				container.addContainerFilter(event.getContainerFilter());
			}
		});
		addComponent(filter);
		setComponentAlignment(filter, Alignment.TOP_RIGHT);

		table = new ProductTable();
		addComponent(table);
		setExpandRatio(table, 1);

		form = new ProductForm();
		form.setWidth("100%");
		form.setEnabled(false);
		form.setCategories(DataService.get().getAllCategories());
		addComponent(form);

		new SampleCrudLogic(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		refreshTable();
	}

	private void refreshTable() {
		Product oldSelection = table.getValue();
		BeanItemContainer<Product> container = table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(DataService.get().getAllProducts());
		table.setValue(oldSelection);
	}

}
