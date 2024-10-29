package com.watchstore.inventory.dataaccesslayer.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class InventoryRepositoryIntegrationTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @BeforeEach
    public void setupDb() {
        inventoryRepository.deleteAll();
    }

    @Test
    public void whenInventoryExists_ReturnInventoryByInventoryId(){
        //arrange
        Inventory inventory1 = new Inventory("Men");

        inventoryRepository.save(inventory1);

        //act
        Inventory inventory = inventoryRepository.findInventoryByInventoryIdentifier_InventoryId(inventory1.getInventoryIdentifier().getInventoryId());

        //assert
        assertNotNull(inventory);
        assertEquals(inventory.getInventoryIdentifier(), inventory1.getInventoryIdentifier());
        assertEquals(inventory.getInventoryIdentifier(), inventory1.getInventoryIdentifier());

    }

    @Test
    public void whenInventoryDoesNotExist_ReturnNull(){
        //arrange
        Inventory inventory1 = new Inventory("Men");
        inventoryRepository.save(inventory1);

        //act
        Inventory inventory = inventoryRepository.findInventoryByInventoryIdentifier_InventoryId("e5913a79-5151-5151-9ffd-06578e7a4321");

        //assert
        assertNull(inventory);

    }

    @Test
    public void whenInventoryIdIsInvalid_ReturnNull(){
        //arrange
        Inventory inventory1 = new Inventory("Men");
        inventoryRepository.save(inventory1);

        //act
        Inventory inventory = inventoryRepository.findInventoryByInventoryIdentifier_InventoryId("e5913a79-5151-5151");

        //assert
        assertNull(inventory);
    }

    @Test
    public void whenInventoryIdIsNull_ReturnNull(){
        //arrange
        Inventory inventory1 = new Inventory("Men");
        inventoryRepository.save(inventory1);

        //act
        Inventory inventory = inventoryRepository.findInventoryByInventoryIdentifier_InventoryId(null);

        //assert
        assertNull(inventory);
    }

    @Test
    public void whenInventoryIdIsEmpty_ReturnNull(){
        //arrange
        Inventory inventory1 = new Inventory("Men");
        inventoryRepository.save(inventory1);

        //act
        Inventory inventory = inventoryRepository.findInventoryByInventoryIdentifier_InventoryId("");

        //assert
        assertNull(inventory);
    }

    @Test
    public void whenInventoryIdIsBlank_ReturnNull(){
        //arrange
        Inventory inventory1 = new Inventory("Men");
        inventoryRepository.save(inventory1);

        //act
        Inventory inventory = inventoryRepository.findInventoryByInventoryIdentifier_InventoryId(" ");

        //assert
        assertNull(inventory);
    }
}