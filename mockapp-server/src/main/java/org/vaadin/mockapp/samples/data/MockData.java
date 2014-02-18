package org.vaadin.mockapp.samples.data;

import java.util.List;

/**
 * Mock data model. Does not handling locking in any way and is not thread safe
 */
public class MockData implements DataService {

	private static MockData INSTANCE;

	private List<Product> products;
	private List<Category> categories;

	private MockData() {
		categories = MockDataGenerator.createCategories();
		products = MockDataGenerator.createProducts(categories);
	}

	public synchronized static DataService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MockData();
		}
		return INSTANCE;
	}

	public List<Product> getAllProducts() {
		return products;
	}

	public List<Category> getAllCategories() {
		return categories;
	}
}
