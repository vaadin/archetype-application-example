package org.vaadin.mockapp.backend.services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vaadin.mockapp.backend.BaseDomainService;
import org.vaadin.mockapp.backend.domain.Diagnosis;

/**
 * @author petter@vaadin.com
 */
public interface DiagnosisService extends BaseDomainService.WritableDomainService<Diagnosis>, BaseDomainService.DeletableDomainService<Diagnosis> {

    @Nullable
    Diagnosis findByIcdCode(@NotNull String icdCode);

}
