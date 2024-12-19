package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.enums.GoodTypeEnum;
import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.po.ProductImage;
import com.seecoder.BlueWhale.po.Store;
import com.seecoder.BlueWhale.repository.ProductImageRepository;
import com.seecoder.BlueWhale.repository.ProductRepository;
import com.seecoder.BlueWhale.repository.StoreRepository;
import com.seecoder.BlueWhale.service.ProductService;
import com.seecoder.BlueWhale.util.SecurityUtil;
import com.seecoder.BlueWhale.util.TokenUtil;
import com.seecoder.BlueWhale.vo.ProductCond;
import com.seecoder.BlueWhale.vo.ProductVO;
import com.seecoder.BlueWhale.vo.StoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    SecurityUtil securityUtil;

    public void check(ProductVO productVO)
    {
        if(storeRepository.findByStoreId(productVO.getStoreId())==null)
        {
            throw  BlueWhaleException.ErrorParam();
        }
    }

    @Override
    public Boolean createProduct(ProductVO productVO,List<String> imageList) {
        check(productVO);
        if (!productRepository.findByStoreIdAndName(productVO.getStoreId(),productVO.getName()).isEmpty()) {
            throw BlueWhaleException.productAlreadyExists();
        }
        Product newproduct=productVO.toPO();
        productRepository.save(newproduct);
        Integer productId = productRepository.getProductByName(productVO.getName()).getId();
        for(int i=0;i<imageList.size();i++){
            String logo = imageList.get(i);
            ProductImage newProductImage = new ProductImage();
            newProductImage.setUrl(logo);
            newProductImage.setProductId(productId);
            productImageRepository.save(newProductImage);
        }
        return true;
    }

    @Override
    public Boolean changeInventory(Integer id,Integer change) {
        Product product=productRepository.getProductById(id);
        product.setQuantity(product.getQuantity()+change);
        productRepository.save(product);
        return true;

    }

    @Override
    public List<ProductVO> getProductListByStoreId(Integer storeId) {
        List<Product> productPOList =productRepository.findByStoreId(storeId);
        List<ProductVO> productVOList = new ArrayList<>();
        for(int i=0;i<productPOList.size();i++){
            productVOList.add(productPOList.get(i).toVO());
        }
        return productVOList;
    }

    @Override
    public ProductVO getProductById(Integer id)
    {
        return productRepository.getProductById(id).toVO();
    }

    @Override
    public List<String> getProductImageListByProductId(Integer productId){
        ArrayList<ProductImage> productImageList = new ArrayList<>(productImageRepository.findByProductId(productId));
        ArrayList<String> logoUrlList = new ArrayList<>();
        for(int i=0;i<productImageList.size();i++){
            logoUrlList.add(productImageList.get(i).getUrl());
        }
        return logoUrlList;
    }

    @Override
    public List<ProductVO> getProductByStoreIdAndType(Integer StoreId , GoodTypeEnum type)
    {
        List<Product> productPOList =productRepository.findByStoreIdAndType(StoreId,type);
        List<ProductVO> productVOList = new ArrayList<>();
        for(int i=0;i<productPOList.size();i++){
            productVOList.add(productPOList.get(i).toVO());
        }
        return productVOList;
    }

    @Override
    public List<String> getProductFirstImageListByStoreId(Integer storeId){
        List<Product> productList = productRepository.findByStoreId(storeId);
        List<String> imageList = new ArrayList<>();
        for (int i = 0;i< productList.size();i++){
            List<ProductImage> productImageList = productImageRepository.findByProductId(productList.get(i).getId());
            imageList.add(productImageList.get(0).getUrl());
        }
        return imageList;

    }

    @Override
    public List<ProductVO> getProductByCond(ProductCond productCond){
        Specification<Product> spec = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> name = root.get("name");//商品名
                Path<Object> type = root.get("type");//商品类型
                Path price = root.get("price");//商品价格

                Predicate cond = criteriaBuilder.greaterThan(price,-1);
                if(productCond.getName()!=null){
                    Predicate nameCond = criteriaBuilder.like(name.as(String.class),"%"+productCond.getName()+"%");
                    cond = criteriaBuilder.and(cond,nameCond);
                }
                if(productCond.getType()!=null){
                    Predicate typeCond = criteriaBuilder.equal(type,productCond.getType());
                    cond = criteriaBuilder.and(cond,typeCond);
                }
                if(productCond.getMinPrice()!=null){
                    Predicate minPriceCond = criteriaBuilder.greaterThanOrEqualTo(price,productCond.getMinPrice());
                    cond = criteriaBuilder.and(cond,minPriceCond);
                }
                if(productCond.getMaxPrice()!=null){
                    Predicate maxPriceCond = criteriaBuilder.lessThanOrEqualTo(price,productCond.getMaxPrice());
                    cond = criteriaBuilder.and(cond,maxPriceCond);
                }
                return cond;
            }
        };
        List<Product> productPOList = productRepository.findAll(spec);
        List<ProductVO> productVOList = new ArrayList<ProductVO>();
        for (Product product : productPOList) {
            int productId = product.getId();
            ProductVO tempProductVO = product.toVO();
            tempProductVO.setProductImages(getProductImageListByProductId(productId));
            productVOList.add(tempProductVO);
        }
        return productVOList;
    }
}
