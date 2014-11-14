package org.vaadin.mockapp.samples.crud;

import org.vaadin.mockapp.samples.data.Product;
import org.vaadin.mockapp.samples.data.Availability;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Table;

public class ProductTable extends Table {

	private BeanItemContainer<Product> container;

	public ProductTable() {
		setSizeFull();

		container = new BeanItemContainer<Product>(Product.class);
		setContainerDataSource(container);
		setVisibleColumns("id", "productName", "price", "availability", "stockCount",
				"category");
		setColumnHeaders("ID", "Product", "Price", "Availability",
				"Stock", "Categories");
		setColumnCollapsingAllowed(true);
		setColumnCollapsed("integerProperty", true);
		setColumnCollapsed("bigDecimalProperty", true);

		setColumnWidth("id", 50);
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
				if ("availability".equals(propertyId)) {
					Availability s = (Availability) source.getContainerProperty(itemId,
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
			SimpleStringFilter availabilityFilter = new SimpleStringFilter("availability",
					filterString, true, false);
			SimpleStringFilter categoryFilter = new SimpleStringFilter(
					"category", filterString, true, false);
			container.addContainerFilter(new Or(nameFilter, availabilityFilter,
					categoryFilter));
		}

	}

	@Override
	public Product getValue() {
		return (Product) super.getValue();
	}

	@Override
	public BeanItemContainer<Product> getContainerDataSource() {
		return container;
	}
}
