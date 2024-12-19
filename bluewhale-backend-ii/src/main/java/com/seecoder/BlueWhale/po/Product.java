package com.seecoder.BlueWhale.po;

import com.seecoder.BlueWhale.enums.GoodTypeEnum;
import com.seecoder.BlueWhale.vo.ProductVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "`product`",indexes = {@Index(columnList = "store_id")})
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "store_id")
    private Integer storeId;

    @Basic
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private GoodTypeEnum type;

    @Basic
    @Column(name = "price")
    private Double price;

    @Basic
    @Column(name = "quantity")
    private Integer quantity=0;

    @Basic
    @Column(name = "introduction")
    private String introduction;

    @Basic
    @Column(name="score")
    private Double score;

    @Basic
    @Column(name="times")
    private Integer times;

//    @Basic
//    @Column(name="logo_url_list")
//    private String logoUrlList;


    public ProductVO toVO(){
        ProductVO productVO = new ProductVO();
        productVO.setId(this.id);
        productVO.setName(this.name);
        productVO.setStoreId(this.storeId);
        productVO.setType(this.type);
        productVO.setPrice(this.price);
        productVO.setQuantity(this.quantity);
        productVO.setIntroduction(this.introduction);
        productVO.setScore(this.score);
        productVO.setTimes(this.times);
//        productVO.setLogoUrlList(this.logoUrlList);
        return productVO;
    }

//    private List<String> stringToList(String str){
//        String[] strArr = str.split("|");
//        List<String> list = new ArrayList<>();
//        for (int i = 0;i<strArr.length;i++){
//            list.add(strArr[i]);
//        }
//        return list;
//    }

}
