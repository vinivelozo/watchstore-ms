package com.watchstore.sales.dataaccesslayer;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SaleRepository extends MongoRepository<Sale, String> {


    Sale findSaleBySaleIdentifier_SaleId(String saleId);

    List<Sale> findSaleByEmployeeModel_EmployeeId(String employeeId);
}
