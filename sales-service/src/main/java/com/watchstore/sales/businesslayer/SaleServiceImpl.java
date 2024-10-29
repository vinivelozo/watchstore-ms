package com.watchstore.sales.businesslayer;


import com.watchstore.sales.dataaccesslayer.Sale;
import com.watchstore.sales.dataaccesslayer.SaleIdentifier;
import com.watchstore.sales.dataaccesslayer.SaleRepository;
import com.watchstore.sales.domainclientlayer.employees.EmployeeModel;
import com.watchstore.sales.domainclientlayer.employees.EmployeesServiceClient;
import com.watchstore.sales.domainclientlayer.inventory.InventoryModel;
import com.watchstore.sales.domainclientlayer.inventory.InventoryServiceClient;
import com.watchstore.sales.domainclientlayer.inventory.Status;
import com.watchstore.sales.domainclientlayer.stores.StoreModel;
import com.watchstore.sales.domainclientlayer.stores.StoresServiceClient;
import com.watchstore.sales.domainclientlayer.watch.WatchModel;
import com.watchstore.sales.domainclientlayer.watch.WatchResponseModel;
import com.watchstore.sales.mapperlayer.SaleRequestMapper;
import com.watchstore.sales.mapperlayer.SaleResponseMapper;
import com.watchstore.sales.presentationlayer.SaleRequestModel;
import com.watchstore.sales.presentationlayer.SaleResponseModel;
import com.watchstore.sales.utils.exceptions.InvalidInputException;
import com.watchstore.sales.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    private final EmployeesServiceClient employeesServiceClient;

    private final InventoryServiceClient inventoryServiceClient;

    private final StoresServiceClient storesServiceClient;


    private final SaleResponseMapper saleResponseMapper;


    private final SaleRequestMapper saleRequestMapper;

    public SaleServiceImpl(SaleRepository saleRepository,
                           EmployeesServiceClient employeesServiceClient,
                           InventoryServiceClient inventoryServiceClient,
                           StoresServiceClient storesServiceClient,
                           SaleResponseMapper saleResponseMapper, SaleRequestMapper saleRequestMapper) {
        this.saleRepository = saleRepository;
        this.employeesServiceClient = employeesServiceClient;
        this.inventoryServiceClient = inventoryServiceClient;
        this.storesServiceClient = storesServiceClient;
        this.saleResponseMapper = saleResponseMapper;
        this.saleRequestMapper = saleRequestMapper;
    }

    @Override
    public List<SaleResponseModel> getAllPurchaseForEmployee(String employeeId) {

        EmployeeModel foundEmployee = employeesServiceClient.getEmployeeByEmployeeId(employeeId);

        if (foundEmployee == null){
            throw new InvalidInputException("EmployeeId provided is invalid" + employeeId);

        }

        List<Sale> employeePurchases = saleRepository.findSaleByEmployeeModel_EmployeeId(employeeId);

        return saleResponseMapper.entityListToResponseModelList(employeePurchases);


    }

    @Override
    public SaleResponseModel getEmployeePurchaseBySaleId(String employeeId, String saleId) {

        //verify employee exists

        EmployeeModel foundEmployee = employeesServiceClient.getEmployeeByEmployeeId(employeeId);

        if (foundEmployee == null){
            throw new InvalidInputException("EmployeeId provided is invalid" + employeeId);

        }

        //verify sale exists
        Sale foundSale = saleRepository.findSaleBySaleIdentifier_SaleId(saleId);
        if (foundSale ==null){

            throw new NotFoundException("Unknown saleId provided: " + saleId);
        }

        return saleResponseMapper.entityToResponseModel(foundSale);
    }

    @Override
    public SaleResponseModel addEmployeePurchase(SaleRequestModel saleRequestModel, String employeeId) {

        //verify if employee exists
        EmployeeModel foundEmployee = employeesServiceClient.getEmployeeByEmployeeId(employeeId);

        if (foundEmployee == null) {
            throw new InvalidInputException("employeeId provided is invalid " + employeeId);
        }
        //verify if store exists
        StoreModel foundStore = storesServiceClient.getStoreByStoreId(saleRequestModel.getStoreId());

        if (foundStore == null) {
            throw new InvalidInputException("StoreId provided is invalid " + saleRequestModel.getStoreId());
        }

        //verify if inventory exists
        InventoryModel foundInventory = inventoryServiceClient.getInventoryByInventoryId(saleRequestModel.getInventoryId());
        if (foundInventory == null){
            throw new InvalidInputException("InventoryId provided is invalid " + saleRequestModel.getInventoryId());
        }

        //verify that watch exists in inventory

        WatchModel foundWatch = inventoryServiceClient.getWatchByReferenceNumberAndInventoryId(saleRequestModel.getInventoryId(), saleRequestModel.getReferenceNumber());
        if (foundWatch== null){
            throw new InvalidInputException("Watch provided is invalid " + saleRequestModel.getReferenceNumber());
        }


//        //verify that watch is not already sold
//
//        if(foundWatch.getStatus() != Status.AVAILABLE){
//            throw new InvalidInputException("Watch is not available for sale:" + saleRequestModel.getReferenceNumber());
//        }

        //convert request model to entity
        Sale sale = saleRequestMapper.requestModelToEntity(saleRequestModel, new SaleIdentifier(), foundEmployee, foundInventory, foundStore, foundWatch);

        //save sale
        Sale savedSale = saleRepository.save(sale);

        //convert savedsale entity to a sale response model

        return saleResponseMapper.entityToResponseModel(savedSale);
    }

    @Override
    public SaleResponseModel updateEmployeeSale(SaleRequestModel saleRequestModel, String employeeId, String saleId) {
        // Retrieve the existing sale
        Sale foundSale = saleRepository.findSaleBySaleIdentifier_SaleId(saleId);
        if (foundSale == null) {
            throw new NotFoundException("SaleId provided is unknown " + saleId);
        }

        // Verify if employee exists
        EmployeeModel foundEmployee = employeesServiceClient.getEmployeeByEmployeeId(saleRequestModel.getEmployeeId());
        if (foundEmployee == null) {
            throw new NotFoundException("EmployeeId provided is invalid " + saleRequestModel.getEmployeeId());
        }

        // Verify if store exists
        StoreModel foundStore = storesServiceClient.getStoreByStoreId(saleRequestModel.getStoreId());
        if (foundStore == null) {
            throw new NotFoundException("StoreId provided is invalid " + saleRequestModel.getStoreId());
        }

        // Verify if inventory exists
        InventoryModel foundInventory = inventoryServiceClient.getInventoryByInventoryId(saleRequestModel.getInventoryId());
        if (foundInventory == null) {
            throw new NotFoundException("InventoryId provided is invalid " + saleRequestModel.getInventoryId());
        }

        // Verify if watch exists
        WatchModel foundWatch = inventoryServiceClient.getWatchByReferenceNumberAndInventoryId(saleRequestModel.getInventoryId(), saleRequestModel.getReferenceNumber());
        if (foundWatch == null) {
            throw new InvalidInputException("Watch provided is invalid " + saleRequestModel.getReferenceNumber());
        }

        // Update the existing sale with new details
        foundSale.setEmployeeModel(foundEmployee);
        foundSale.setInventoryModel(foundInventory);
        foundSale.setStoreModel(foundStore);
        foundSale.setWatchModel(foundWatch);
        // ... update other fields as necessary

        // Save the updated sale
        Sale savedSale = saleRepository.save(foundSale);

        // Return the response model
        return saleResponseMapper.entityToResponseModel(savedSale);
    }


    @Override
    public void deletePurchaseFromEmployee(String employeeId, String saleId) {

        String randomSaleId = UUID.randomUUID().toString();

        EmployeeModel foundEmployee = employeesServiceClient.getEmployeeByEmployeeId(employeeId);
        if (foundEmployee == null) {
            throw new NotFoundException("EmployeeId provided is invalid" + employeeId);
        }

        Sale sale = saleRepository.findSaleBySaleIdentifier_SaleId(saleId);

        if (sale == null){
            throw new NotFoundException("Unknow saleId" + saleId);
        }


        saleRepository.delete(sale);

    }
}






