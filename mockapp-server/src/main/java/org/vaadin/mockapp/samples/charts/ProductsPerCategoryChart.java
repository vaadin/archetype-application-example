package org.vaadin.mockapp.samples.charts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.vaadin.mockapp.samples.backend.MockData;
import org.vaadin.mockapp.samples.data.Category;
import org.vaadin.mockapp.samples.data.Product;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsPie;

public class ProductsPerCategoryChart extends Chart {
	public ProductsPerCategoryChart() {
		super(ChartType.PIE);

		Configuration conf = getConfiguration();

		conf.setTitle("Products per category");
		// conf.setSubTitle("Source: www.census.gov");

		PlotOptionsPie options = new PlotOptionsPie();
		options.setInnerSize(0); // Non-0 results in a donut
		options.setSize("50%"); // Default
		options.setCenter("50%", "50%"); // Default
		conf.setPlotOptions(options);

		drawChart(conf);
	}

	public void setData(Collection<Product> products,
			Collection<Category> categories) {
		DataSeries series = new DataSeries();
		Map<Category, Integer> categoryData = new HashMap<Category, Integer>();
		for (Category c : categories) {
			categoryData.put(c, 0);
		}

		for (Product p : products) {
			for (Category c : p.getCategory()) {
				categoryData.put(c, categoryData.get(c) + 1);
			}
		}

		for (Category c : categories) {
			series.add(new DataSeriesItem(c.getName(), categoryData.get(c)));
		}
		getConfiguration().setSeries(series);

	}
}
