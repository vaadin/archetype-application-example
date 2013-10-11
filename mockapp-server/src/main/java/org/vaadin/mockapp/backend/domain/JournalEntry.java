package org.vaadin.mockapp.backend.domain;

import org.vaadin.mockapp.backend.BaseDomain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author petter@vaadin.com
 */
public class JournalEntry extends BaseDomain {

    private Patient patient;
    private Doctor doctor;
    private Appointment appointment;
    private Set<Diagnosis> diagnoses = new HashSet<Diagnosis>();
    private String text;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Set<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
