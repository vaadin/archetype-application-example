package org.vaadin.mockapp.backend.services.impl;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.vaadin.mockapp.backend.MockAppRoles;
import org.vaadin.mockapp.backend.authentication.AccessDeniedException;
import org.vaadin.mockapp.backend.domain.EvacuationCenter;
import org.vaadin.mockapp.backend.services.EvacuationCenterService;

import java.util.*;

/**
 * @author petter@vaadin.com
 */
public class MockEvacuationCenterService extends MockBaseDomainService<EvacuationCenter> implements EvacuationCenterService {


    void createMockData() {
        for (int i = 0; i < 50; ++i) {
            EvacuationCenter evacCenter = new EvacuationCenter();
            evacCenter.setCreateTimestamp(new DateTime());
            evacCenter.setCreateUserName("root");
            evacCenter.setUuid(UUID.randomUUID());
            evacCenter.setName(MockDataUtils.getRandomWorkingName());
            evacCenter.setAddress(MockDataUtils.getRandomStreetAddress());
            evacCenter.setOpenDate(MockDataUtils.getRandomDateTime(2009, 2012));
            if (Math.random() > 0.5) {
                evacCenter.setCloseDate(evacCenter.getOpenDate().plusDays(5));
            }
            doSave(evacCenter);
        }
    }

    @Override
    protected void beforeSave(@NotNull EvacuationCenter entity) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_ADMIN);
    }

    @Override
    protected void beforeDelete(@NotNull EvacuationCenter entity) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ROLE_ADMIN);
    }

    @Override
    protected void beforeFindByUuid(@NotNull UUID uuid) {
        AccessDeniedException.requireAnyOf(MockAppRoles.ALL_ROLES);
    }

    @NotNull
    @Override
    public synchronized List<EvacuationCenter> findAll() {
        AccessDeniedException.requireAnyOf(MockAppRoles.ALL_ROLES);
        List<EvacuationCenter> centers = new ArrayList<EvacuationCenter>();
        for (EvacuationCenter center : entityMap.values()) {
            centers.add(nullSafeClone(center));
        }
        Collections.sort(centers, new Comparator<EvacuationCenter>() {
            @Override
            public int compare(EvacuationCenter o1, EvacuationCenter o2) {
                int o = o1.getOpenDate().compareTo(o2.getOpenDate());
                if (o == 0) {
                    o = o1.getName().compareTo(o2.getName());
                }
                return o;
            }
        });

        return centers;
    }


}
