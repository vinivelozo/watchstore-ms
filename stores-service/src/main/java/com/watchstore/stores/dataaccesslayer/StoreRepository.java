package com.watchstore.stores.dataaccesslayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {

    Store findStoreByStoreIdentifier_StoreId(String storeId);
}
