package org.vaadin.mockapp.samples.crud;

import java.util.Collection;

import org.vaadin.mockapp.samples.AttributeExtension;
import org.vaadin.mockapp.samples.data.Category;
import org.vaadin.mockapp.samples.data.State;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ProductForm extends VerticalLayout {

	TextField stockCount = new TextField("In stock");
	NativeSelect state = new NativeSelect("Availability");
	CategoryField category = new CategoryField("Categories");
	TextField price = new TextField("Price");
	TextField productName = new TextField("Product name");
	Button saveButton = new Button("Save");
	Button deleteButton = new Button("Delete");
	Button discardButton = new Button("Discard");
	private SampleCrudLogic viewLogic;

	public ProductForm(SampleCrudLogic sampleCrudLogic) {
		this.viewLogic = sampleCrudLogic;

		productName.setWidth("100%");

		price.setConverter(new EuroConverter());

		AttributeExtension ae = new AttributeExtension();
		ae.extend(stockCount);
		ae.setAttribute("type", "number");
		stockCount.setWidth("80px");

		state.setNullSelectionAllowed(false);
		for (State s : State.values()) {
			state.addItem(s);
		}

		category.setWidth("100%");

		saveButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		deleteButton.addStyleName("dark-button");
		saveButton.setWidth("100%");
		discardButton.setWidth("100%");
		deleteButton.setWidth("100%");

		setSizeFull();
		setMargin(true);
		setSpacing(true);
		addStyleName("form-layout");

		HorizontalLayout priceAndStock = new HorizontalLayout(price, stockCount);
		priceAndStock.setSpacing(true);
		priceAndStock.setWidth("100%");
		price.setWidth("100%");
		stockCount.setWidth("100%");
		addComponent(productName);
		addComponent(priceAndStock);
		addComponent(state);
		addComponent(category);
		addComponent(saveButton);
		addComponent(discardButton);
		addComponent(deleteButton);

		setExpandRatio(category, 1);

		saveButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				viewLogic.saveProduct();
			}
		});

		deleteButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				viewLogic.deleteProduct();
			}
		});

		discardButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				viewLogic.discardProduct();
			}
		});

	}

	public void setCategories(Collection<Category> categories) {
		category.setOptions(categories);
		// category.removeAllItems();
		// for (Category c : categories) {
		// category.addItem(c);
		// }
	}

}
