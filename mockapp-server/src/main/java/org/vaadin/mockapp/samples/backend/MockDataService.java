package org.vaadin.mockapp.samples.backend;

import java.util.List;

import org.vaadin.mockapp.samples.data.Category;
import org.vaadin.mockapp.samples.data.Product;

/**
 * Mock data model. Does not handling locking in any way and is not thread safe
 */
public class MockDataService extends DataService {

	private static MockDataService INSTANCE;

	private List<Product> products;
	private List<Category> categories;

	private MockDataService() {
		categories = MockDataGenerator.createCategories();
		products = MockDataGenerator.createProducts(categories);
	}

	public synchronized static DataService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MockDataService();
		}
		return INSTANCE;
	}

	public List<Product> getAllProducts() {
		return products;
	}

	public List<Category> getAllCategories() {
		return categories;
	}

	@Override
	public void updateProduct(Product p) {
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getId() == p.getId()) {
				products.set(i, p);
				return;
			}
		}

		throw new IllegalArgumentException("No product with id " + p.getId()
				+ " found");
	}

	@Override
	public Product getProductById(int productId) {
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getId() == productId)
				return products.get(i);
		}
		return null;
	}
}
