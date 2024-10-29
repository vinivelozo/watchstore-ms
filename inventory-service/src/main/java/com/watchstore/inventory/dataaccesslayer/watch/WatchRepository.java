package com.watchstore.inventory.dataaccesslayer.watch;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchRepository extends JpaRepository<Watch, Integer> {

    Watch findWatchByWatchIdentifier_ReferenceNumber(String referenceNumber);

    Watch findByInventoryIdentifier_InventoryIdAndWatchIdentifier_ReferenceNumber(String inventoryIdentifier_inventoryId, String watchIdentifier_referenceNumber);

    List<Watch> findAllByInventoryIdentifier_InventoryId(String inventoryId);

    List<Watch> findAllByInventoryIdentifier_InventoryIdAndStatusEquals(String inventoryIdentifier_inventoryId, Status status);


}
