package com.watchstore.sales.businesslayer;

import com.watchstore.sales.dataaccesslayer.Sale;
import com.watchstore.sales.dataaccesslayer.SaleIdentifier;
import com.watchstore.sales.dataaccesslayer.SaleRepository;
import com.watchstore.sales.dataaccesslayer.SaleStatus;
import com.watchstore.sales.domainclientlayer.employees.EmployeeModel;
import com.watchstore.sales.domainclientlayer.employees.EmployeesServiceClient;
import com.watchstore.sales.domainclientlayer.inventory.InventoryModel;
import com.watchstore.sales.domainclientlayer.inventory.InventoryServiceClient;
import com.watchstore.sales.domainclientlayer.stores.StoreModel;
import com.watchstore.sales.domainclientlayer.stores.StoresServiceClient;
import com.watchstore.sales.domainclientlayer.watch.WatchModel;
import com.watchstore.sales.mapperlayer.SaleResponseMapper;
import com.watchstore.sales.presentationlayer.SaleRequestModel;
import com.watchstore.sales.presentationlayer.SaleResponseModel;
import com.watchstore.sales.utils.exceptions.InvalidInputException;
import com.watchstore.sales.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration")
@ActiveProfiles("test")
class SaleServiceUnitTest {

    @Autowired
    SaleService saleService;

    @MockBean
    EmployeesServiceClient employeesServiceClient;

    @MockBean
    InventoryServiceClient inventoryServiceClient;

    @MockBean
    StoresServiceClient storesServiceClient;

    @MockBean
    SaleRepository saleRepository;

    @SpyBean
    SaleResponseMapper saleResponseMapper;

    @Test
    public void whenValidEmployeeId_InventoryId_StoreId_thenProcessRequest(){

        //arrange
        var inventoryModel = InventoryModel.builder()

                ///.model("Submariner")
                //.brand("Rolex")
                // .color("Gold")
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var inventoryModelStatusUpdate = InventoryModel.builder()

                ///.model("Submariner")
                //.brand("Rolex")
                // .color("Gold")
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var watchModel = WatchModel.builder()
                .referenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234")
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var storeModel = StoreModel.builder()
                .storeId("e5913a79-9b1e-4516-9ffd-06578e7af261") //regarder dans le datasql file et mettre les donnes ici. Import lles donnees egalement
                .build();

        var employeeModel = EmployeeModel.builder()
                .employeeId("e5913a79-9b1e-4516-9ffd-06578e7af201")
                //.firstName("Vilma")
                //  .lastName("Chawner")
                .build();

        var saleRequestModel = SaleRequestModel.builder()
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .employeeId("e5913a79-9b1e-4516-9ffd-06578e7af201")
                .storeId("e5913a79-9b1e-4516-9ffd-06578e7af261")
                .referenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234")
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        var saleIdentifier1 = new SaleIdentifier();

        var sale1 = Sale.builder()
                .saleIdentifier(saleIdentifier1)
                .inventoryModel(inventoryModel)
                .storeModel(storeModel)
                .employeeModel(employeeModel)
                .watchModel(watchModel)
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        var updateSale = Sale.builder()
                .saleIdentifier(saleIdentifier1)
                .inventoryModel(inventoryModel)
                .storeModel(storeModel)
                .employeeModel(employeeModel)
                .watchModel(watchModel)
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        when(employeesServiceClient.getEmployeeByEmployeeId(employeeModel.getEmployeeId())).thenReturn(employeeModel);
        when(inventoryServiceClient.getInventoryByInventoryId(inventoryModel.getInventoryId())).thenReturn(inventoryModel);
        when(storesServiceClient.getStoreByStoreId(storeModel.getStoreId())).thenReturn(storeModel);
        when(inventoryServiceClient.getWatchByReferenceNumberAndInventoryId(any(),any())).thenReturn(watchModel);
        when(saleRepository.save(any(Sale.class))).thenReturn(sale1, updateSale);

        //act
        SaleResponseModel saleResponseModel = saleService.addEmployeePurchase(saleRequestModel, employeeModel.getEmployeeId());

        //assert
        assertNotNull(saleResponseModel);
        assertNotNull(saleResponseModel.getSaleId());
        assertEquals(inventoryModelStatusUpdate.getInventoryId(), saleResponseModel.getInventoryId());
        assertEquals(employeeModel.getEmployeeId(), saleResponseModel.getEmployeeId());
        assertEquals(storeModel.getStoreId(), saleResponseModel.getStoreId());
        assertEquals(inventoryModel.getInventoryId(), saleResponseModel.getInventoryId());

        verify(saleResponseMapper, times(1)).entityToResponseModel(updateSale);
    }

