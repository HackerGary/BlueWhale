package com.seecoder.BlueWhale.vo;

import com.seecoder.BlueWhale.enums.GoodTypeEnum;
import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.po.ProductImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductVO {

    private Integer id;

    private String name;

    private Integer storeId;

    private GoodTypeEnum type;

    private Double price;

    private Integer quantity=0;

    private String introduction;

    private Double score;

    private Integer times;

    private List<String> ProductImages;


    public Product toPO(){
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.name);
        product.setStoreId(this.storeId);
        product.setType(this.type);
        product.setPrice(this.price);
        product.setQuantity(this.quantity);
        product.setIntroduction(this.introduction);
        product.setScore(this.score);
        product.setTimes(this.times);
//        product.setLogoUrlList(this.logoUrlList);
        return product;
    }

//    private String listToString(List<String> list){
//        String Str = list.get(0);
//        for (int i = 1;i<list.size();i++){
//            Str=Str+"|"+list.get(i);
//        }
//        return Str;
//
//    }



}
