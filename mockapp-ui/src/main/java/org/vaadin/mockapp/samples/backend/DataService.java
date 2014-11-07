package org.vaadin.mockapp.samples.backend;

import java.util.Collection;

import org.vaadin.mockapp.samples.data.Category;
import org.vaadin.mockapp.samples.data.Product;

public abstract class DataService {

	public abstract Collection<Product> getAllProducts();

	public abstract Collection<Category> getAllCategories();

	public abstract void updateProduct(Product p);

	public abstract void deleteProduct(int productId);

	public abstract Product getProductById(int productId);

	public static DataService get() {
		return MockDataService.getInstance();
	}

}
