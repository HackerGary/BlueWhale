package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Integer>  {
    Store findByStoreId(Integer storeID);
    Store findByName(String name);
    List<Store> findAll();
}
