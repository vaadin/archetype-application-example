package org.vaadin.mockapp.backend.services.impl;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.backend.authentication.AccessDeniedException;
import org.vaadin.mockapp.backend.domain.Doctor;
import org.vaadin.mockapp.backend.services.DoctorService;

import java.util.*;

/**
 * @author petter@vaadin.com
 */
public class MockDoctorService extends MockBaseDomainService<Doctor> implements DoctorService {

    public static final String[] SPECIALITIES = {
            "Allergy and Immunology",
            "Anesthesiology",
            "Colon and Rectal Surgery",
            "Dermatology",
            "Emergency Medicine",
            "Family Medicine",
            "Internal Medicine",
            "Clinical Biochemical Genetics",
            "Clinical Cytogenetics",
            "Clinical Genetics (MD)",
            "Clinical Molecular Genetics",
            "Neurological Surgery",
            "Nuclear Medicine",
            "Obstetrics and Gynecology",
            "Ophthalmology",
            "Orthopaedic Surgery",
            "Otolaryngology",
            "Pathology - Anatomic",
            "Pathology - Clinical",
            "Pediatrics",
            "Physical Medicine and Rehabilitation",
            "Plastic Surgery",
            "Aerospace Medicine",
            "Occupational Medicine",
            "Public Health and General Preventive Medicine",
            "Psychiatry",
            "Neurology",
            "Diagnostic Radiology",
            "Interventional Radiology and Diagnostic Radiology",
            "Radiation Oncology",
            "Medical Physics",
            "Surgery",
            "Vascular Surgery",
            "Thoracic and Cardiac Surgery",
            "Urology"
    };
    private static String[] FIRST_NAMES = {"James", "John", "Robert", "Michael",
            "William", "David", "Richard", "Charles", "Joseph", "Christopher",
            "Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer",
            "Maria", "Susan", "Margaret", "Dorothy", "Lisa"};
    private static String[] LAST_NAMES = {"Smith", "Johnson", "Williams",
            "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
            "Anderson", "Thomas", "Jackson", "White"};
    private final Map<String, Doctor> codeToDoctorMap = new HashMap<String, Doctor>();

    void createMockData() {
        final Random rnd = new Random();
        for (int i = 0; i < 100; ++i) {
            Doctor doctor = new Doctor();
            doctor.setCreateTimestamp(new DateTime());
            doctor.setCreateUserName("root");
            doctor.setUuid(UUID.randomUUID());
            doctor.setSpeciality(SPECIALITIES[rnd.nextInt(SPECIALITIES.length)]);
            doctor.setFirstName(FIRST_NAMES[rnd.nextInt(FIRST_NAMES.length)]);
            doctor.setLastName(LAST_NAMES[rnd.nextInt(LAST_NAMES.length)]);
            doctor.setCode(generateUniqueCode(doctor.getFirstName(), doctor.getLastName()));
            doSave(doctor);
        }
    }

    private String generateUniqueCode(String firstName, String lastName) {
        String code;
        int firstNameCount = 1;
        int lastNameCount = 1;
        int loopCount = 0;
        do {
            code = lastName.substring(0, lastNameCount) + firstName.substring(0, firstNameCount);

            if (firstNameCount == firstName.length() && lastNameCount == lastName.length()) {
                code = code + loopCount;
            }

            if (loopCount++ % 2 == 0) {
                if (firstNameCount < firstName.length()) {
                    ++firstNameCount;
                }
            } else {
                if (lastNameCount < lastName.length()) {
                    ++lastNameCount;
                }
            }
        } while (codeToDoctorMap.containsKey(code));
        return code.toUpperCase();
    }

    @Override
    protected void beforeSave(@NotNull Doctor entity) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_ADMIN);
        Doctor existingDoctor = codeToDoctorMap.get(entity.getCode());
        if (existingDoctor != null && !entity.equals(existingDoctor)) {
            throw new IllegalArgumentException("The doctor has a code that is already taken");
        }
    }

    @Override
    protected void beforeDelete(@NotNull Doctor entity) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_ADMIN);
    }

    @Override
    protected void beforeFindByUuid(@NotNull UUID uuid) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ALL_ROLES);
    }

    @Override
    protected void doSave(@NotNull Doctor entity) {
        Doctor oldDoctor = entityMap.get(entity.getUuid());
        if (oldDoctor != null) {
            codeToDoctorMap.remove(oldDoctor.getCode());
        }
        super.doSave(entity);
        codeToDoctorMap.put(entity.getCode(), entity);
    }

    @Override
    protected void doDelete(@NotNull Doctor entity) {
        super.doDelete(entity);
        codeToDoctorMap.remove(entity.getCode());
    }

    @Override
    public synchronized Doctor findByCode(@NotNull String code) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ALL_ROLES);
        return nullSafeClone(codeToDoctorMap.get(code));
    }

    @NotNull
    @Override
    public synchronized List<Doctor> findAll() {
        AccessDeniedException.requireAnyOf(MockAppRoles.ALL_ROLES);
        List<Doctor> doctors = new ArrayList<Doctor>();
        for (Doctor doctor : entityMap.values()) {
            doctors.add(nullSafeClone(doctor));
        }
        Collections.sort(doctors, new Comparator<Doctor>() {
            @Override
            public int compare(Doctor o1, Doctor o2) {
                int o = o1.getLastName().compareTo(o2.getLastName());
                if (o == 0) {
                    o = o1.getFirstName().compareTo(o2.getFirstName());
                }
                return o;
            }
        });

        return doctors;
    }


}
