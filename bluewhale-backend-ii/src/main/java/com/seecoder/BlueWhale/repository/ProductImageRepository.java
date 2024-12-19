package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.po.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Integer> {
    List<ProductImage> findByProductId(Integer productId);
}
