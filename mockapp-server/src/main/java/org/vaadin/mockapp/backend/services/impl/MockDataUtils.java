package org.vaadin.mockapp.backend.services.impl;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import org.vaadin.mockapp.backend.domain.Address;

import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author petter@vaadin.com
 */
final class MockDataUtils {

    private static final Random rnd = new Random();
    private static String[] MALE_FIRST_NAMES = {
            "James", "John", "Robert", "Michael",
            "William", "David", "Richard", "Charles",
            "Joseph", "Christopher", "Rex", "Maxwell",
            "Evan", "Jake", "Elwood", "Bruce",
            "Homer", "Bart", "Jasper", "Benjamin",
            "Birchibald", "Kent", "Charles", "Carl",
            "Scott", "Eddie", "Ernst", "Tony",
            "Rod", "Barney", "Bob"};
    private static String[] FEMALE_FIRST_NAMES = {
            "Mary", "Patricia", "Linda", "Barbara",
            "Elizabeth", "Jennifer", "Maria", "Susan",
            "Margaret", "Dorothy", "Alice", "Marge",
            "Lisa", "Helen", "Maggie", "Lois",
            "Ruth", "Edna", "Rachel", "Gloria",
            "Maude", "Francesca", "Selma", "Jacqueline",
            "Scarlet", "Evelyn", "Natasha", "Elektra",
            "Selina", "Lara"};
    private static String[] LAST_NAMES = {
            "Kramer", "Smart", "Baxter", "Blues",
            "Nolan", "Wayne", "Almighty", "Simpson",
            "Gumble", "Flanders", "Soprano", "Bouvier",
            "Burns", "Carlson", "Norton", "Borton",
            "Hibbert", "Hermann", "Hoover", "Hutz",
            "Jordan", "Largo", "Meyers", "Monroe",
            "Murdock", "Murphy", "Powell", "Prince",
            "Croft", "Smith", "Johnson", "Williams",
            "Jones", "Brown", "Davis", "Miller",
            "Wilson", "Moore", "Taylor", "Anderson",
            "Thomas", "Jackson", "White"};
    private static String[] COLORS = {
            "White", "Red", "Blue", "Crimson", "Purple", "Yellow", "Black", "Cyan", "Green", "Scarlet", "Gray", "Brown"
    };
    private static String[] GREEK = {
            "Aphrodite", "Apollo", "Ares", "Artemis", "Athena", "Demeter", "Dionysus", "Hades", "Hephaestus", "Hera", "Hermes", "Hestia", "Poseidon", "Zeus"
    };
    private static String[] CITIES = {
            "Amsterdam",
            "Andorra la Vella",
            "Athens",
            "Belgrade",
            "Berlin",
            "Bern",
            "Bratislava",
            "Brussels",
            "Bucharest",
            "Budapest",
            "Chisinau",
            "Copenhagen",
            "Dublin",
            "Helsinki",
            "Kiev",
            "Lisbon",
            "Ljubljana",
            "Luxembourg",
            "London",
            "Madrid",
            "Minsk",
            "Monaco",
            "Moscow",
            "Nicosia",
            "Nuuk",
            "Oslo",
            "Paris",
            "Podgorica",
            "Prague",
            "Reykjavik",
            "Riga",
            "Rome",
            "San Marino",
            "Sarajevo",
            "Skopje",
            "Sofia",
            "Stockholm",
            "Tallinn",
            "Tirana",
            "Vaduz",
            "Valletta",
            "Vatican City",
            "Vienna",
            "Vilnius",
            "Warsaw",
            "Zagreb"
    };

    private MockDataUtils() {
    }

    public static String getRandomLastName() {
        return LAST_NAMES[rnd.nextInt(LAST_NAMES.length)];
    }

    public static String getRandomFemaleFirstNames(int count) {
        return getRandomNames(count, FEMALE_FIRST_NAMES);
    }

    public static String getRandomMaleFirstNames(int count) {
        return getRandomNames(count, MALE_FIRST_NAMES);
    }

    public static String getRandomName() {
        StringBuilder sb = new StringBuilder();
        if (rnd.nextBoolean()) {
            sb.append(getRandomMaleFirstNames(1));
        } else {
            sb.append(getRandomFemaleFirstNames(1));
        }
        sb.append(" ");
        sb.append(getRandomLastName());
        return sb.toString();
    }

    public static String getRandomPhoneNumber() {
        return String.format("%03d-%04d-%04d", rnd.nextInt(1000), rnd.nextInt(10000), rnd.nextInt(10000));
    }

    public static LocalDate getRandomDate(int minYear, int maxYear) {
        return new LocalDate(minYear + rnd.nextInt(maxYear - minYear + 1), rnd.nextInt(12) + 1, rnd.nextInt(28) + 1);
    }

    public static Address getRandomStreetAddress() {
        Address address = new Address();
        address.setStreet(getRandomStreetName() + " " + rnd.nextInt(1000) + 1);
        address.setPostalCode(String.format("%05d", rnd.nextInt(100000)));
        address.setCity(CITIES[rnd.nextInt(CITIES.length)]);
        return address;
    }

    public static String getRandomStreetName() {
        StringBuilder sb = new StringBuilder();
        switch (rnd.nextInt(4)) {
            case 0:
                sb.append(COLORS[rnd.nextInt(COLORS.length)]);
                break;
            case 1:
                sb.append(GREEK[rnd.nextInt(GREEK.length)]);
                break;
            case 2:
                sb.append("S:t ");
                sb.append(getRandomFemaleFirstNames(1));
                break;
            case 3:
                sb.append("S:t ");
                sb.append(getRandomMaleFirstNames(1));
                break;
        }
        sb.append(" ");
        if (rnd.nextBoolean()) {
            sb.append(" Road");
        } else {
            sb.append(" Street");
        }
        return sb.toString();
    }

    public static DateTime getRandomDateTime(int minYear, int maxYear) {
        try {
            return getRandomDate(minYear, maxYear).toDateTimeAtCurrentTime()
                    .withHourOfDay(rnd.nextInt(24)).withMinuteOfHour(rnd.nextInt(60))
                    .withSecondOfMinute(rnd.nextInt(60));
        } catch (IllegalFieldValueException ex) {
            return getRandomDateTime(minYear, maxYear);
        }
    }

    public static <T> T selectRandom(T[] values) {
        return values[rnd.nextInt(values.length)];
    }

    private static String getRandomNames(int count, String[] names) {
        SortedSet<String> selectedNames = new TreeSet<String>();
        while (selectedNames.size() < count) {
            selectedNames.add(names[rnd.nextInt(names.length)]);
        }
        Iterator<String> it = selectedNames.iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
