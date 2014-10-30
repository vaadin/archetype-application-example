package org.vaadin.mockapp.samples.crud;

import java.util.Collection;

import org.vaadin.mockapp.samples.ResetButtonForTextField;
import org.vaadin.mockapp.samples.backend.DataService;
import org.vaadin.mockapp.samples.data.Product;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SampleCrudView extends HorizontalLayout implements View {

	public static final String VIEW_NAME = "Editor";
	private ProductTable table;
	private ProductForm form;

	private SampleCrudLogic viewLogic = new SampleCrudLogic(this);
	private Button newProduct;

	public SampleCrudView() {
		setSpacing(true);
		setSizeFull();

		HorizontalLayout topLayout = createTopBar();

		table = new ProductTable();
		table.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				viewLogic.rowSelected(table.getValue());
			}
		});

		form = new ProductForm(viewLogic);
		form.setWidth("300px");
		form.setHeight("100%");
		form.setCategories(DataService.get().getAllCategories());

		VerticalLayout barAndTableLayout = new VerticalLayout();
		barAndTableLayout.addComponent(topLayout);
		barAndTableLayout.addComponent(table);
		barAndTableLayout.setMargin(true);
		barAndTableLayout.setSpacing(true);
		barAndTableLayout.setSizeFull();
		barAndTableLayout.setExpandRatio(table, 1);

		addComponent(barAndTableLayout);
		addComponent(form);
		setExpandRatio(barAndTableLayout, 1);

		viewLogic.init();
	}

	public HorizontalLayout createTopBar() {
		TextField filter = new TextField();
		filter.setInputPrompt("Filter");
		ResetButtonForTextField.extend(filter);
		filter.setImmediate(true);
		filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
			@Override
			public void textChange(FieldEvents.TextChangeEvent event) {
				table.setFilter(event.getText());
			}
		});

		newProduct = new Button("New product");
		newProduct.addStyleName(ValoTheme.BUTTON_PRIMARY);
		newProduct.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				viewLogic.newProduct();
			}
		});

		HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.setSpacing(true);
		topLayout.setWidth("100%");
		topLayout.addComponent(filter);
		topLayout.addComponent(newProduct);
		topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
		topLayout.setExpandRatio(filter, 1);
		return topLayout;
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

	public void setNewProductEnabled(boolean enabled) {
		newProduct.setEnabled(enabled);
	}

	public void selectRow(Product row) {
		table.setValue(row);
	}
	

	public void editProduct(Product product) {
		form.setVisible(product != null);
		form.editProduct(product);
	}

	public Product getSelectedRow() {
		return table.getValue();
	}

	public void showProducts(Collection<Product> products) {
		BeanItemContainer<Product> container = table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(products);
	}

}
