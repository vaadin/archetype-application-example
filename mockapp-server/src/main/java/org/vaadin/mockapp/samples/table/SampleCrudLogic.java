package org.vaadin.mockapp.samples.table;

import org.vaadin.mockapp.samples.backend.DataService;
import org.vaadin.mockapp.samples.data.Product;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;

public class SampleCrudLogic {

	private SampleCrudView view;
	private BeanFieldGroup<Product> fieldGroup;

	public SampleCrudLogic(SampleCrudView simpleCrudView) {
		this.view = simpleCrudView;
	}

	public void init() {
		setupTable();
		setupForm();
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
	}

	public void discardProduct() {
		fieldGroup.discard();
		view.table.setValue(null);
	}

	public void saveProduct() {
		try {
			fieldGroup.commit();
			Product p = fieldGroup.getItemDataSource().getBean();
			view.showSaveNotification(p.getProductName() + " (" + p.getId()
					+ ") updated");
			view.table.setValue(null);
		} catch (CommitException e) {
			view.showError("Please re-check the fields");
		}
	}

	private void setupTable() {
		view.table.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				setFormDataSource(view.table.getItem(view.table.getValue()));
			}
		});
	}

	public void setFormDataSource(BeanItem<Product> item) {
		view.form.setEnabled(item != null);
		if (item == null) {
			fieldGroup.setItemDataSource(new BeanItem<Product>(new Product()));
		} else {
			fieldGroup.setItemDataSource(item);
		}
	}

	public void refreshTable() {
		Product oldSelection = view.table.getValue();
		BeanItemContainer<Product> container = view.table
				.getContainerDataSource();
		container.removeAllItems();
		container.addAll(DataService.get().getAllProducts());
		view.table.setValue(oldSelection);

	}

}
