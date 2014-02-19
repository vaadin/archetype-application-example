package org.vaadin.mockapp.samples.crud;

import org.vaadin.mockapp.MockAppUI;
import org.vaadin.mockapp.samples.backend.DataService;
import org.vaadin.mockapp.samples.data.Product;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Label;

public class SampleCrudLogic {

	private SampleCrudView view;
	private BeanFieldGroup<Product> fieldGroup;

	public SampleCrudLogic(SampleCrudView simpleCrudView) {
		this.view = simpleCrudView;
	}

	public void init() {
		setupTable();
		setupForm();
		refreshTable();
	}

	private void setupForm() {
		fieldGroup = new BeanFieldGroup<Product>(Product.class);
		fieldGroup.bindMemberFields(view.form);

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

		// Set default field values
		setFormDataSource(null);

		// Hide the whole form if not admin
		if (!MockAppUI.get().getAccessControl().isUserInRole("admin")) {
			view.replaceComponent(view.form, new Label(
					"Login as 'admin' to have edit access"));
		}
	}

	public void discardProduct() {
		fieldGroup.discard();
		navigateToProduct("");
	}

	private void navigateToProduct(String productId) {
		String targetState;
		if (productId == null || productId.isEmpty()) {
			targetState = SampleCrudView.VIEW_NAME;
		} else {
			targetState = SampleCrudView.VIEW_NAME + "/" + productId;
		}

		Navigator navigator = view.getUI().getNavigator();
		String currentState = navigator.getState();
		if (!currentState.equals(targetState)) {
			navigator.navigateTo(targetState);
		}
	}

	public void enter(String productId) {
		if (productId == null || productId.isEmpty()) {
			setFormDataSource(null);
			view.table.setValue(null);
		} else {
			Product product = findProduct(Integer.parseInt(productId));
			setFormDataSource(product);
			view.table.setValue(product.getId());
		}
	}

	private Product findProduct(int productId) {
		return DataService.get().getProductById(productId);
	}

	public void saveProduct() {
		try {
			fieldGroup.commit();
			Product p = fieldGroup.getItemDataSource().getBean();
			view.showSaveNotification(p.getProductName() + " (" + p.getId()
					+ ") updated");
			refreshTable();
			navigateToProduct("");
		} catch (CommitException e) {
			view.showError("Please re-check the fields");
		}
	}

	private void setupTable() {
		view.table.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				Integer selectedProductId = view.table.getValue();
				if (selectedProductId == null)
					navigateToProduct("");
				else
					navigateToProduct(selectedProductId + "");
			}
		});
	}

	public void setFormDataSource(Product product) {
		view.form.setEnabled(product != null);
		if (product == null) {
			fieldGroup.setItemDataSource(new BeanItem<Product>(new Product()));
		} else {
			fieldGroup.setItemDataSource(new BeanItem<Product>(product));
		}
	}

	private void refreshTable() {
		Object oldSelection = view.table.getValue();
		BeanContainer<Integer, Product> container = view.table
				.getContainerDataSource();
		container.removeAllItems();
		container.addAll(DataService.get().getAllProducts());
		view.table.setValue(oldSelection);
	}
}
