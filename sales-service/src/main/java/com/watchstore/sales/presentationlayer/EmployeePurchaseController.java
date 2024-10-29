package com.watchstore.sales.presentationlayer;

import com.watchstore.sales.businesslayer.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/employees/{employeeId}/purchases")
public class EmployeePurchaseController {

    private final SaleService saleService;

    public EmployeePurchaseController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping()
    public ResponseEntity<List<SaleResponseModel>> getAllPurchaseForEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok().body(saleService.getAllPurchaseForEmployee(employeeId));
    }

    @GetMapping(value = "{saleId}", produces = "application/json")
    public ResponseEntity<SaleResponseModel> getEmployeePurchaseBySaleId(@PathVariable String employeeId, @PathVariable String saleId) {
        return ResponseEntity.ok().body(saleService.getEmployeePurchaseBySaleId(employeeId, saleId));
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<SaleResponseModel> addEmployeePurchase(@RequestBody SaleRequestModel saleRequestModel, @PathVariable String employeeId) {
        return ResponseEntity.ok().body(saleService.addEmployeePurchase(saleRequestModel, employeeId));
    }

    @PutMapping(value ="/{saleId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<SaleResponseModel> updateEmployeeSale (@RequestBody SaleRequestModel saleRequestModel, @PathVariable String employeeId, @PathVariable String saleId) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.updateEmployeeSale(saleRequestModel, employeeId, saleId));
    }

    @DeleteMapping("/{saleId}")
    public ResponseEntity<Void> deletePurchaseFromEmployee(@PathVariable  String employeeId, @PathVariable String saleId) {
        saleService.deletePurchaseFromEmployee(employeeId, saleId);
        return ResponseEntity.noContent().build();
    }
}
