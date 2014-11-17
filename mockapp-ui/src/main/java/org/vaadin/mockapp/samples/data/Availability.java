package org.vaadin.mockapp.samples.data;

public enum Availability {
	COMING("Coming"), AVAILABLE("Available"), DISCONTINUED("Discontinued");

	private final String name;

	private Availability(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
