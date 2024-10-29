package com.watchstore.inventory.dataaccesslayer.watch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WatchRepositoryIntegrationTest {
    @Autowired
    private WatchRepository watchRepository;

    @BeforeEach
    public void setupDb() {
        watchRepository.deleteAll();
    }


    @Test
    public void whenWatchExists_ReturnWatchByReferenceNumber(){
        //arrange
        List<Feature> features = Arrays.asList(new Feature("Gold", 44, "Solar", 60000.0));
        Watch watch1 = new Watch(features, "Rolex","Submariner","Gold", 2021 ,Status.AVAILABLE);

        watchRepository.save(watch1);

        //act
        Watch watch = watchRepository.findWatchByWatchIdentifier_ReferenceNumber(watch1.getWatchIdentifier().getReferenceNumber());

        //assert

        assertEquals(watch.getWatchIdentifier(), watch1.getWatchIdentifier());
        assertEquals(watch.getBrand(), watch1.getBrand());
        assertEquals(watch.getColor(), watch1.getColor());
    }

    @Test
    public void whenWatchDoesNotExist_ReturnNull(){
        //arrange
        List<Feature> features = Arrays.asList(new Feature("Gold", 44, "Solar", 60000.0));
        Watch watch1 = new Watch(features, "Rolex","Submariner","Gold", 2021 ,Status.AVAILABLE);

        watchRepository.save(watch1);

        //act

        Watch watch = watchRepository.findWatchByWatchIdentifier_ReferenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234");

        assertNull(watch);
    }

    @Test
    public void whenWatchIsInvalid_ReturnNull(){
        //arrange
        List<Feature> features = Arrays.asList(new Feature("Gold", 44, "Solar", 60000.0));
        Watch watch1 = new Watch(features, "Rolex","Submariner","Gold", 2021 ,Status.AVAILABLE);

        watchRepository.save(watch1);

        //act
         Watch watch = watchRepository.findWatchByWatchIdentifier_ReferenceNumber("a1913a79-5151-5151");

         //assert
        assertNull(watch);

    }

    @Test
    public void whenWatchIsNull_ReturnNull(){
        //arrange
        List<Feature> features = Arrays.asList(new Feature("Gold", 44, "Solar", 60000.0));
        Watch watch1 = new Watch(features, "Rolex","Submariner","Gold", 2021 ,Status.AVAILABLE);

        watchRepository.save(watch1);

        //act
        Watch watch = watchRepository.findWatchByWatchIdentifier_ReferenceNumber(null);

        //assert
        assertNull(watch);
    }

    @Test
    public void whenReferenceNumberIsEmpty_ReturnNull(){
        //arrange
        List<Feature> features = Arrays.asList(new Feature("Gold", 44, "Solar", 60000.0));
        Watch watch1 = new Watch(features, "Rolex","Submariner","Gold", 2021 ,Status.AVAILABLE);

        watchRepository.save(watch1);

        //act
        Watch watch = watchRepository.findWatchByWatchIdentifier_ReferenceNumber("");

        //assert
        assertNull(watch);
    }

    @Test
    public void whenReferenceNumberIsBlank_ReturnNull(){
        //arrange
        List<Feature> features = Arrays.asList(new Feature("Gold", 44, "Solar", 60000.0));
        Watch watch1 = new Watch(features, "Rolex","Submariner","Gold", 2021 ,Status.AVAILABLE);

        watchRepository.save(watch1);

        //act
        Watch watch = watchRepository.findWatchByWatchIdentifier_ReferenceNumber(" ");

        //assert
        assertNull(watch);
    }
}