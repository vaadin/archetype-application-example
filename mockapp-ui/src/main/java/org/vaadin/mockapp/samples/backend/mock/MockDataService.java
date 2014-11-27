package org.vaadin.mockapp.samples.backend.mock;

import java.util.List;

import org.vaadin.mockapp.samples.backend.DataService;
import org.vaadin.mockapp.samples.backend.data.Category;
import org.vaadin.mockapp.samples.backend.data.Product;

/**
 * Mock data model. Does not handling locking in any way and is not thread safe
 */
public class MockDataService extends DataService {

	private static MockDataService INSTANCE;

	private List<Product> products;
	private List<Category> categories;
	private int nextProductId = 0;

	private MockDataService() {
		categories = MockDataGenerator.createCategories();
		products = MockDataGenerator.createProducts(categories);
		nextProductId = products.size() + 1;
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
		if (p.getId() < 0) {
			// New product
			p.setId(nextProductId++);
			products.add(p);
			return;
		}
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

	@Override
	public void deleteProduct(int productId) {
		Product p = getProductById(productId);
		if (p == null)
			throw new IllegalArgumentException("Product with id "+productId+" not found");
		products.remove(p);
	}
}