    @Test
    public void whenValidMemberId_EmployeeId_StoreId_thenProcessGetAll(){
        //arrange
        var inventoryModel = InventoryModel.builder()
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var inventoryModelStatusUpdate = InventoryModel.builder()

                ///.model("Submariner")
                //.brand("Rolex")
                // .color("Gold")
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var watchModel = WatchModel.builder()
                .referenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234")
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var storeModel = StoreModel.builder()
                .storeId("e5913a79-9b1e-4516-9ffd-06578e7af261") //regarder dans le datasql file et mettre les donnes ici. Import lles donnees egalement
                .build();

        var employeeModel = EmployeeModel.builder()
                .employeeId("e5913a79-9b1e-4516-9ffd-06578e7af201")
                //.firstName("Vilma")
                //  .lastName("Chawner")
                .build();

        var saleRequestModel = SaleRequestModel.builder()
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .employeeId("e5913a79-9b1e-4516-9ffd-06578e7af201")
                .storeId("e5913a79-9b1e-4516-9ffd-06578e7af261")
                .referenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234")
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        var saleIdentifier1 = new SaleIdentifier();

        var sale1 = Sale.builder()
                .saleIdentifier(saleIdentifier1)
                .inventoryModel(inventoryModel)
                .storeModel(storeModel)
                .employeeModel(employeeModel)
                .watchModel(watchModel)
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        var updateSale = Sale.builder()
                .saleIdentifier(saleIdentifier1)
                .inventoryModel(inventoryModel)
                .storeModel(storeModel)
                .employeeModel(employeeModel)
                .watchModel(watchModel)
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        when(employeesServiceClient.getEmployeeByEmployeeId(employeeModel.getEmployeeId())).thenReturn(employeeModel);
        when(inventoryServiceClient.getInventoryByInventoryId(inventoryModel.getInventoryId())).thenReturn(inventoryModel);
        when(storesServiceClient.getStoreByStoreId(storeModel.getStoreId())).thenReturn(storeModel);
        when(saleRepository.save(any(Sale.class))).thenReturn(sale1, updateSale);

        //act
        List<SaleResponseModel> saleResponseModel = saleService.getAllPurchaseForEmployee(employeeModel.getEmployeeId());


        //assert
        assertNotNull(saleResponseModel);

        verify(saleResponseMapper, times(1)).entityListToResponseModelList(anyList());

        for (SaleResponseModel responseModel : saleResponseModel) {
            assertNotNull(responseModel);
            assertNotNull(responseModel.getEmployeeId());
            assertNotNull(responseModel.getSaleId());
        }
    }

    @Test
    void deletePurchaseFromEmployee_ValidEmployeeIdAndSaleId_DeleteSuccessfully() {
        // Arrange
        String employeeId = "e5913a79-9b1e-4516-9ffd-06578e7af201";
        String saleId = "11";
        EmployeeModel employeeModel = new EmployeeModel();
        Sale sale = new Sale();
        when(employeesServiceClient.getEmployeeByEmployeeId(employeeId)).thenReturn(employeeModel);
        when(saleRepository.findSaleBySaleIdentifier_SaleId(saleId)).thenReturn(sale);

        // Act
        saleService.deletePurchaseFromEmployee(employeeId, saleId);

        // Assert
        verify(saleRepository, times(1)).delete(sale);
    }

    @Test
    void deletePurchaseFromEmployee_InvalidEmployeeId_ThrowsNotFoundException() {
        // Arrange
        String employeeId = "e5913a79-9b1e-4516-9ffd-06578e7a0000";
        String saleId = "11";
        when(employeesServiceClient.getEmployeeByEmployeeId(employeeId)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> saleService.deletePurchaseFromEmployee(employeeId, saleId));
        verify(saleRepository, never()).delete(any());
    }

