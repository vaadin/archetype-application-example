package org.vaadin.mockapp.backend.services.impl;

import org.jetbrains.annotations.NotNull;
import org.joda.time.*;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.backend.authentication.AccessDeniedException;
import org.vaadin.mockapp.backend.domain.Doctor;
import org.vaadin.mockapp.backend.domain.TimeSlot;
import org.vaadin.mockapp.backend.services.DoctorService;
import org.vaadin.mockapp.backend.services.TimeSlotService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
public class MockTimeSlotService extends MockBaseDomainService<TimeSlot> implements TimeSlotService {

    void createMockData() {
        final Random rnd = new Random();
        final LocalDate startDay = new LocalDate().minusMonths(2);
        final LocalDate endDay = new LocalDate().plusMonths(1);
        for (Doctor doctor : ((MockDoctorService) Services.get(DoctorService.class)).getEntityMap().values()) {
            for (LocalDate day = startDay; day.compareTo(endDay) <= 0; day = day.plusDays(1)) {
                int timeSlots = rnd.nextInt(26);
                DateTime starts = day.toDateTimeAtStartOfDay().withHourOfDay(8);
                for (int i = 1; i <= timeSlots; i++) {
                    if (rnd.nextBoolean()) {
                        TimeSlot timeSlot = new TimeSlot();
                        timeSlot.setCreateTimestamp(new DateTime());
                        timeSlot.setCreateUserName("root");
                        timeSlot.setUuid(UUID.randomUUID());
                        timeSlot.setDoctor(doctor);
                        timeSlot.setDuration(Duration.standardMinutes(20));
                        timeSlot.setStarts(starts);
                        doSave(timeSlot);
                    }
                    starts = starts.plus(Duration.standardMinutes(20));
                }
            }
        }
    }

    @Override
    protected void beforeSave(@NotNull TimeSlot entity) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_DOCTOR);
    }

    @Override
    protected void beforeDelete(@NotNull TimeSlot entity) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_DOCTOR);
    }

    @Override
    protected void beforeFindByUuid(@NotNull UUID uuid) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_DOCTOR, MockAppRoles.ROLE_RECEPTIONIST);
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
    public synchronized List<TimeSlot> findTimeSlotsForInterval(@NotNull Interval interval, @NotNull Doctor doctor) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_DOCTOR, MockAppRoles.ROLE_RECEPTIONIST);
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
