package org.vaadin.mockapp.backend.services;

import org.vaadin.mockapp.backend.BaseDomainService;
import org.vaadin.mockapp.backend.domain.Appointment;
import org.vaadin.mockapp.backend.domain.TimeSlot;

/**
 * @author petter@vaadin.com
 */
public interface AppointmentService extends BaseDomainService.WritableDomainService<Appointment>, BaseDomainService.DeletableDomainService<Appointment> {

    Appointment findByTimeSlot(TimeSlot timeSlot);

}
