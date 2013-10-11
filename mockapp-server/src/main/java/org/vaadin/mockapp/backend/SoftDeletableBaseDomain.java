package org.vaadin.mockapp.backend;

/**
 * @author petter@vaadin.com
 */
public abstract class SoftDeletableBaseDomain extends BaseDomain {

    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
