package org.vaadin.mockapp.samples.data;

public enum State {
	NOT_YET_AVAILABLE, PREORDER, IN_STORES, DISCONTINUED;

	public boolean isAvailable() {
		return !(this == NOT_YET_AVAILABLE || this == DISCONTINUED);
	}

}
