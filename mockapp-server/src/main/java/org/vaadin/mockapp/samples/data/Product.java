package org.vaadin.mockapp.samples.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Product implements Serializable {

	@NotNull
	private int id;
	@NotNull
	private String productName = "";
	@Min(0)
	private BigDecimal price = BigDecimal.ZERO;;
	private Set<Category> category;
	@Min(0)
	private int stockCount = 0;
	// private boolean onTheWeb = false;
	@NotNull
	private State state = State.NOT_YET_AVAILABLE;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Set<Category> getCategory() {
		return category;
	}

	public void setCategory(Set<Category> category) {
		this.category = category;
	}

	public int getStockCount() {
		return stockCount;
	}

	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
