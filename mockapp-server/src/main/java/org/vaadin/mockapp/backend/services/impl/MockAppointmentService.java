package org.vaadin.mockapp.backend.services.impl;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.backend.authentication.AccessDeniedException;
import org.vaadin.mockapp.backend.domain.Appointment;
import org.vaadin.mockapp.backend.domain.Patient;
import org.vaadin.mockapp.backend.domain.TimeSlot;
import org.vaadin.mockapp.backend.services.AppointmentService;
import org.vaadin.mockapp.backend.services.PatientService;
import org.vaadin.mockapp.backend.services.TimeSlotService;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
public class MockAppointmentService extends MockBaseDomainService<Appointment> implements AppointmentService {

    void createMockData() {
        final Random rnd = new Random();
        final Collection<Patient> patients = ((MockPatientService) Services.get(PatientService.class)).getEntityMap().values();
        final Collection<TimeSlot> timeSlots = ((MockTimeSlotService) Services.get(TimeSlotService.class)).getEntityMap().values();

        for (TimeSlot ts : timeSlots) {
            if (rnd.nextBoolean()) {
                Appointment appointment = new Appointment();
                appointment.setCreateTimestamp(new DateTime());
                appointment.setCreateUserName("root");
                appointment.setUuid(UUID.randomUUID());
                appointment.setTimeSlot(ts);
                appointment.setPatient((Patient) patients.toArray()[rnd.nextInt(patients.size())]);
                doSave(appointment);
            }
        }
    }

    @Override
    public synchronized Appointment findByTimeSlot(TimeSlot timeSlot) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_DOCTOR, MockAppRoles.ROLE_RECEPTIONIST);
        for (Appointment appointment : entityMap.values()) {
            if (appointment.getTimeSlot().equals(timeSlot)) {
                return appointment;
            }
        }
        return null;
    }

    @Override
    protected void beforeSave(@NotNull Appointment entity) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_DOCTOR, MockAppRoles.ROLE_RECEPTIONIST);
    }

    @Override
    protected void beforeDelete(@NotNull Appointment entity) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_DOCTOR, MockAppRoles.ROLE_RECEPTIONIST);
    }

    @Override
    protected void beforeFindByUuid(@NotNull UUID uuid) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_DOCTOR, MockAppRoles.ROLE_RECEPTIONIST);
    }
}
