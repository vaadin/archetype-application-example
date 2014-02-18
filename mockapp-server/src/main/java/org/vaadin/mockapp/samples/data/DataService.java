package org.vaadin.mockapp.samples.data;

import java.util.Collection;

public interface DataService {

	Collection<Product> getAllProducts();

	Collection<Category> getAllCategories();

}
