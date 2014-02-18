package org.vaadin.mockapp.samples.data;

import java.util.List;

/**
 * @author petter@vaadin.com
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

	public synchronized List<Product> getAllProducts() {
		// TODO
		return products;
		// List<SampleMaster> copy = new ArrayList<SampleMaster>(
		// masterRecords.size());
		// for (SampleMaster master : masterRecords) {
		// copy.add(nullSafeClone(master));
		// }
		// return copy;
	}
}
