package org.vaadin.mockapp.samples.data;

public enum State {
	NOT_YET_AVAILABLE, PREORDER, FOR_SALE, FOR_SALE_BUSINESS_ONLY, DISCONTINUED;

	public boolean isAvailable() {
		return !(this == NOT_YET_AVAILABLE || this == DISCONTINUED);
	}

}
