package org.vaadin.mockapp.backend.services;

import org.jetbrains.annotations.NotNull;
import org.vaadin.mockapp.backend.BaseDomainService;
import org.vaadin.mockapp.backend.domain.EvacuationCenter;

import java.util.List;

/**
 * @author petter@vaadin.com
 */
public interface EvacuationCenterService extends BaseDomainService.WritableDomainService<EvacuationCenter>, BaseDomainService.DeletableDomainService<EvacuationCenter> {

    @NotNull
    List<EvacuationCenter> findAll();

}