    @Test
    void deletePurchaseFromEmployee_UnknownSaleId_ThrowsNotFoundException() {
        // Arrange
        String employeeId = "e5913a79-9b1e-4516-9ffd-06578e7af201";
        String saleId = "10";
        EmployeeModel employeeModel = new EmployeeModel();
        when(employeesServiceClient.getEmployeeByEmployeeId(employeeId)).thenReturn(employeeModel);
        when(saleRepository.findSaleBySaleIdentifier_SaleId(saleId)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> saleService.deletePurchaseFromEmployee(employeeId, saleId));
        verify(saleRepository, never()).delete(any());
    }

    @Test
    public void whenValidEmployeeId_EmployeeId_StoreId_thenUpdateSale(){
        //arrange
        String saleId = "11";
        var inventoryModel = InventoryModel.builder()
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var inventoryModelStatusUpdate = InventoryModel.builder()

                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var watchModel = WatchModel.builder()
                .referenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234")
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var storeModel = StoreModel.builder()
                .storeId("e5913a79-9b1e-4516-9ffd-06578e7af261") //regarder dans le datasql file et mettre les donnes ici. Import lles donnees egalement
                .build();

        var employeeModel = EmployeeModel.builder()
                .employeeId("e5913a79-9b1e-4516-9ffd-06578e7af201")
                //.firstName("Vilma")
                //  .lastName("Chawner")
                .build();

        var saleRequestModel = SaleRequestModel.builder()
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .employeeId("e5913a79-9b1e-4516-9ffd-06578e7af201")
                .storeId("e5913a79-9b1e-4516-9ffd-06578e7af261")
                .referenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234")
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        var saleIdentifier1 = new SaleIdentifier();
        saleIdentifier1.setSaleId(saleId);
        var sale1 = Sale.builder()
                .saleIdentifier(saleIdentifier1)
                .inventoryModel(inventoryModel)
                .storeModel(storeModel)
                .employeeModel(employeeModel)
                .watchModel(watchModel)
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        var updateSale = Sale.builder()
                .saleIdentifier(saleIdentifier1)
                .inventoryModel(inventoryModel)
                .storeModel(storeModel)
                .employeeModel(employeeModel)
                .watchModel(watchModel)
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        when(employeesServiceClient.getEmployeeByEmployeeId(employeeModel.getEmployeeId())).thenReturn(employeeModel);
        when(inventoryServiceClient.getInventoryByInventoryId(inventoryModel.getInventoryId())).thenReturn(inventoryModel);
        when(inventoryServiceClient.getWatchByReferenceNumberAndInventoryId(any(),any())).thenReturn(watchModel);
        when(storesServiceClient.getStoreByStoreId(storeModel.getStoreId())).thenReturn(storeModel);
        when(saleRepository.findSaleBySaleIdentifier_SaleId(saleId)).thenReturn(sale1);
        when(saleRepository.save(any(Sale.class))).thenReturn(sale1, updateSale);

        //act
        SaleResponseModel saleResponseModel = saleService.updateEmployeeSale(saleRequestModel, employeeModel.getEmployeeId(), sale1.getSaleIdentifier().getSaleId());

        //assert
        assertNotNull(saleResponseModel);
        assertNotNull(saleResponseModel.getSaleId());
        assertEquals(inventoryModelStatusUpdate.getInventoryId(), saleResponseModel.getInventoryId());
        assertEquals(employeeModel.getEmployeeId(), saleResponseModel.getEmployeeId());
        assertEquals(storeModel.getStoreId(), saleResponseModel.getStoreId());
        assertEquals(inventoryModel.getInventoryId(), saleResponseModel.getInventoryId());

        verify(saleResponseMapper, times(1)).entityToResponseModel(updateSale);
    }

