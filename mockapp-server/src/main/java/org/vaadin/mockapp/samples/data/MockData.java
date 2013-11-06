package org.vaadin.mockapp.samples.data;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author petter@vaadin.com
 */
public class MockData {

    private static MockData INSTANCE;
    private List<SampleMaster> masterRecords;
    private Map<UUID, SampleMaster> idToMasterRecordMap;

    private MockData() {
        Random rnd = new Random();
        masterRecords = new ArrayList<SampleMaster>();
        idToMasterRecordMap = new HashMap<UUID, SampleMaster>();
        for (int i = 0; i < 200; ++i) {
            SampleMaster master = new SampleMaster();
            master.setUuid(UUID.randomUUID());
            master.setIntegerProperty(i);
            master.setBooleanProperty(i % 2 == 0);
            master.setStringProperty("This is string number " + i);
            master.setBigDecimalProperty(new BigDecimal(i / 100.0));
            master.getEmbeddedProperty().setEnumProperty(SampleEnum.values()[i % SampleEnum.values().length]);

            int detailCount = rnd.nextInt(15) + 1;
            for (int j = 0; j < detailCount; ++j) {
                SampleDetail detail = new SampleDetail();
                detail.setIntegerProperty(rnd.nextInt());
                detail.setStringProperty("This is the child " + j + " of master " + i);
                master.getDetails().add(detail);
            }
            masterRecords.add(master);
            idToMasterRecordMap.put(master.getUuid(), master);
        }
    }

    public synchronized static MockData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MockData();
        }
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Cloneable> T nullSafeClone(T original) {
        if (original == null) {
            return null;
        }
        try {
            return (T) original.getClass().getMethod("clone").invoke(original);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized SampleMaster getMasterRecordByUuid(UUID uuid) {
        return nullSafeClone(idToMasterRecordMap.get(uuid));
    }

    public synchronized List<SampleMaster> getMasterRecords() {
        List<SampleMaster> copy = new ArrayList<SampleMaster>(masterRecords.size());
        for (SampleMaster master : masterRecords) {
            copy.add(nullSafeClone(master));
        }
        return copy;
    }
}
