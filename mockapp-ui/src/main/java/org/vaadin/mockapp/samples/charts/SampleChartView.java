package org.vaadin.mockapp.samples.charts;

import java.util.Collection;

import org.vaadin.mockapp.samples.backend.DataService;
import org.vaadin.mockapp.samples.data.Category;
import org.vaadin.mockapp.samples.data.Product;

import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;

/**
 * 
 */
public class SampleChartView extends HorizontalLayout implements View {

	public static final String VIEW_NAME = "Charts";

	public SampleChartView() {
		setSizeFull();
		setSpacing(true);

		Collection<Category> categories = DataService.get().getAllCategories();
		Collection<Product> products = DataService.get().getAllProducts();

		ProductsPerCategoryChart c = new ProductsPerCategoryChart();
		c.setData(products, categories);
		ProductsPriceChart c2 = new ProductsPriceChart();
		c2.setData(products, categories);

		addComponent(c2);
		addComponent(c);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
	}
}
