package org.vaadin.mockapp.backend.services.impl;

import org.jetbrains.annotations.NotNull;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.authentication.AccessDeniedException;
import org.vaadin.mockapp.backend.authentication.Roles;
import org.vaadin.mockapp.backend.domain.Doctor;
import org.vaadin.mockapp.backend.domain.TimeSlot;
import org.vaadin.mockapp.backend.services.TimeSlotService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
public class MockTimeSlotService extends MockBaseDomainService<TimeSlot> implements TimeSlotService, ServletContextListener {

    @Override
    protected void beforeSave(@NotNull TimeSlot entity) {
        AccessDeniedException.requireAnyOf(Roles.ROLE_DOCTOR, Roles.ROLE_ADMIN);
    }

    @Override
    protected void beforeDelete(@NotNull TimeSlot entity) {
        AccessDeniedException.requireAnyOf(Roles.ROLE_DOCTOR, Roles.ROLE_ADMIN);
    }

    @Override
    protected void beforeFindByUuid(@NotNull UUID uuid) {
        AccessDeniedException.requireAnyOf(Roles.ALL_ROLES);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Services.register(this, TimeSlotService.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Services.remove(TimeSlotService.class);
    }

    @NotNull
    @Override
    public List<TimeSlot> findTimeSlotsForMonth(@NotNull YearMonth yearMonth, @NotNull Doctor doctor) {
        return findTimeSlotsForInterval(yearMonth.toInterval(), doctor);
    }

    @NotNull
    @Override
    public List<TimeSlot> findTimeSlotsForDay(@NotNull LocalDate day, @NotNull Doctor doctor) {
        return findTimeSlotsForInterval(day.toInterval(), doctor);
    }

    @NotNull
    @Override
    public List<TimeSlot> findTimeSlotsForInterval(@NotNull Interval interval, @NotNull Doctor doctor) {
        AccessDeniedException.requireAnyOf(Roles.ALL_ROLES);
        List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
        for (TimeSlot timeSlot : entityMap.values()) {
            if (timeSlot.getDoctor().equals(doctor)) {
                if (interval.contains(timeSlot.getStarts())) {
                    timeSlots.add(nullSafeClone(timeSlot));
                }
            }
        }
        return timeSlots;
    }
}
