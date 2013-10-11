package org.vaadin.mockapp.backend.services;

import org.jetbrains.annotations.NotNull;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.vaadin.mockapp.backend.BaseDomainService;
import org.vaadin.mockapp.backend.domain.Doctor;
import org.vaadin.mockapp.backend.domain.TimeSlot;

import java.util.List;

/**
 * @author petter@vaadin.com
 */
public interface TimeSlotService extends BaseDomainService.WritableDomainService<TimeSlot>, BaseDomainService.DeletableDomainService<TimeSlot> {

    @NotNull
    List<TimeSlot> findTimeSlotsForMonth(@NotNull YearMonth yearMonth, @NotNull Doctor doctor);

    @NotNull
    List<TimeSlot> findTimeSlotsForDay(@NotNull LocalDate day, @NotNull Doctor doctor);

    @NotNull
    List<TimeSlot> findTimeSlotsForInterval(@NotNull Interval interval, @NotNull Doctor doctor);

}
