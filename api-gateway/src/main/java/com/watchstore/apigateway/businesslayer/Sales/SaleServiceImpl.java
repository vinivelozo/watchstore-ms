package com.watchstore.apigateway.businesslayer.Sales;



import com.watchstore.apigateway.domainclientlayer.Employee.EmployeesServiceClient;
import com.watchstore.apigateway.domainclientlayer.Inventory.InventoryServiceClient;
import com.watchstore.apigateway.domainclientlayer.Sales.SalesServiceClient;
import com.watchstore.apigateway.domainclientlayer.Stores.StoresServiceClient;
import com.watchstore.apigateway.mappinglayer.SaleResponseMapper;
import com.watchstore.apigateway.presentationlayer.Sales.SaleRequestModel;
import com.watchstore.apigateway.presentationlayer.Sales.SaleResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SaleServiceImpl implements SaleService {

    private final SalesServiceClient salesServiceClient;

    private final SaleResponseMapper saleResponseMapper;


    public SaleServiceImpl(SalesServiceClient salesServiceClient, SaleResponseMapper saleResponseMapper) {
        this.salesServiceClient = salesServiceClient;
        this.saleResponseMapper = saleResponseMapper;
    }

    @Override
    public List<SaleResponseModel> getAllPurchaseForEmployee(String employeeId) {

        return saleResponseMapper.responseModelListToResponseModelList(salesServiceClient.getAllPurchaseForEmployee(employeeId));


    }

    @Override
    public SaleResponseModel getEmployeePurchaseBySaleId(String employeeId, String saleId) {

      return saleResponseMapper.responseModelToResponseModel(salesServiceClient.getEmployeePurchaseBySaleId(employeeId, saleId));

    }

    @Override
    public SaleResponseModel addEmployeePurchase(SaleRequestModel saleRequestModel, String employeeId) {

        return saleResponseMapper.responseModelToResponseModel(salesServiceClient.addEmployeePurchase(saleRequestModel, employeeId));
    }

    @Override
    public SaleResponseModel updateEmployeeSale(SaleRequestModel saleRequestModel, String employeeId, String saleId) {



        return saleResponseMapper.responseModelToResponseModel(salesServiceClient.updateEmployeeSale(saleRequestModel, employeeId, saleId));



    }

    @Override
    public void deletePurchaseFromEmployee(String employeeId, String saleId) {


        salesServiceClient.deletePurchaseFromEmployee(employeeId, saleId);

    }
}






