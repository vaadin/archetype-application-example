package org.vaadin.mockapp.ui.views;

import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.domain.Appointment;
import org.vaadin.mockapp.backend.domain.Doctor;
import org.vaadin.mockapp.backend.domain.TimeSlot;
import org.vaadin.mockapp.backend.services.AppointmentService;
import org.vaadin.mockapp.backend.services.TimeSlotService;

import java.util.*;

/**
 * @author petter@vaadin.com
 */
public class TimeSlotEventProvider implements CalendarEventProvider, CalendarEventProvider.EventSetChangeNotifier {

    private Set<EventSetChangeListener> listenerSet = new HashSet<EventSetChangeListener>();
    private Doctor doctor;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        notifyListeners();
    }

    @Override
    public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
        if (doctor == null) {
            return Collections.emptyList();
        }
        final Interval interval = new Interval(new DateTime(startDate), new DateTime(endDate));
        final List<TimeSlot> timeSlots = getTimeSlotService().findTimeSlotsForInterval(interval, doctor);
        final List<CalendarEvent> events = new ArrayList<CalendarEvent>(timeSlots.size());
        for (TimeSlot timeSlot : timeSlots) {
            events.add(new TimeSlotEvent(timeSlot));
        }
        return events;
    }

    private TimeSlotService getTimeSlotService() {
        return Services.get(TimeSlotService.class);
    }

    @Override
    public void addEventSetChangeListener(EventSetChangeListener listener) {
        listenerSet.add(listener);
    }

    @Override
    public void removeEventSetChangeListener(EventSetChangeListener listener) {
        listenerSet.remove(listener);
    }

    private void notifyListeners() {
        EventSetChangeEvent event = new EventSetChangeEvent(this);
        for (EventSetChangeListener listener : new LinkedList<EventSetChangeListener>(listenerSet)) {
            listener.eventSetChange(event);
        }
    }

    public void refresh() {
        notifyListeners();
    }

    public static class TimeSlotEvent implements CalendarEvent {

        private final TimeSlot timeSlot;
        private final Appointment appointment;

        public TimeSlotEvent(TimeSlot timeSlot) {
            this.timeSlot = timeSlot;
            appointment = Services.get(AppointmentService.class).findByTimeSlot(timeSlot);
        }

        @Override
        public Date getStart() {
            return timeSlot.getStarts().toDate();
        }

        @Override
        public Date getEnd() {
            return timeSlot.getStarts().plus(timeSlot.getDuration()).toDate();
        }

        @Override
        public String getCaption() {
            if (appointment == null) {
                if (timeSlot.getStarts().isBeforeNow()) {
                    return "N/A";
                } else {
                    return "Free";
                }
            } else {
                return appointment.getPatient().getFullName();
            }
        }

        @Override
        public String getDescription() {
            if (appointment != null) {
                return appointment.getPatient().getDisplayName();
            } else {
                return "";
            }
        }

        @Override
        public String getStyleName() {
            if (timeSlot.getStarts().isBeforeNow()) {
                return "time-slot-past";
            }
            if (appointment == null) {
                return "time-slot-free";
            } else {
                return "time-slot-taken";
            }
        }

        @Override
        public boolean isAllDay() {
            return false;
        }
    }

}
