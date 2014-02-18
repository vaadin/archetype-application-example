package org.vaadin.mockapp.samples.table;

import org.vaadin.mockapp.samples.backend.DataService;
import org.vaadin.mockapp.samples.data.Product;
import org.vaadin.mockapp.samples.filtering.FilterField;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;

public class SampleCrudView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "Editor";
	ProductTable table;
	ProductForm form;
	private FilterField filter = new FilterField();
	private SampleCrudLogic viewLogic;

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

		form = new ProductForm(viewLogic);
		form.setWidth("100%");
		form.setEnabled(false);
		form.setCategories(DataService.get().getAllCategories());
		addComponent(form);

		viewLogic = new SampleCrudLogic(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		viewLogic.refreshTable();
	}

	public void showError(String msg) {
		Notification.show(msg, Type.ERROR_MESSAGE);
	}

}
