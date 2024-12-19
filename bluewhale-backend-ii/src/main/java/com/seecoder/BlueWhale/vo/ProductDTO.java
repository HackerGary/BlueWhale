package com.seecoder.BlueWhale.vo;

import com.seecoder.BlueWhale.enums.GoodTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private String name;

    private Integer storeId;

    private Double price;

    private GoodTypeEnum type;

    private String introduction;

    private String logoUrlList;

    private Double score;
    private Integer times;
}
