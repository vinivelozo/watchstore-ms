package com.watchstore.inventory.presentationlayer;

import com.watchstore.inventory.businesslayer.WatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



import static org.mockito.Mockito.when;

@SpringBootTest(classes = WatchController.class)
class WatchControllerUnitTest {

    private final String FOUND_WATCH_REFERENCE_NUMBER = "a1913a79-5151-5151-9ffd-06578e7a1234";

    private final String NOT_FOUND_WATCH_REFERENCE_NUMBER = "a1913a79-5151-5151-9ffd-065780000000";

    private final String INVALID_WATCH_REFERENCE_NUMBER = "05c8ab76-4f75-45c1-b6e2";

    @Autowired
    WatchController watchController;

    @MockBean
    private WatchService watchService;


    @Test
    public void whenWatchDoesNotExist_thenReturnEmptyList(){
        //arrange
        UUID inventoryId = UUID.fromString("e5913a79-5151-5151-9ffd-06578e7a4321");
        Map<String, String> queryParams = Collections.emptyMap();
        when(watchService.getAllWatches(inventoryId, queryParams)).thenReturn(Collections.emptyList());

        //act
        ResponseEntity<List<WatchResponseModel>> responseEntity = watchController.getAllWatches(inventoryId, queryParams);

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(watchService, times(1)).getAllWatches(inventoryId, queryParams);

    }




}