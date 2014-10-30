package org.vaadin.mockapp.samples.data;

public enum Availability {
	COMING_SOON("Coming soon"), PREORDER("Preorder"), AVAILABLE("Available"), DISCONTINUED(
			"Discontinued");

	private final String name;
	
	private Availability(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public boolean isAvailable() {
		return !(this == COMING_SOON || this == DISCONTINUED);
	}

}
