package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.enums.GoodTypeEnum;
import com.seecoder.BlueWhale.po.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer>, JpaSpecificationExecutor<Product> {


    List<Product> findByStoreIdAndName(Integer storeid,String name);

    Product getProductById(Integer id);
    Product getProductByName(String name);
    List<Product>findByStoreId(Integer storeId);
    List<Product> findAll();
    List<Product>findByStoreIdAndType(Integer StoreId, GoodTypeEnum type);

}
