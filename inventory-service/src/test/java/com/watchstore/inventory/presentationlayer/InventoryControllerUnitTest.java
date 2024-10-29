package com.watchstore.inventory.presentationlayer;

import com.watchstore.inventory.businesslayer.InventoryService;
import com.watchstore.inventory.dataaccesslayer.inventory.Inventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = InventoryController.class)
class InventoryControllerUnitTest {

    @Autowired
    InventoryController inventoryController;
    @MockBean
    InventoryService inventoryService;

    @Test
    public void whenInventoriesExist_thenReturnInventories(){
        //arrange
        when(inventoryService.getAllInventories()).thenReturn(Collections.emptyList());

        //act
        ResponseEntity<List<InventoryResponseModel>> responseEntity = inventoryController.getAllInventories();

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(inventoryService, times(1)).getAllInventories();
    }

    @Test
    public void whenInventoriesExists_thenReturnInventoryById(){
        //arrange
        String inventoryId = "e5913a79-5151-5151-9ffd-06578e7a4321";

        Inventory inventory = new Inventory("Men");

        InventoryResponseModel expectedInventory = new InventoryResponseModel();
        expectedInventory.setInventoryId(inventoryId);
        expectedInventory.setType(inventory.getType());

        when(inventoryService.getInventoryById(inventoryId)).thenReturn(expectedInventory);

        //act
        ResponseEntity<InventoryResponseModel> responseEntity = inventoryController.getInventoryById(inventoryId);

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(inventoryService, times(1)).getInventoryById(inventoryId);
        InventoryResponseModel actualInventory = responseEntity.getBody();
        assertNotNull(actualInventory);
        assertEquals(expectedInventory.getInventoryId(), actualInventory.getInventoryId());
        assertEquals(expectedInventory.getType(), actualInventory.getType());



    }

    @Test
    public void whenAddingInventory_thenReturnInventory(){
        InventoryRequestModel requestModel = InventoryRequestModel.builder()
                .type("Men")
                .build();

        InventoryResponseModel expectedInventory = new InventoryResponseModel();
        expectedInventory.setInventoryId("e5913a79-5151-5151-9ffd-06578e7a4321");
        expectedInventory.setType(requestModel.getType());

        when(inventoryService.addInventory(any(InventoryRequestModel.class))).thenReturn(expectedInventory);

        // Act
        ResponseEntity<InventoryResponseModel> responseEntity = inventoryController.addInventory(requestModel);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(inventoryService, times(1)).addInventory(any(InventoryRequestModel.class));

        InventoryResponseModel actualInventory = responseEntity.getBody();
        assertNotNull(actualInventory);
        assertEquals(expectedInventory.getInventoryId(), actualInventory.getInventoryId());


    }

    
}