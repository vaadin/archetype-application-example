package org.vaadin.mockapp.samples.backend;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.vaadin.mockapp.samples.data.Category;
import org.vaadin.mockapp.samples.data.Product;
import org.vaadin.mockapp.samples.data.State;

public class MockDataGenerator {
	private static int nextCategoryId = 1;
	private static int nextProductId = 1;
	private static final Random random = new Random(1);
	private static final String categoryNames[] = new String[] {
			"Children's books", "Best sellers", "Romance", "Mystery",
			"Thriller", "Sci-fi", "Non-fiction", "Cookbooks" };

	private static String[] word1 = new String[] { "The art of", "Mastering",
			"The secrets of", "Avoiding" };

	private static String[] word2 = new String[] { "gardening",
			"living a healthy life", "designing tree houses", "home security",
			"intergalaxy travel", "meditation", "ice hockey",
			"children's education" };

	static List<Category> createCategories() {
		List<Category> categories = new ArrayList<Category>();
		for (String name : categoryNames) {
			Category c = createCategory(name);
			categories.add(c);
		}
		return categories;

	}

	static List<Product> createProducts(List<Category> categories) {
		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i < 100; i++) {
			Product p = createProduct(categories);
			products.add(p);
		}

		return products;
	}

	private static Category createCategory(String name) {
		Category c = new Category();
		c.setId(nextCategoryId++);
		c.setName(name);
		return c;
	}

	private static Product createProduct(List<Category> categories) {
		Product p = new Product();
		p.setId(nextProductId++);
		p.setProductName(generateName());

		p.setPrice(new BigDecimal(random.nextInt(10000) / 10.0));
		p.setState(State.values()[random.nextInt(State.values().length)]);
		if (p.getState().isAvailable()) {
			p.setStockCount(random.nextInt(523));
		}

		p.setCategory(getCategory(categories, 1, 2));
		return p;
	}

	private static Set<Category> getCategory(List<Category> categories,
			int min, int max) {
		int nr = random.nextInt(max) + min;
		HashSet<Category> productCategories = new HashSet<Category>();
		for (int i = 0; i < nr; i++) {
			productCategories.add(categories.get(random.nextInt(categories
					.size())));
		}

		return productCategories;
	}

	private static String generateName() {
		return word1[random.nextInt(word1.length)] + " "
				+ word2[random.nextInt(word2.length)];
	}

}
