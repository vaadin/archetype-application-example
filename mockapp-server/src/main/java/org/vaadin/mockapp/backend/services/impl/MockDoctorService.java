package org.vaadin.mockapp.backend.services.impl;

import org.jetbrains.annotations.NotNull;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.authentication.AccessDeniedException;
import org.vaadin.mockapp.backend.authentication.Roles;
import org.vaadin.mockapp.backend.domain.Doctor;
import org.vaadin.mockapp.backend.services.DoctorService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.*;

/**
 * @author petter@vaadin.com
 */
@WebListener
public class MockDoctorService extends MockBaseDomainService<Doctor> implements DoctorService, ServletContextListener {

    private final Map<String, Doctor> codeToDoctorMap = new HashMap<String, Doctor>();

    @Override
    protected void beforeSave(@NotNull Doctor entity) {
        AccessDeniedException.requireAnyOf(Roles.ROLE_ADMIN);
        Doctor existingDoctor = codeToDoctorMap.get(entity.getCode());
        if (existingDoctor != null && !entity.equals(existingDoctor)) {
            throw new IllegalArgumentException("The doctor has a code that is already taken");
        }
    }

    @Override
    protected void beforeDelete(@NotNull Doctor entity) {
        AccessDeniedException.requireAnyOf(Roles.ROLE_ADMIN);
    }

    @Override
    protected void beforeFindByUuid(@NotNull UUID uuid) {
        AccessDeniedException.requireAnyOf(Roles.ALL_ROLES);
    }

    @Override
    protected void doSave(@NotNull Doctor entity) {
        super.doSave(entity);
        codeToDoctorMap.put(entity.getCode(), entity);
    }

    @Override
    public synchronized Doctor findByCode(@NotNull String code) {
        AccessDeniedException.requireAnyOf(Roles.ALL_ROLES);
        return nullSafeClone(codeToDoctorMap.get(code));
    }

    @NotNull
    @Override
    public synchronized List<Doctor> findAll(boolean includeDeleted) {
        AccessDeniedException.requireAnyOf(Roles.ALL_ROLES);
        List<Doctor> doctors = new ArrayList<Doctor>();
        for (Doctor doctor : entityMap.values()) {
            if (includeDeleted || !doctor.isDeleted()) {
                doctors.add(nullSafeClone(doctor));
            }
        }
        Collections.sort(doctors, new Comparator<Doctor>() {
            @Override
            public int compare(Doctor o1, Doctor o2) {
                int o = o1.getLastName().compareTo(o2.getLastName());
                if (o == 0) {
                    return o1.getFirstName().compareTo(o2.getFirstName());
                } else {
                    return o;
                }
            }
        });

        return doctors;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Services.register(this, DoctorService.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Services.remove(DoctorService.class);
    }
}
