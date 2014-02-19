package org.vaadin.mockapp.samples.crud;

import org.vaadin.mockapp.samples.backend.DataService;
import org.vaadin.mockapp.samples.filtering.FilterField;

import com.vaadin.data.Container;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

public class SampleCrudView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "Editor";
	ProductTable table;
	ProductForm form;
	private FilterField filter = new FilterField();
	private SampleCrudLogic viewLogic = new SampleCrudLogic(this);
	Button newProduct = new Button("New product");

	public SampleCrudView() {
		setSpacing(true);
		setMargin(true);
		setSizeFull();

		HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.setWidth("100%");

		filter.addFilterListener(new FilterField.FilterListener() {
			@Override
			public void filter(FilterField.FilterEvent event) {
				Container.Filterable container = table.getContainerDataSource();
				container.removeAllContainerFilters();
				container.addContainerFilter(event.getContainerFilter());
			}
		});
		addComponent(topLayout);
		topLayout.addComponent(newProduct);
		newProduct.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				viewLogic.newProduct();
			}
		});
		topLayout.addComponent(filter);
		topLayout.setComponentAlignment(filter, Alignment.TOP_RIGHT);
		topLayout.setExpandRatio(filter, 1);

		table = new ProductTable();
		addComponent(table);
		setExpandRatio(table, 1);

		form = new ProductForm(viewLogic);
		form.setWidth("100%");
		form.setEnabled(false);
		form.setCategories(DataService.get().getAllCategories());
		addComponent(form);

		viewLogic.init();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		viewLogic.enter(event.getParameters());
	}

	public void showError(String msg) {
		Notification.show(msg, Type.ERROR_MESSAGE);
	}

	public void showSaveNotification(String msg) {
		Notification.show(msg, Type.TRAY_NOTIFICATION);
	}

}
