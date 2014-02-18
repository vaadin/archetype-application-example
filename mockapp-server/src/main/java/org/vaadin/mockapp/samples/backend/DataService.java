package org.vaadin.mockapp.samples.backend;

import java.util.Collection;

import org.vaadin.mockapp.samples.data.Category;
import org.vaadin.mockapp.samples.data.Product;

public interface DataService {

	Collection<Product> getAllProducts();

	Collection<Category> getAllCategories();

}
