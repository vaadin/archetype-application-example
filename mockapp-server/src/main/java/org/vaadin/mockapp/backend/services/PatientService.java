package org.vaadin.mockapp.backend.services;

import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;
import org.vaadin.mockapp.backend.BaseDomainService;
import org.vaadin.mockapp.backend.domain.Patient;

import java.util.List;

/**
 * @author petter@vaadin.com
 */
public interface PatientService extends BaseDomainService.WritableDomainService<Patient> {

    @NotNull
    List<Patient> findByDateOfBirth(@NotNull LocalDate dateOfBirth);

    @NotNull
    List<Patient> findByName(@NotNull String firstName, @NotNull String lastName);

}
