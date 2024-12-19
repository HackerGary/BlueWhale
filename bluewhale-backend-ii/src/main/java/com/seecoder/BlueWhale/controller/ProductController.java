package com.seecoder.BlueWhale.controller;

import com.alibaba.fastjson.JSON;
import com.seecoder.BlueWhale.enums.GoodTypeEnum;
import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.service.ProductService;
import com.seecoder.BlueWhale.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping("/createProduct")
    public ResultVO<Boolean> createProduct(@RequestBody ProductDTO productDTO)
    {
        ProductVO productVO = new ProductVO();
        productVO.setType(productDTO.getType());
        productVO.setName(productDTO.getName());
        productVO.setIntroduction(productDTO.getIntroduction());
        productVO.setStoreId(productDTO.getStoreId());
        productVO.setPrice(productDTO.getPrice());
        productVO.setTimes(productDTO.getTimes());
        productVO.setScore(productDTO.getScore());
        String logoUrlList = productDTO.getLogoUrlList();
        List<String> imageList = JSON.parseArray(logoUrlList, String.class);
        return ResultVO.buildSuccess(productService.createProduct(productVO,imageList));
    }
    @GetMapping("/changeInventory")
    public ResultVO<Boolean> changeInventory(@RequestParam("productId")Integer id,@RequestParam("change")Integer change)
    {
        return ResultVO.buildSuccess(productService.changeInventory(id,change));
    }
    @GetMapping("/getProductListByStoreId")
    public ResultVO<List<ProductVO>> getProductListByStoreId(@RequestParam ("storeId")Integer storeId){
        return ResultVO.buildSuccess(productService.getProductListByStoreId(storeId));
    }
    @GetMapping("/getProductById")
    public ResultVO<ProductVO> getProductById(@RequestParam("productId")Integer id)
    {
        return ResultVO.buildSuccess(productService.getProductById(id));
    }
    @GetMapping("/getProductImageById")
    public ResultVO<List<String>> getProductImageById(@RequestParam("productId")Integer productid)
    {
        return ResultVO.buildSuccess(productService.getProductImageListByProductId((productid)));
    }
    @GetMapping("/getProductByStoreIdAndType")
    public ResultVO<List<ProductVO>> getProductByStoreIdAndType(@RequestParam("StoreId")Integer storeId, @RequestParam("Type")GoodTypeEnum Type)
    {
           return ResultVO.buildSuccess(productService.getProductByStoreIdAndType(storeId, Type));
    }

    @GetMapping("/getProductFirstImageListByStoreId")
    public ResultVO<List<String>> getProductFirstImageListByStoreId(@RequestParam("storeId")Integer storeId){
        return ResultVO.buildSuccess(productService.getProductFirstImageListByStoreId(storeId));
    }

    @GetMapping("/getProductByCond")
    public ResultVO<List<ProductVO>> getProductByCond(@RequestParam("name")String name, @RequestParam("type")GoodTypeEnum type, @RequestParam("minPrice")Integer minPrice, @RequestParam("maxPrice")Integer maxPrice){
        ProductCond productCond = new ProductCond(name,type,minPrice,maxPrice);
        return ResultVO.buildSuccess(productService.getProductByCond(productCond));
    }

}
