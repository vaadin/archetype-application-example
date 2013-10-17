package org.vaadin.mockapp.backend.services.impl;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.vaadin.mockapp.backend.domain.Doctor;
import org.vaadin.mockapp.backend.domain.Gender;
import org.vaadin.mockapp.backend.domain.Patient;
import org.vaadin.mockapp.backend.services.PatientService;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
public class MockPatientService extends MockBaseDomainService<Patient> implements PatientService {

    private static String[] MALE_FIRST_NAMES = {
            "Rex", "Maxwell", "Evan", "Jake", "Elwood", "Bruce", "Homer", "Bart", "Jasper", "Benjamin",
            "Birchibald", "Kent", "Charles", "Carl", "Scott", "Eddie", "Ernst", "Tony", "Rod", "Barney", "Bob"};
    private static String[] FEMALE_FIRST_NAMES = {
            "Alice", "Marge", "Lisa", "Helen", "Maggie", "Lois", "Ruth", "Edna", "Rachel", "Gloria",
            "Maude", "Francesca", "Selma", "Jacqueline", "Scarlet", "Evelyn", "Natasha", "Elektra", "Selina", "Lara"};
    private static String[] LAST_NAMES = {
            "Kramer", "Smart", "Baxter", "Blues", "Nolan", "Wayne", "Almighty", "Simpson", "Gumble", "Flanders",
            "Soprano", "Bouvier", "Burns", "Carlson", "Norton", "Borton", "Hibbert", "Hermann", "Hoover", "Hutz",
            "Jordan", "Largo", "Meyers", "Monroe", "Murdock", "Murphy", "Powell", "Prince", "Croft"};

    void createMockData() {
        final Random rnd = new Random();
        for (int i = 0; i < 550; ++i) {
            Patient patient = new Patient();
            patient.setCreateTimestamp(new DateTime());
            patient.setCreateUserName("root");
            patient.setUuid(UUID.randomUUID());
            patient.setLastName(LAST_NAMES[rnd.nextInt(LAST_NAMES.length)]);
            if (rnd.nextBoolean()) {
                patient.setGender(Gender.FEMALE);
                patient.setFirstName(FEMALE_FIRST_NAMES[rnd.nextInt(FEMALE_FIRST_NAMES.length)]);
            } else {
                patient.setGender(Gender.MALE);
                patient.setFirstName(MALE_FIRST_NAMES[rnd.nextInt(MALE_FIRST_NAMES.length)]);
            }
            patient.setPhoneNumber(String.format("1-%03d-%04d-%04d",
                    i, rnd.nextInt(10000), rnd.nextInt(10000)));
            patient.setDateOfBirth(new LocalDate(1900 + rnd.nextInt(110), rnd.nextInt(12)+1, rnd.nextInt(28)+1));
            doSave(patient);
        }
    }

    @NotNull
    @Override
    public List<Patient> findByDateOfBirth(@NotNull LocalDate dateOfBirth) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @NotNull
    @Override
    public List<Patient> findByName(@NotNull String firstName, @NotNull String lastName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
