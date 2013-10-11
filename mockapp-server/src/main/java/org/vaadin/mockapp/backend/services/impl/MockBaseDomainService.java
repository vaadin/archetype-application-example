package org.vaadin.mockapp.backend.services.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.vaadin.mockapp.backend.BaseDomain;
import org.vaadin.mockapp.backend.BaseDomainService;
import org.vaadin.mockapp.backend.SoftDeletableBaseDomain;
import org.vaadin.mockapp.backend.authentication.AuthenticationHolder;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
public abstract class MockBaseDomainService<E extends BaseDomain> implements BaseDomainService<E>, BaseDomainService.DeletableDomainService<E>, BaseDomainService.WritableDomainService<E> {

    protected final Map<UUID, E> entityMap = new HashMap<UUID, E>();

    @NotNull
    protected Class<E> getEntityClass() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public synchronized void save(@NotNull E entity) {
        beforeSave(entity);
        if (entity.getUuid() == null || !entityMap.containsKey(entity.getUuid())) {
            entity.setUuid(UUID.randomUUID());
            entity.setCreateTimestamp(new DateTime());
            entity.setCreateUserName(AuthenticationHolder.getAuthentication().getName());
            entity.setUpdateTimestamp(null);
            entity.setUpdateUserName(null);
        } else {
            entity.setUpdateTimestamp(new DateTime());
            entity.setUpdateUserName(AuthenticationHolder.getAuthentication().getName());
        }
        doSave(getEntityClass().cast(entity.clone()));
    }

    protected void beforeSave(@NotNull E entity) {
    }

    protected void doSave(@NotNull E entity) {
        entityMap.put(entity.getUuid(), entity);
    }

    @Override
    public synchronized E findByUuid(@NotNull UUID uuid) {
        beforeFindByUuid(uuid);
        return nullSafeClone(entityMap.get(uuid));
    }

    protected void beforeFindByUuid(@NotNull UUID uuid) {
    }

    @Override
    public synchronized void delete(@NotNull E entity) {
        beforeDelete(entity);
        if (entityMap.containsKey(entity.getUuid())) {
            if (entity instanceof SoftDeletableBaseDomain) {
                ((SoftDeletableBaseDomain) entity).setDeleted(true);
                save(entity);
            } else {
                doDelete(entity);
            }
        }
    }

    protected void beforeDelete(@NotNull E entity) {
    }

    protected void doDelete(@NotNull E entity) {
        entityMap.remove(entity.getUuid());
    }

    @Nullable
    protected E nullSafeClone(@Nullable E entity) {
        return entity == null ? null : getEntityClass().cast(entity.clone());
    }

}
