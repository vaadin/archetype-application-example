package org.vaadin.mockapp.backend.services.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.backend.authentication.AccessDeniedException;
import org.vaadin.mockapp.backend.domain.EvacuationCenter;
import org.vaadin.mockapp.backend.domain.Evacuee;
import org.vaadin.mockapp.backend.domain.Gender;
import org.vaadin.mockapp.backend.services.EvacuationCenterService;
import org.vaadin.mockapp.backend.services.EvacueeService;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author petter@vaadin.com
 */
public class MockEvacueeService extends MockBaseDomainService<Evacuee> implements EvacueeService {

    private Map<EvacuationCenter, AtomicInteger> evacueeNumberMap = new HashMap<EvacuationCenter, AtomicInteger>();

    void createMockData() {
        final Random rnd = new Random();
        for (EvacuationCenter evacuationCenter : ((MockEvacuationCenterService) Services.get(EvacuationCenterService.class)).getEntityMap().values()) {
            final int evacueeCount = rnd.nextInt(500);
            for (int i = 0; i < evacueeCount; ++i) {
                Evacuee evacuee = new Evacuee();
                evacuee.setCreateTimestamp(new DateTime());
                evacuee.setCreateUserName("root");
                evacuee.setUuid(UUID.randomUUID());
                evacuee.setEvacuationCenter(evacuationCenter);
                evacuee.setFamilyName(MockDataUtils.getRandomLastName());
                if (rnd.nextBoolean()) {
                    evacuee.setGender(Gender.FEMALE);
                    evacuee.setGivenNames(MockDataUtils.getRandomFemaleFirstNames(2));
                } else {
                    evacuee.setGender(Gender.MALE);
                    evacuee.setGivenNames(MockDataUtils.getRandomMaleFirstNames(2));
                }
                evacuee.setCellPhone(MockDataUtils.getRandomPhoneNumber());
                evacuee.setHomeAddress(MockDataUtils.getRandomStreetAddress());
                if (rnd.nextBoolean()) {
                    evacuee.setEvacuationAddress(MockDataUtils.getRandomNonStreetAddress());
                }
                evacuee.setDateOfBirth(MockDataUtils.getRandomDate(1930, 2012));
                evacuee.setArrived(evacuationCenter.getOpened().plusMinutes(rnd.nextInt(86400)));
                if (evacuationCenter.isClosed() || rnd.nextBoolean()) {
                    evacuee.setLeft(evacuee.getArrived().plusMinutes(rnd.nextInt(259200)));
                }
                evacuee.setNumber(getNextNumber(evacuationCenter));
                // TODO Pets, notes
                doSave(evacuee);
            }
        }
    }

    private synchronized int getNextNumber(EvacuationCenter center) {
        AtomicInteger number = evacueeNumberMap.get(center);
        if (number == null) {
            number = new AtomicInteger(0);
            evacueeNumberMap.put(center, number);
        }
        return number.incrementAndGet();
    }

    @Override
    protected void beforeSave(@NotNull Evacuee entity) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_USER);
        if (entity.getEvacuationCenter() == null) {
            throw new IllegalArgumentException("Evacuee has no evacuation center");
        }
        if (entity.getNumber() == null) {
            entity.setNumber(getNextNumber(entity.getEvacuationCenter()));
        }
    }

    @Override
    protected void beforeDelete(@NotNull Evacuee entity) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_USER);
    }

    @Override
    protected void beforeFindByUuid(@NotNull UUID uuid) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_USER, MockAppRoles.ROLE_OBSERVER);
    }

    @NotNull
    @Override
    public synchronized List<Evacuee> findByDateOfBirth(@NotNull EvacuationCenter center, @NotNull LocalDate dateOfBirth) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_USER, MockAppRoles.ROLE_OBSERVER);
        List<Evacuee> resultList = new ArrayList<Evacuee>();
        for (Evacuee evacuee : entityMap.values()) {
            if (evacuee.getEvacuationCenter().equals(center) && dateOfBirth.equals(evacuee.getDateOfBirth())) {
                resultList.add(nullSafeClone(evacuee));
            }
        }
        return resultList;
    }

    @NotNull
    @Override
    public synchronized List<Evacuee> findByName(@NotNull EvacuationCenter center, @NotNull String familyName, @NotNull String givenNames) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_USER, MockAppRoles.ROLE_OBSERVER);
        List<Evacuee> resultList = new ArrayList<Evacuee>();
        for (Evacuee evacuee : entityMap.values()) {
            if (evacuee.getEvacuationCenter().equals(center) && evacuee.getFamilyName().contains(familyName) && evacuee.getGivenNames().contains(givenNames)) {
                resultList.add(nullSafeClone(evacuee));
            }
        }
        return resultList;
    }

    @Nullable
    @Override
    public synchronized Evacuee findByNumber(@NotNull EvacuationCenter center, int number) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_USER, MockAppRoles.ROLE_OBSERVER);
        for (Evacuee evacuee : entityMap.values()) {
            if (evacuee.getEvacuationCenter().equals(center) && new Integer(number).equals(evacuee.getNumber())) {
                return nullSafeClone(evacuee);
            }
        }
        return null;
    }
}
