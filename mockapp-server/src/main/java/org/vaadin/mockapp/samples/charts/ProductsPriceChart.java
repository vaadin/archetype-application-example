package org.vaadin.mockapp.samples.charts;

import java.util.Collection;

import org.vaadin.mockapp.samples.data.Category;
import org.vaadin.mockapp.samples.data.Product;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.XAxis;

public class ProductsPriceChart extends Chart {

	public ProductsPriceChart() {
		super(ChartType.COLUMN);

		Configuration conf = getConfiguration();
		conf.setTitle("Product prices");
		conf.getLegend().setEnabled(false); // Disable legend

		XAxis x1 = new XAxis();
		conf.addxAxis(x1);
		x1.setReversed(false);

		final String[] categories = new String[20];

		int step = 1000 / 20;
		for (int i = 0; i < 20; i++) {
			categories[i] = i * step + "-" + (i + 1) * step;
		}
		x1.setCategories(categories);

		drawChart();
	}

	public void setData(Collection<Product> products,
			Collection<Category> categories) {
		final Integer[] prices = new Integer[20];
		for (Product p : products) {
			int priceCategory = (int) (p.getPrice().doubleValue() / 20);
			if (priceCategory > 19)
				priceCategory = 19;

			if (prices[priceCategory] == null)
				prices[priceCategory] = 1;
			else
				prices[priceCategory]++;
		}

		ListSeries series = new ListSeries("Prices");
		series.setData(prices);

		getConfiguration().setSeries(series);

	}
}
