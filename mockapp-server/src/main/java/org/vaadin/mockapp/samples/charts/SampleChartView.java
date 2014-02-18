package org.vaadin.mockapp.samples.charts;

import java.util.Collection;

import org.vaadin.mockapp.samples.data.Category;
import org.vaadin.mockapp.samples.data.MockData;
import org.vaadin.mockapp.samples.data.Product;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * @author petter@vaadin.com
 */
public class SampleChartView extends HorizontalLayout implements View {

	public static final String VIEW_NAME = "sampleChartView";

	public SampleChartView() {
		Collection<Category> categories = MockData.getInstance()
				.getAllCategories();
		Collection<Product> products = MockData.getInstance().getAllProducts();

		ProductsPerCategoryChart c = new ProductsPerCategoryChart();
		c.setData(products, categories);
		addComponent(c);
		ProductsPriceChart c2 = new ProductsPriceChart();
		c2.setData(products, categories);
		addComponent(c2);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
	}
}
