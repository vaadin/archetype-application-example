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
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class SampleCrudLogic {

	private SampleCrudView view;
	private BeanFieldGroup<Product> fieldGroup;

	public SampleCrudLogic(SampleCrudView simpleCrudView) {
		this.view = simpleCrudView;

		setupTable();
		setupForm();
	}

	private void setupForm() {
		fieldGroup = new BeanFieldGroup<Product>(Product.class);
		fieldGroup.bindMemberFields(view.form);

		IntegerRangeValidator stockValidator = new IntegerRangeValidator(
				"Can't have negative amount in stock", 0, null);
		view.form.stockCount.addValidator(stockValidator);

		view.form.saveButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					fieldGroup.commit();
					view.table.setValue(null);
				} catch (CommitException e) {
					Notification.show("Please re-check the fields",
							Type.ERROR_MESSAGE);
				}
			}
		});
		view.form.discardButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				fieldGroup.discard();
				view.table.setValue(null);
			}
		});

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

}
