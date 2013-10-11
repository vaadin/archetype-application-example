package org.vaadin.mockapp.backend.domain;

import org.vaadin.mockapp.backend.BaseDomain;

/**
 * @author petter@vaadin.com
 */
public class Appointment extends BaseDomain {

    private Patient patient;
    private TimeSlot timeSlot;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
}
