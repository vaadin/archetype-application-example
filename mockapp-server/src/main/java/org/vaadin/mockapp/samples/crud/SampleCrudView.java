package org.vaadin.mockapp.samples.crud;

import org.vaadin.mockapp.samples.ResetButtonForTextField;
import org.vaadin.mockapp.samples.backend.DataService;

import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class SampleCrudView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "Editor";
	ProductTable table;
	ProductForm form;
	private TextField filter = new TextField();
	private SampleCrudLogic viewLogic = new SampleCrudLogic(this);
	Button newProduct = new Button("New product");

	CheckBox formAsPopup = new CheckBox("Form as popup");
	Window formWindow = new Window("Edit form");

	public SampleCrudView() {
		setSpacing(true);
		setMargin(true);
		setSizeFull();

		HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.setSpacing(true);
		topLayout.setWidth("100%");
		addComponent(topLayout);
		
		filter.setInputPrompt("Filter the table");
		ResetButtonForTextField.extend(filter);
		filter.setImmediate(true);
		filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
			@Override
			public void textChange(FieldEvents.TextChangeEvent event) {
				table.setFilter(event.getText());
			}
		});
		topLayout.addComponent(filter);
		topLayout.setComponentAlignment(filter, Alignment.TOP_RIGHT);


		topLayout.addComponent(newProduct);
		newProduct.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				viewLogic.newProduct();
			}
		});
		topLayout.addComponent(formAsPopup);
		topLayout.setComponentAlignment(formAsPopup, Alignment.MIDDLE_LEFT);
		formWindow.setWidth("400px");
		formWindow.setModal(true);
		formWindow.addCloseListener(new CloseListener() {
			@Override
			public void windowClose(CloseEvent e) {
				viewLogic.discardProduct();
			}
		});
		formAsPopup.setImmediate(true);
		formAsPopup.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (formAsPopup.getValue()) {
					removeComponent(form);
					formWindow.setContent(form);
				} else {
					formWindow.setContent(null);
					addComponent(form);
				}
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
