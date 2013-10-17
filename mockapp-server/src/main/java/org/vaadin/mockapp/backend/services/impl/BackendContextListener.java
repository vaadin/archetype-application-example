package org.vaadin.mockapp.backend.services.impl;

import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.services.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author petter@vaadin.com
 */
@WebListener
public class BackendContextListener implements ServletContextListener {

    private MockLoginService loginService;
    private MockDoctorService doctorService;
    private MockTimeSlotService timeSlotService;
    private MockPatientService patientService;
    private MockAppointmentService appointmentService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        loginService = new MockLoginService();
        Services.register(loginService, LoginService.class);

        doctorService = new MockDoctorService();
        Services.register(doctorService, DoctorService.class);
        doctorService.createMockData();

        timeSlotService = new MockTimeSlotService();
        Services.register(timeSlotService, TimeSlotService.class);
        timeSlotService.createMockData();

        patientService = new MockPatientService();
        Services.register(patientService, PatientService.class);
        patientService.createMockData();

        appointmentService = new MockAppointmentService();
        Services.register(appointmentService, AppointmentService.class);
        appointmentService.createMockData();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Services.remove(AppointmentService.class);
        Services.remove(PatientService.class);
        Services.remove(TimeSlotService.class);
        Services.remove(DoctorService.class);
        Services.remove(LoginService.class);
    }
}
