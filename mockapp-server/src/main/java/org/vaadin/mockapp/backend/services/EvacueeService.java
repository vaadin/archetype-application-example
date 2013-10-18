package org.vaadin.mockapp.backend.services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.LocalDate;
import org.vaadin.mockapp.backend.BaseDomainService;
import org.vaadin.mockapp.backend.domain.EvacuationCenter;
import org.vaadin.mockapp.backend.domain.Evacuee;

import java.util.List;

/**
 * @author petter@vaadin.com
 */
public interface EvacueeService extends BaseDomainService.WritableDomainService<Evacuee> {

    @NotNull
    List<Evacuee> findByDateOfBirth(@NotNull EvacuationCenter center, @NotNull LocalDate dateOfBirth);

    @NotNull
    List<Evacuee> findByName(@NotNull EvacuationCenter center, @NotNull String familyName, @NotNull String givenNames);

    @Nullable
    Evacuee findByNumber(@NotNull EvacuationCenter center, int number);

}
