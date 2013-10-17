package org.vaadin.mockapp.backend.services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.vaadin.mockapp.backend.BaseDomainService;
import org.vaadin.mockapp.backend.domain.Doctor;

import java.util.List;

/**
 * @author petter@vaadin.com
 */
public interface DoctorService extends BaseDomainService.WritableDomainService<Doctor>, BaseDomainService.DeletableDomainService<Doctor> {

    @Nullable
    Doctor findByCode(@NotNull String code);

    @NotNull
    List<Doctor> findAll();

}
