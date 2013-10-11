package org.vaadin.mockapp.backend;

import org.jetbrains.annotations.Nullable;
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

    @Nullable
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(@Nullable UUID uuid) {
        this.uuid = uuid;
    }

    @Nullable
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(@Nullable String createUserName) {
        this.createUserName = createUserName;
    }

    @Nullable
    public DateTime getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(@Nullable DateTime createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    @Nullable
    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(@Nullable String updateUserName) {
        this.updateUserName = updateUserName;
    }

    @Nullable
    public DateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(@Nullable DateTime updateTimestamp) {
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
    public BaseDomain clone() {
        try {
            return (BaseDomain) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