    @Test
    public void whenValidEmployeeIdAndSaleId_thenGetEmployeePurchaseBySaleId() {
        //arrange
        var inventoryModel = InventoryModel.builder()
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var inventoryModelStatusUpdate = InventoryModel.builder()

                ///.model("Submariner")
                //.brand("Rolex")
                // .color("Gold")
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var watchModel = WatchModel.builder()
                .referenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234")
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .build();

        var storeModel = StoreModel.builder()
                .storeId("e5913a79-9b1e-4516-9ffd-06578e7af261") //regarder dans le datasql file et mettre les donnes ici. Import lles donnees egalement
                .build();

        var employeeModel = EmployeeModel.builder()
                .employeeId("e5913a79-9b1e-4516-9ffd-06578e7af201")
                //.firstName("Vilma")
                //  .lastName("Chawner")
                .build();

        var saleRequestModel = SaleRequestModel.builder()
                .inventoryId("e5913a79-5151-5151-9ffd-06578e7a4321")
                .employeeId("e5913a79-9b1e-4516-9ffd-06578e7af201")
                .storeId("e5913a79-9b1e-4516-9ffd-06578e7af261")
                .referenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234")
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        var saleIdentifier1 = new SaleIdentifier();

        var sale1 = Sale.builder()
                .saleIdentifier(saleIdentifier1)
                .inventoryModel(inventoryModel)
                .storeModel(storeModel)
                .employeeModel(employeeModel)
                .watchModel(watchModel)
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        var updateSale = Sale.builder()
                .saleIdentifier(saleIdentifier1)
                .inventoryModel(inventoryModel)
                .storeModel(storeModel)
                .employeeModel(employeeModel)
                .watchModel(watchModel)
                .saleStatus(SaleStatus.PURCHASE_COMPLETED)
                .build();

        when(employeesServiceClient.getEmployeeByEmployeeId(employeeModel.getEmployeeId())).thenReturn(employeeModel);
        when(inventoryServiceClient.getInventoryByInventoryId(inventoryModel.getInventoryId())).thenReturn(inventoryModel);
        when(storesServiceClient.getStoreByStoreId(storeModel.getStoreId())).thenReturn(storeModel);
        when(saleRepository.findSaleBySaleIdentifier_SaleId(sale1.getSaleIdentifier().getSaleId())).thenReturn(sale1);
        when(saleRepository.save(any(Sale.class))).thenReturn(sale1, updateSale);


        //act
        SaleResponseModel saleResponseModel = saleService.getEmployeePurchaseBySaleId(employeeModel.getEmployeeId(), sale1.getSaleIdentifier().getSaleId());



        //assert
        assertNotNull(saleResponseModel);

        verify(saleResponseMapper, times(1)).entityToResponseModel(updateSale);


        }
    @Test
    public void whenInValidEmployeeId_thenThrowInvalidInputException() {
        // Arrange
        String invalidEmployeeId = "00";
        String validSaleId = "11";
        when(employeesServiceClient.getEmployeeByEmployeeId(invalidEmployeeId)).thenReturn(null);

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            saleService.getEmployeePurchaseBySaleId(invalidEmployeeId, validSaleId);
        });

        // Verify
        verify(employeesServiceClient, times(1)).getEmployeeByEmployeeId(invalidEmployeeId);
        assertEquals("EmployeeId provided is invalid" + invalidEmployeeId, exception.getMessage());
    }

    @Test
    public void whenInValidSaleId_thenThrowNotFoundException() {
        // Arrange
        String validEmployeeId = "11";
        String invalidSaleId = "00";
        when(employeesServiceClient.getEmployeeByEmployeeId(validEmployeeId)).thenReturn(new EmployeeModel());
        when(saleRepository.findSaleBySaleIdentifier_SaleId(invalidSaleId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            saleService.getEmployeePurchaseBySaleId(validEmployeeId, invalidSaleId);
        });

        // Verify
        verify(employeesServiceClient, times(1)).getEmployeeByEmployeeId(validEmployeeId);
        verify(saleRepository, times(1)).findSaleBySaleIdentifier_SaleId(invalidSaleId);
        assertEquals("Unknown saleId provided: " + invalidSaleId, exception.getMessage());
    }


    @Test
    public void whenInvalidEmployeeId_thenThrowInvalidInputException() {
        // Arrange
        String invalidEmployeeId = "3333333333333";
        SaleRequestModel saleRequestModel = new SaleRequestModel();
        saleRequestModel.setStoreId("e5913a79-9b1e-4516-9ffd-06578e7af261");
        saleRequestModel.setInventoryId("e5913a79-5151-5151-9ffd-06578e7a4321");
        saleRequestModel.setReferenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234");

        when(employeesServiceClient.getEmployeeByEmployeeId(invalidEmployeeId)).thenReturn(null);

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            saleService.addEmployeePurchase(saleRequestModel, invalidEmployeeId);
        });

        // Verify
        verify(employeesServiceClient, times(1)).getEmployeeByEmployeeId(invalidEmployeeId);
        assertEquals("employeeId provided is invalid " + invalidEmployeeId, exception.getMessage());
    }

    @Test
    public void whenInvalidStoreId_thenThrowInvalidInputException() {
        // Arrange
        String validEmployeeId = "e5913a79-9b1e-4516-9ffd-06578e7af201";
        String invalidStoreId = "333333333333333333";
        SaleRequestModel saleRequestModel = new SaleRequestModel();
        saleRequestModel.setStoreId(invalidStoreId);
        saleRequestModel.setInventoryId("e5913a79-5151-5151-9ffd-06578e7a4321");
        saleRequestModel.setReferenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234");

        when(employeesServiceClient.getEmployeeByEmployeeId(validEmployeeId)).thenReturn(new EmployeeModel());
        when(storesServiceClient.getStoreByStoreId(invalidStoreId)).thenReturn(null);

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            saleService.addEmployeePurchase(saleRequestModel, validEmployeeId);
        });

        // Verify
        verify(storesServiceClient, times(1)).getStoreByStoreId(invalidStoreId);
        assertEquals("StoreId provided is invalid " + invalidStoreId, exception.getMessage());
    }

    @Test
    public void whenInvalidInventoryId_thenThrowInvalidInputException() {
        // Arrange
        String validEmployeeId = "e5913a79-9b1e-4516-9ffd-06578e7af201";
        String validStoreId = "e5913a79-9b1e-4516-9ffd-06578e7af261";
        String invalidInventoryId = "33333333333333333";
        SaleRequestModel saleRequestModel = new SaleRequestModel();
        saleRequestModel.setStoreId(validStoreId);
        saleRequestModel.setInventoryId(invalidInventoryId);
        saleRequestModel.setReferenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234");

        when(employeesServiceClient.getEmployeeByEmployeeId(validEmployeeId)).thenReturn(new EmployeeModel());
        when(storesServiceClient.getStoreByStoreId(validStoreId)).thenReturn(new StoreModel());
        when(inventoryServiceClient.getInventoryByInventoryId(invalidInventoryId)).thenReturn(null);

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            saleService.addEmployeePurchase(saleRequestModel, validEmployeeId);
        });

        // Verify
        verify(inventoryServiceClient, times(1)).getInventoryByInventoryId(invalidInventoryId);
        assertEquals("InventoryId provided is invalid " + invalidInventoryId, exception.getMessage());
    }

    @Test
    public void whenInvalidWatchReferenceNumber_thenThrowInvalidInputException() {
        // Arrange
        String validEmployeeId = "e5913a79-9b1e-4516-9ffd-06578e7af201";
        String validStoreId = "e5913a79-9b1e-4516-9ffd-06578e7af261";
        String validInventoryId = "e5913a79-5151-5151-9ffd-06578e7a4321";
        String invalidReferenceNumber = "333333333333333333";
        SaleRequestModel saleRequestModel = new SaleRequestModel();
        saleRequestModel.setStoreId(validStoreId);
        saleRequestModel.setInventoryId(validInventoryId);
        saleRequestModel.setReferenceNumber(invalidReferenceNumber);

        when(employeesServiceClient.getEmployeeByEmployeeId(validEmployeeId)).thenReturn(new EmployeeModel());
        when(storesServiceClient.getStoreByStoreId(validStoreId)).thenReturn(new StoreModel());
        when(inventoryServiceClient.getInventoryByInventoryId(validInventoryId)).thenReturn(new InventoryModel());
        when(inventoryServiceClient.getWatchByReferenceNumberAndInventoryId(validInventoryId, invalidReferenceNumber)).thenReturn(null);

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            saleService.addEmployeePurchase(saleRequestModel, validEmployeeId);
        });

        // Verify
        verify(inventoryServiceClient, times(1)).getWatchByReferenceNumberAndInventoryId(validInventoryId, invalidReferenceNumber);
        assertEquals("Watch provided is invalid " + invalidReferenceNumber, exception.getMessage());
    }


    @Test
    public void whenInvalidSaleId_thenThrowNotFoundException() {
        // Arrange
        String invalidSaleId = "3333333333333333";
        String validEmployeeId = "e5913a79-9b1e-4516-9ffd-06578e7af201";
        SaleRequestModel saleRequestModel = new SaleRequestModel();
        saleRequestModel.setEmployeeId(validEmployeeId);
        saleRequestModel.setStoreId("e5913a79-9b1e-4516-9ffd-06578e7af261");
        saleRequestModel.setInventoryId("e5913a79-5151-5151-9ffd-06578e7a4321");
        saleRequestModel.setReferenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234");

        when(saleRepository.findSaleBySaleIdentifier_SaleId(invalidSaleId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            saleService.updateEmployeeSale(saleRequestModel, validEmployeeId, invalidSaleId);
        });

        // Verify
        verify(saleRepository, times(1)).findSaleBySaleIdentifier_SaleId(invalidSaleId);
        assertEquals("SaleId provided is unknown " + invalidSaleId, exception.getMessage());
    }

    @Test
    public void whenInvalidEmployeeId_thenThrowNotFoundException() {
        // Arrange
        String validSaleId = "11";
        String invalidEmployeeId = "000000000";
        SaleRequestModel saleRequestModel = new SaleRequestModel();
        saleRequestModel.setEmployeeId(invalidEmployeeId);
        saleRequestModel.setStoreId("e5913a79-9b1e-4516-9ffd-06578e7af261");
        saleRequestModel.setInventoryId("e5913a79-5151-5151-9ffd-06578e7a4321");
        saleRequestModel.setReferenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234");

        when(saleRepository.findSaleBySaleIdentifier_SaleId(validSaleId)).thenReturn(new Sale());
        when(employeesServiceClient.getEmployeeByEmployeeId(invalidEmployeeId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            saleService.updateEmployeeSale(saleRequestModel, invalidEmployeeId, validSaleId);
        });

        // Verify
        verify(employeesServiceClient, times(1)).getEmployeeByEmployeeId(invalidEmployeeId);
        assertEquals("EmployeeId provided is invalid " + invalidEmployeeId, exception.getMessage());
    }

    @Test
    public void whenInvalidStoreId_thenThrowNotFoundException() {
        // Arrange
        String validSaleId = "11";
        String validEmployeeId = "e5913a79-9b1e-4516-9ffd-06578e7af201";
        String invalidStoreId = "333333333333333";
        SaleRequestModel saleRequestModel = new SaleRequestModel();
        saleRequestModel.setEmployeeId(validEmployeeId);
        saleRequestModel.setStoreId(invalidStoreId);
        saleRequestModel.setInventoryId("e5913a79-5151-5151-9ffd-06578e7a4321");
        saleRequestModel.setReferenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234");

        when(saleRepository.findSaleBySaleIdentifier_SaleId(validSaleId)).thenReturn(new Sale());
        when(employeesServiceClient.getEmployeeByEmployeeId(validEmployeeId)).thenReturn(new EmployeeModel());
        when(storesServiceClient.getStoreByStoreId(invalidStoreId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            saleService.updateEmployeeSale(saleRequestModel, validEmployeeId, validSaleId);
        });

        // Verify
        verify(storesServiceClient, times(1)).getStoreByStoreId(invalidStoreId);
        assertEquals("StoreId provided is invalid " + invalidStoreId, exception.getMessage());
    }

    @Test
    public void whenInvalidInventoryId_thenThrowNotFoundException() {
        // Arrange
        String validSaleId = "11";
        String validEmployeeId = "e5913a79-9b1e-4516-9ffd-06578e7af201";
        String validStoreId = "e5913a79-9b1e-4516-9ffd-06578e7af261";
        String invalidInventoryId = "00000000000000";
        SaleRequestModel saleRequestModel = new SaleRequestModel();
        saleRequestModel.setEmployeeId(validEmployeeId);
        saleRequestModel.setStoreId(validStoreId);
        saleRequestModel.setInventoryId(invalidInventoryId);
        saleRequestModel.setReferenceNumber("a1913a79-5151-5151-9ffd-06578e7a1234");

        when(saleRepository.findSaleBySaleIdentifier_SaleId(validSaleId)).thenReturn(new Sale());
        when(employeesServiceClient.getEmployeeByEmployeeId(validEmployeeId)).thenReturn(new EmployeeModel());
        when(storesServiceClient.getStoreByStoreId(validStoreId)).thenReturn(new StoreModel());
        when(inventoryServiceClient.getInventoryByInventoryId(invalidInventoryId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            saleService.updateEmployeeSale(saleRequestModel, validEmployeeId, validSaleId);
        });

        // Verify
        verify(inventoryServiceClient, times(1)).getInventoryByInventoryId(invalidInventoryId);
        assertEquals("InventoryId provided is invalid " + invalidInventoryId, exception.getMessage());
    }


}



