package org.vaadin.mockapp.ui.views;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarComponentEvents;
import org.joda.time.*;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.backend.authentication.AuthenticationHolder;
import org.vaadin.mockapp.backend.domain.Doctor;
import org.vaadin.mockapp.backend.services.DoctorService;
import org.vaadin.mockapp.backend.services.TimeSlotService;
import org.vaadin.mockapp.ui.theme.MockAppTheme;
import org.vaadin.peter.buttongroup.ButtonGroup;

import java.util.*;

/**
 * @author petter@vaadin.com
 */
@ViewDefinition(name = CalendarView.VIEW_NAME,
        caption = "Calendar",
        iconThemeResource = "icons/CalendarView.png",
        order = 90,
        allowedRoles = {MockAppRoles.ROLE_DOCTOR, MockAppRoles.ROLE_RECEPTIONIST})
public class CalendarView extends VerticalLayout implements View,
        CalendarComponentEvents.EventClickHandler,
        CalendarComponentEvents.EventMoveHandler,
        CalendarComponentEvents.EventResizeHandler {

    public static final String VIEW_NAME = "calendar";
    private Label title;
    private ComboBox doctor;
    private Calendar calendar;
    private Button today;
    private Label visibleTimePeriod;
    private ButtonGroup navigationButtons;
    private ButtonGroup calendarViewButtons;
    private Button refresh;
    private Button addTimeSlots;
    private BeanItemContainer<Doctor> doctorContainer;
    private TimeSlotEventProvider timeSlotsProvider;

    public CalendarView() {
        init();
    }

    private void init() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        title = new Label("Calendar");
        title.addStyleName(MockAppTheme.LABEL_H1);
        addComponent(title);

        final HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setSpacing(true);
        addComponent(header);

        doctor = new ComboBox();
        doctor.setInputPrompt("Select the doctor whose time slots should be shown");
        doctor.setDescription(doctor.getInputPrompt());
        doctor.setWidth(30, Unit.EM);
        doctor.setImmediate(true);
        doctor.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                timeSlotsProvider.setDoctor((Doctor) event.getProperty().getValue());
            }
        });
        doctor.setContainerDataSource(doctorContainer = new BeanItemContainer<Doctor>(Doctor.class));
        doctor.setItemCaptionPropertyId("displayName");
        doctor.setFilteringMode(FilteringMode.CONTAINS);

        header.addComponent(doctor);
        header.setComponentAlignment(doctor, Alignment.MIDDLE_LEFT);

        visibleTimePeriod = new Label();
        visibleTimePeriod.addStyleName(MockAppTheme.LABEL_H2);
        visibleTimePeriod.setSizeUndefined();
        header.addComponent(visibleTimePeriod);
        header.setExpandRatio(visibleTimePeriod, 1f);
        header.setComponentAlignment(visibleTimePeriod, Alignment.MIDDLE_LEFT);

        navigationButtons = new ButtonGroup();
        header.addComponent(navigationButtons);
        header.setComponentAlignment(navigationButtons, Alignment.MIDDLE_RIGHT);

        navigationButtons.addButton(new Button("<", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                backward();
            }
        }));
        navigationButtons.addButton(new Button(">", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                forward();
            }
        }));

        today = new Button("Today", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                today();
            }
        });
        header.addComponent(today);
        header.setComponentAlignment(today, Alignment.MIDDLE_RIGHT);

        calendarViewButtons = new ButtonGroup();
        header.addComponent(calendarViewButtons);
        header.setComponentAlignment(calendarViewButtons, Alignment.MIDDLE_RIGHT);

        calendarViewButtons.addButton(new Button("Day", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                dayView();
            }
        }));
        calendarViewButtons.addButton(new Button("Week", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                weekView();
            }
        }));
        calendarViewButtons.addButton(new Button("Month", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                monthView();
            }
        }));

        calendar = new Calendar();
        calendar.setLocale(Locale.UK);
        calendar.addStyleName("time-slots");
        calendar.setSizeFull();
        calendar.setEventProvider(timeSlotsProvider = new TimeSlotEventProvider());
        calendar.setFirstVisibleHourOfDay(7);
        calendar.setLastVisibleHourOfDay(17);
        calendar.setTimeFormat(Calendar.TimeFormat.Format24H);
        calendar.setHandler((CalendarComponentEvents.EventClickHandler) this);
        calendar.setHandler((CalendarComponentEvents.EventMoveHandler) this);
        calendar.setHandler((CalendarComponentEvents.EventResizeHandler) this);
        calendar.setWeeklyCaptionFormat("d/M");
        addComponent(calendar);
        setExpandRatio(calendar, 1f);

        final HorizontalLayout footer = new HorizontalLayout();
        footer.setWidth("100%");
        footer.setSpacing(true);
        addComponent(footer);

        refresh = new Button("Refresh", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                refresh();
            }
        });
        refresh.setDisableOnClick(true);
        refresh.addStyleName(MockAppTheme.BUTTON_DEFAULT_NO_MODIFICATIONS);
        footer.addComponent(refresh);
        footer.setExpandRatio(refresh, 1f);

        if (AuthenticationHolder.getAuthentication().hasRole(MockAppRoles.ROLE_DOCTOR)) {
            addTimeSlots = new Button("Add Time Slots", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Notification.show("This feature is not implemented in this project stub :-)");
                }
            });
            addTimeSlots.addStyleName(MockAppTheme.BUTTON_DEFAULT_MODIFY);

            footer.addComponent(addTimeSlots);
            footer.setComponentAlignment(addTimeSlots, Alignment.MIDDLE_RIGHT);
        }

        weekView();
    }

    private DoctorService getDoctorService() {
        return Services.get(DoctorService.class);
    }

    private TimeSlotService getTimeSlotService() {
        return Services.get(TimeSlotService.class);
    }

    private void refresh() {
        timeSlotsProvider.refresh();
        refresh.setEnabled(true);
    }

    private void today() {
        Date now = new Date();
        calendar.setStartDate(now);
        calendar.setEndDate(now);
        updateVisibleTimePeriodLabel();
    }

    private void dayView() {
        if (isTodayVisible()) {
            today();
        } else {
            calendar.setEndDate(calendar.getStartDate());
        }
        updateVisibleTimePeriodLabel();
    }

    private void weekView() {
        LocalDate date;
        if (isTodayVisible()) {
            date = new LocalDate();
        } else {
            date = new LocalDate(calendar.getStartDate());
        }
        int daysFromDateToStartOfWeek = date.getDayOfWeek() - 1;
        LocalDate start = date.minusDays(daysFromDateToStartOfWeek);
        LocalDate end = date.plusDays(6 - daysFromDateToStartOfWeek);
        calendar.setStartDate(start.toDate());
        calendar.setEndDate(end.toDate());
        updateVisibleTimePeriodLabel();
    }

    private boolean isTodayVisible() {
        Interval interval = new Interval(new DateTime(calendar.getStartDate()), new DateTime(calendar.getEndDate()));
        return interval.containsNow();
    }

    private void monthView() {
        Interval yearMonth = new YearMonth(calendar.getStartDate()).toInterval();
        calendar.setStartDate(yearMonth.getStart().toDate());
        calendar.setEndDate(yearMonth.getEnd().minusHours(1).toDate()); // The end of an interval is exclusive, i.e. the beginning of the next month. Therefore, we need to remove some time to get the end date back to the actual month.
        updateVisibleTimePeriodLabel();
    }

    private void updateVisibleTimePeriodLabel() {
        LocalDate start =  new LocalDate(calendar.getStartDate());
        LocalDate end = new LocalDate(calendar.getEndDate());
        if (start.isEqual(end)) {
            visibleTimePeriod.setValue(start.toString("MMM d, yyyy"));
        } else if (start.getYear() == end.getYear()) {
            if (start.getMonthOfYear() == end.getMonthOfYear()) {
                visibleTimePeriod.setValue(String.format("%s %d - %d, %d",
                        start.toString("MMM"), start.getDayOfMonth(), end.getDayOfMonth(), start.getYear()));
            } else {
                visibleTimePeriod.setValue(String.format("%s - %s, %d",
                        start.toString("MMM d"), end.toString("MMM d"), start.getYear()));
            }
        } else {
            visibleTimePeriod.setValue(String.format("%s - %s",
                    start.toString("MMM d, yyyy"), end.toString("MMM d, yyyy")));
        }
    }

    private void forward() {
        navigate(1);
    }

    private void navigate(int offset) {
        LocalDate start =  new LocalDate(calendar.getStartDate());
        LocalDate end = new LocalDate(calendar.getEndDate());
        if (calendar.isMonthlyMode()) {
            calendar.setStartDate(start.plusMonths(offset).toDate());
            calendar.setEndDate(end.plusMonths(offset).toDate());
        } else if (start.isEqual(end)) {
            calendar.setStartDate(start.plusDays(offset).toDate());
            calendar.setEndDate(calendar.getStartDate());
        } else {
            calendar.setStartDate(start.plusWeeks(offset).toDate());
            calendar.setEndDate(end.plusWeeks(offset).toDate());
        }
        updateVisibleTimePeriodLabel();
    }

    private void backward() {
        navigate(-1);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        doctorContainer.addAll(getDoctorService().findAll());
    }

    @Override
    public void eventClick(CalendarComponentEvents.EventClick event) {
        Notification.show("This feature is not implemented in this project stub :-)");
    }

    @Override
    public void eventMove(CalendarComponentEvents.MoveEvent event) {
        Notification.show("This feature is not implemented in this project stub :-)");
    }

    @Override
    public void eventResize(CalendarComponentEvents.EventResize event) {
        Notification.show("This feature is not implemented in this project stub :-)");
    }
}
