package org.vaadin.mockapp.samples.table;

import java.math.BigDecimal;
import java.util.Set;

import org.vaadin.mockapp.samples.data.Category;
import org.vaadin.mockapp.samples.data.MockData;
import org.vaadin.mockapp.samples.data.Product;
import org.vaadin.mockapp.samples.data.State;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Table;

public class ProductTable extends Table {

	private BeanItemContainer<Product> container = new BeanItemContainer<Product>(
			Product.class);

	public ProductTable() {
		setSizeFull();
		setContainerDataSource(container);
//		setVisibleColumns("productName", "integerProperty",
//				"bigDecimalProperty", "booleanProperty",
//				"embeddedProperty.enumProperty");
//		setColumnHeaders("UUID", "String", "Integer", "BigDecimal", "Boolean",
//				"Enum");
		setColumnCollapsingAllowed(true);
		setColumnCollapsed("integerProperty", true);
		setColumnCollapsed("bigDecimalProperty", true);
		setSelectable(true);
		setImmediate(true);

//		setConverter("price",new EuroConverter());
	}

	public void setFilter(String filterString) {
		container.removeAllContainerFilters();
		if (filterString.length() > 0) {
			String[] filterWords = filterString.split(" ");
			Container.Filter[] filters = new Container.Filter[filterWords.length];
			for (int i = 0; i < filterWords.length; ++i) {
				String word = filterWords[i];
				filters[i] = new Or(new SimpleStringFilter("stringProperty",
						word, false, false), new SimpleStringFilter(
						"integerProperty", word, true, true),
						new SimpleStringFilter("embeddedProperty.enumProperty",
								word, true, false));
			}
			container.addContainerFilter(new And(filters));
		}

	}

	@Override
	public Product getValue() {
		return (Product) super.getValue();
	}

	@Override
	public BeanItemContainer<Product> getContainerDataSource() {
		return (BeanItemContainer<Product>) super.getContainerDataSource();
	}
}
