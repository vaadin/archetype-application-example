package org.vaadin.mockapp.samples.crud;

import org.vaadin.mockapp.samples.data.Availability;
import org.vaadin.mockapp.samples.data.Product;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class ProductTable extends Table {

	private BeanItemContainer<Product> container;

	private ColumnGenerator availabilityGenerator = new ColumnGenerator() {

		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {
			Property property = source.getItem(itemId)
					.getItemProperty(columnId);
			if (property != null) {
				Availability availability = (Availability) property.getValue();
				String color = "";
				if (availability == Availability.AVAILABLE) {
					color = "#2dd085";
				} else if (availability == Availability.COMING) {
					color = "#ffc66e";
				} else if (availability == Availability.DISCONTINUED) {
					color = "#f54993";
				}

				String iconCode = "<span class=\"v-icon\" style=\"font-family: "
						+ FontAwesome.CIRCLE.getFontFamily()
						+ ";color:"
						+ color
						+ "\">&#x"
						+ Integer
								.toHexString(FontAwesome.CIRCLE.getCodepoint())
						+ ";</span>";

				return new Label(iconCode + " " + property.getValue(),
						ContentMode.HTML);
			}
			return null;
		}
	};
	
	public ProductTable() {
		setSizeFull();

		container = new BeanItemContainer<Product>(Product.class);
		setContainerDataSource(container);
		setVisibleColumns("id", "productName", "price", "availability",
				"stockCount", "category");
		setColumnHeaders("ID", "Product", "Price", "Availability", "Stock",
				"Categories");
		setColumnCollapsingAllowed(true);
		setColumnCollapsed("integerProperty", true);
		setColumnCollapsed("bigDecimalProperty", true);

		setColumnWidth("id", 50);
		setColumnAlignment("price", Align.RIGHT);
		setColumnAlignment("stockCount", Align.RIGHT);
		setSelectable(true);
		setImmediate(true);
		// Add an traffic light icon in front of availability
		addGeneratedColumn("availability", availabilityGenerator);
		// Add " €" automatically after price
		setConverter("price", new EuroConverter());
		// Show categories as a comma separated list
		setConverter("category", new CollectionToStringConverter());
	}
	
	@Override
    protected String formatPropertyValue(Object rowId,
            Object colId, Property property) {
		if(colId.equals("stockCount")){
			Integer stock = (Integer)property.getValue();
			if(stock.equals(0)){
				return "-";
			} else {
				return stock.toString();
			}
		}
        return super.formatPropertyValue(rowId, colId, property);
    }

	public void setFilter(String filterString) {
		container.removeAllContainerFilters();
		if (filterString.length() > 0) {
			SimpleStringFilter nameFilter = new SimpleStringFilter(
					"productName", filterString, true, false);
			SimpleStringFilter availabilityFilter = new SimpleStringFilter(
					"availability", filterString, true, false);
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
