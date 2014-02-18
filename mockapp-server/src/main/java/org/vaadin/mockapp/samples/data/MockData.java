package org.vaadin.mockapp.samples.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.google.gwt.dev.util.collect.HashSet;

/**
 * @author petter@vaadin.com
 */
public class MockData implements DataService {

	private static MockData INSTANCE;

	private List<Product> products;
	private List<Product> categories;

	private Map<UUID, Product> idToMasterRecordMap;

	private Random random = new Random(1);

	private MockData() {
		generateMockData();

		Random rnd = new Random();
		masterRecords = new ArrayList<SampleMaster>();
		idToMasterRecordMap = new HashMap<UUID, SampleMaster>();
		for (int i = 0; i < 200; ++i) {
			SampleMaster master = new SampleMaster();
			master.setUuid(UUID.randomUUID());
			master.setIntegerProperty(i);
			master.setBooleanProperty(i % 2 == 0);
			master.setStringProperty("This is string number " + i);
			master.setBigDecimalProperty(new BigDecimal(i / 100.0));
			master.getEmbeddedProperty().setEnumProperty(
					SampleEnum.values()[i % SampleEnum.values().length]);

			int detailCount = rnd.nextInt(15) + 1;
			for (int j = 0; j < detailCount; ++j) {
				SampleDetail detail = new SampleDetail();
				detail.setIntegerProperty(rnd.nextInt());
				detail.setStringProperty("This is the child " + j
						+ " of master " + i);
				master.getDetails().add(detail);
			}
			masterRecords.add(master);
			idToMasterRecordMap.put(master.getUuid(), master);
		}
	}

	private void generateMockData() {
		addCategory("Children's books");
		addCategory("Best sellers");
		addCategory("Romance");
		addCategory("Mystery");
		addCategory("Thriller");
		addCategory("Sci-fi");
		addCategory("Non-fiction");
		addCategory("Cookbooks");

		for (int i = 0; i < 100; i++) {
			Product p = generateProduct(i + 1);
			products.add(p);

		}
	}

	private Product generateProduct(int id) {
		Product p = new Product();
		p.setId(id);
		p.setProductName(generateName());

		p.setPrice(new BigDecimal(random.nextInt(10000) / 10.0));
		p.setState(State.values()[random.nextInt(State.values().length)]);
		if (p.getState().isAvailable()) {
			p.setStockCount(random.nextInt(523));
		}

		p.setCategory(category);
		return p;
	}

	private String[] word1 = new String[] { "The art of", "Mastering",
			"The secrets of" };

	private String[] word2 = new String[] { "gardening",
			"living a healthy life", "designing tree houses", "home security",
			"intergalaxy travel", };

	private String generateName() {
		return word1[random.nextInt(word1.length)] + " "
				+ word2[random.nextInt(word2.length)];
	}

	private Set<Category> getCategory(int i, int j) {
		int nr = random.nextInt(j - 1) + i;
		HashSet<Category> productCategories = new HashSet<Category>()
		for (int i=0; i < nr; i++) {
			productCategories.add(categories.get(random.nextInt(categories.size())));
		}
	}

	public synchronized static DataService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MockData();
		}
		return INSTANCE;
	}

	@SuppressWarnings("unchecked")
	private static <T extends Cloneable> T nullSafeClone(T original) {
		if (original == null) {
			return null;
		}
		try {
			return (T) original.getClass().getMethod("clone").invoke(original);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized List<SampleMaster> getMasterRecords() {
		List<SampleMaster> copy = new ArrayList<SampleMaster>(
				masterRecords.size());
		for (SampleMaster master : masterRecords) {
			copy.add(nullSafeClone(master));
		}
		return copy;
	}
}
