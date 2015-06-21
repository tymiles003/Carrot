package com.boxedfolder.service;

import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.exceptions.GeneralExceptions;
import com.boxedfolder.carrot.repository.BeaconRepository;
import com.boxedfolder.carrot.service.impl.BeaconServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class BeaconServiceImplTest {
    @Mock
    private BeaconRepository repository;
    private BeaconServiceImpl service;
    private List<Beacon> testData;

    @Before
    public void setUp() {
        service = new BeaconServiceImpl();
        service.setRepository(repository);
        testData = new ArrayList<Beacon>();

        // Create 3 different beacons
        Beacon beacon = new Beacon();
        beacon.setName("Beacon 1");
        beacon.setUuid(UUID.fromString("550e8400-e29b-11d4-a716-446655440000"));
        beacon.setMajor(1);
        beacon.setMinor(1);
        beacon.setId(1L);
        testData.add(beacon);

        beacon = new Beacon();
        beacon.setName("Beacon 2");
        testData.add(beacon);

        beacon = new Beacon();
        beacon.setName("Beacon 3");
        testData.add(beacon);
    }

    @Test
    public void testRetrieveAllBeacons() {
        when(repository.findAllByOrderByDateCreatedDesc()).thenReturn(testData);
        List<Beacon> beacons = service.findAll();
        assertEquals(beacons, testData);
    }

    @Test
    public void testRetrieveSingleBeacon() {
        when(repository.findOne(1L)).thenReturn(testData.get(0));
        Beacon beacon = service.find(1L);
        assertEquals(beacon, testData.get(0));
    }

    @Test
    public void testSaveBeacon() {
        when(repository.save(testData.get(0))).thenReturn(testData.get(0));
        when(repository.findOne(1L)).thenReturn(testData.get(0));
        Beacon beacon = service.save(testData.get(0));
        assertEquals(beacon, testData.get(0));
    }


    @Test(expected = GeneralExceptions.AlreadyExistsException.class)
    public void testSaveDuplicateBeacon() throws Exception {
        Beacon beacon = new Beacon();
        beacon.setName("Beacon 2");
        beacon.setUuid(UUID.fromString("550e8400-e29b-11d4-a716-446655440000"));
        beacon.setMajor(1);
        beacon.setMinor(1);
        beacon.setId(2L);

        when(repository.save(testData.get(0))).thenReturn(testData.get(0));
        when(repository.findFirstByUuidAndMajorAndMinor((UUID)any(), anyInt(), anyInt())).thenReturn(testData.get(0));
        service.save(beacon);
    }
}
