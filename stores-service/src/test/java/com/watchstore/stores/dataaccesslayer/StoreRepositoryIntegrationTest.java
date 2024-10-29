package com.watchstore.stores.dataaccesslayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class StoreRepositoryIntegrationTest {

    @Autowired
    private StoreRepository storeRepository;

    @BeforeEach
    public void setupDb() {
        storeRepository.deleteAll();

    }

    @Test
    public void whenStoreExists_ReturnStoreByStoreId(){
        //arrange
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.STORE1, "514-456-7990"));
        Store store1 = new Store(new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"),phoneNumbers, "viniv@outlook.com");

        storeRepository.save(store1);

        //act
        Store store = storeRepository .findStoreByStoreIdentifier_StoreId(store1.getStoreIdentifier().getStoreId());

        //assert
        assertNotNull(store);
        assertEquals(store.getStoreIdentifier(), store1.getStoreIdentifier());
        assertEquals(store.getAddress().getCountry(), store1.getAddress().getCountry());
        assertEquals(store.getAddress().getStreet(), store1.getAddress().getStreet());
        assertEquals(store.getAddress().getCity(), store1.getAddress().getCity());
        assertEquals(store.getEmail(), store1.getEmail());
        assertEquals(store.getPhoneNumbers().size(), store1.getPhoneNumbers().size());

    }

    @Test
    public void whenStoreDoesNotExist_ReturnNull(){
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.STORE1, "514-456-7990"));
        Store store1 = new Store(new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"),phoneNumbers, "viniv@outlook.com");

        storeRepository.save(store1);

        //act
        Store store= storeRepository.findStoreByStoreIdentifier_StoreId("e5913a79-9b1e-4516-9ffd-065780000000");

        //assert
        assertNull(store);
    }

    @Test
    public void whenStoreIdIsInvalid_ReturnNull(){
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.STORE1, "514-456-7990"));
        Store store1 = new Store(new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"),phoneNumbers, "viniv@outlook.com");

        storeRepository.save(store1);

        //act
        Store store= storeRepository.findStoreByStoreIdentifier_StoreId("e5913a79-9b1e-4516");

        //assert
        assertNull(store);

    }

    @Test
    public void whenStoreIdIsNull_ReturnNull(){
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.STORE1, "514-456-7990"));
        Store store1 = new Store(new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"),phoneNumbers, "viniv@outlook.com");

        storeRepository.save(store1);

        //act
        Store store= storeRepository.findStoreByStoreIdentifier_StoreId(null);

        //assert
        assertNull(store);

    }

    @Test
    public void whenStoreIdIsEmpty_ReturnNull(){
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.STORE1, "514-456-7990"));
        Store store1 = new Store(new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"),phoneNumbers, "viniv@outlook.com");

        storeRepository.save(store1);

        //act
        Store store= storeRepository.findStoreByStoreIdentifier_StoreId("");

        //assert
        assertNull(store);

    }

    @Test
    public void whenStoreIdIsBlank_ReturnNull(){

        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.STORE1, "514-456-7990"));
        Store store1 = new Store(new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"),phoneNumbers, "viniv@outlook.com");

        storeRepository.save(store1);

        //act
        Store store= storeRepository.findStoreByStoreIdentifier_StoreId(" ");

        //assert
        assertNull(store);
    }
}