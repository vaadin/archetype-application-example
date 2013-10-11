package org.vaadin.mockapp.backend;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
public interface BaseDomainService<E extends BaseDomain> {

    @Nullable
    E findByUuid(@NotNull UUID uuid);

    public interface WritableDomainService<E extends BaseDomain> extends BaseDomainService<E> {
        void save(@NotNull E entity);
    }

    public interface DeletableDomainService<E extends BaseDomain> extends BaseDomainService<E> {
        void delete(@NotNull E entity);
    }
}
