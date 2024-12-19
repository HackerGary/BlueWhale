package com.seecoder.BlueWhale.service;

import com.seecoder.BlueWhale.enums.GoodTypeEnum;
import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.vo.ProductCond;
import com.seecoder.BlueWhale.vo.ProductVO;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {
      ProductVO getProductById(Integer id);

    Boolean createProduct(ProductVO productVO, List<String> imageList);

    Boolean changeInventory(Integer id,Integer add);

    List<ProductVO> getProductListByStoreId(Integer storeId);
    List<String> getProductImageListByProductId(Integer productId);
    List<ProductVO> getProductByStoreIdAndType(Integer StoreId, GoodTypeEnum type);

    List<String> getProductFirstImageListByStoreId(Integer storeId);

    List<ProductVO> getProductByCond(ProductCond productCond);
}
