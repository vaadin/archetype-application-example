package org.vaadin.mockapp.samples.crud;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.vaadin.mockapp.samples.data.Category;

import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.VerticalLayout;

/**
 * A custom Field implementation that allows selecting a set of categories using
 * checkboxes rather than multi-selection in a list/table or a TwinColSelect.
 */
public class CategoryField extends CustomField<Set<Category>> {

	private VerticalLayout options;
	private Map<Category, CheckBox> checkboxes = new HashMap<Category, CheckBox>();
	private boolean updatingField = false;

	public CategoryField() {
		options = new VerticalLayout();
	}

	public CategoryField(String caption) {
		this();
		setCaption(caption);
	}

	@Override
	protected Component initContent() {
		return options;
	}

	/**
	 * Set the collection of categories among which the used can select a
	 * subset.
	 * 
	 * @param categories
	 *            all available categories
	 */
	public void setOptions(Collection<Category> categories) {
		options.removeAllComponents();
		checkboxes.clear();
		for (final Category category : categories) {
			final CheckBox box = new CheckBox(category.getName());
			checkboxes.put(category, box);
			box.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(
						com.vaadin.data.Property.ValueChangeEvent event) {
					if (!updatingField) {
						Set<Category> oldCategories = getValue();
						Set<Category> categories;
						if (oldCategories != null) {
							categories = new HashSet<Category>(oldCategories);
						} else {
							categories = new HashSet<Category>();
						}
						if (box.getValue()) {
							categories.add(category);
						} else {
							categories.remove(category);
						}
						setInternalValue(categories);
					}
				}
			});
			options.addComponent(box);
		}
	}

	@Override
	public Class getType() {
		return Set.class;
	}

	@Override
	protected void setInternalValue(Set<Category> newValue) {
		updatingField = true;
		super.setInternalValue(newValue);
		if (newValue != null) {
			for (Category category : checkboxes.keySet()) {
				checkboxes.get(category).setValue(newValue.contains(category));
			}
		} else {
			for (Category category : checkboxes.keySet()) {
				checkboxes.get(category).setValue(false);
			}
		}
		updatingField = false;
	}
}
