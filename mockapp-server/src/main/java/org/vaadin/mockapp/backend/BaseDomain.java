package org.vaadin.mockapp.backend;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
public abstract class BaseDomain implements Serializable, Cloneable {

    private UUID uuid;
    private String createUserName;
    private DateTime createTimestamp;
    private String updateUserName;
    private DateTime updateTimestamp;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public DateTime getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(DateTime createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public DateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(DateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        BaseDomain other = (BaseDomain) obj;
        if (uuid == null) {
            return other == this;
        } else {
            return uuid.equals(other.uuid);
        }
    }

    @Override
    public int hashCode() {
        if (uuid == null) {
            return super.hashCode();
        } else {
            return uuid.hashCode();
        }
    }

    @Override
    public BaseDomain clone() throws CloneNotSupportedException {
        return (BaseDomain) super.clone();
    }

}
