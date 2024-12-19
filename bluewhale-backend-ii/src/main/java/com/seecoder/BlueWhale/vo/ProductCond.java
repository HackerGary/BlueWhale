package com.seecoder.BlueWhale.vo;

import com.seecoder.BlueWhale.enums.GoodTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//用来传递商品动态多条件查询的条件
@Getter
@Setter
public class ProductCond {
    private String name;
    private GoodTypeEnum type;
    private Integer minPrice;
    private Integer maxPrice;

    public ProductCond(String name, GoodTypeEnum type, Integer minPrice, Integer maxPrice) {
        this.name = name;
        this.type = type;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
