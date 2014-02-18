package org.vaadin.mockapp.samples.table;

import java.util.Collection;

import org.vaadin.mockapp.samples.backend.DataService;
import org.vaadin.mockapp.samples.data.Category;
import org.vaadin.mockapp.samples.data.Product;
import org.vaadin.mockapp.samples.data.State;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public class ProductForm extends GridLayout {

	TextField stockCount = new TextField("In stock");
	NativeSelect state = new NativeSelect("State");
	TwinColSelect category = new TwinColSelect("Categories");
	TextField price = new TextField("Price");
	TextField productName = new TextField("Product name");
	Button saveButton = new Button("Save");
	Button discardButton = new Button("Discard");

	public ProductForm() {
		super(3, 3);
		setSpacing(true);

		productName.setWidth("100%");
		addComponent(productName, 0, 0, 2, 0);

		price.setWidth("60px");
		addComponent(price);

		stockCount.setWidth("80px");
		addComponent(stockCount);

		for (State s : State.values()) {
			state.addItem(s);
		}
		addComponent(state);

		category.setWidth("100%");
		addComponent(category, 0, 2, 2, 2);

		addComponent(saveButton);
		addComponent(discardButton);
		
		setColumnExpandRatio(2,1);

	}

	public void setCategories(Collection<Category> categories) {
		category.removeAllItems();
		for (Category c : categories) {
			category.addItem(c);
		}
	}
}
