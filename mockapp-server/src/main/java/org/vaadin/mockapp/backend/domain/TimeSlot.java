package org.vaadin.mockapp.backend.domain;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.vaadin.mockapp.backend.BaseDomain;

/**
 * @author petter@vaadin.com
 */
public class TimeSlot extends BaseDomain {

    private Doctor doctor;
    private DateTime starts;
    private Duration duration;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public DateTime getStarts() {
        return starts;
    }

    public void setStarts(DateTime starts) {
        this.starts = starts;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
