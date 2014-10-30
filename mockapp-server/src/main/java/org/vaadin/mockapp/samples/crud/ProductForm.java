package org.vaadin.mockapp.samples.crud;

import java.util.Collection;

import org.vaadin.mockapp.samples.AttributeExtension;
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
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
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
	private BeanFieldGroup<Product> fieldGroup;

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

		fieldGroup = new BeanFieldGroup<Product>(Product.class);
		fieldGroup.bindMemberFields(this);

		fieldGroup.addCommitHandler(new CommitHandler() {

			@Override
			public void preCommit(CommitEvent commitEvent)
					throws CommitException {
			}

			@Override
			public void postCommit(CommitEvent commitEvent)
					throws CommitException {
				DataService.get().updateProduct(
						fieldGroup.getItemDataSource().getBean());
			}
		});

		saveButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					fieldGroup.commit();
				} catch (CommitException e) {
					Notification.show("Please re-check the fields",
							Type.ERROR_MESSAGE);
					e.printStackTrace();
				}
				Product product = fieldGroup.getItemDataSource().getBean();
				viewLogic.saveProduct(product);
			}
		});

		deleteButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Product product = fieldGroup.getItemDataSource().getBean();
				viewLogic.deleteProduct(product);
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

	public void editProduct(Product product) {
		if (product == null) {
			fieldGroup.setItemDataSource(new BeanItem<Product>(new Product()));
		} else {
			fieldGroup.setItemDataSource(new BeanItem<Product>(product));
		}
	}

}
