package org.vaadin.mockapp.samples.crud;

import org.vaadin.mockapp.samples.data.Product;
import org.vaadin.mockapp.samples.data.State;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Table;

public class ProductTable extends Table {

	private BeanContainer<Integer, Product> container;

	public ProductTable() {
		setSizeFull();

		container = new BeanContainer<Integer, Product>(Product.class);
		container.setBeanIdProperty("id");
		setContainerDataSource(container);
		setVisibleColumns("id", "productName", "price", "state", "stockCount",
				"category");
		setColumnHeaders("Product id", "Product", "Price (€)", "State",
				"In stock", "Categories");
		setColumnCollapsingAllowed(true);
		setColumnCollapsed("integerProperty", true);
		setColumnCollapsed("bigDecimalProperty", true);

		setColumnWidth("id", 70);
		setColumnAlignment("price", Align.RIGHT);
		setColumnAlignment("stockCount", Align.RIGHT);
		setSelectable(true);
		setImmediate(true);

		// Add " €" automatically after price
		setConverter("price", new EuroConverter());
		// Show categories as a comma separated list
		setConverter("category", new CollectionToStringConverter());
		setCellStyleGenerator(new CellStyleGenerator() {

			@Override
			public String getStyle(Table source, Object itemId,
					Object propertyId) {
				if ("state".equals(propertyId)) {
					State s = (State) source.getContainerProperty(itemId,
							propertyId).getValue();
					if (!s.isAvailable())
						return "not-available";
				}
				return null;
			}
		});
	}

	public void setFilter(String filterString) {
		container.removeAllContainerFilters();
		if (filterString.length() > 0) {
			SimpleStringFilter nameFilter = new SimpleStringFilter(
					"productName", filterString, true, false);
			SimpleStringFilter stateFilter = new SimpleStringFilter("state",
					filterString, true, false);
			SimpleStringFilter categoryFilter = new SimpleStringFilter(
					"category", filterString, true, false);
			container.addContainerFilter(new Or(nameFilter, stateFilter,
					categoryFilter));
		}

	}

	@Override
	public Integer getValue() {
		return (Integer) super.getValue();
	}

	@Override
	public BeanItem<Product> getItem(Object itemId) {
		return (BeanItem<Product>) super.getItem(itemId);
	}

	@Override
	public BeanContainer<Integer, Product> getContainerDataSource() {
		return (BeanContainer<Integer, Product>) super.getContainerDataSource();
	}
}
