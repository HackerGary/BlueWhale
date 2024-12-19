package com.seecoder.BlueWhale.po;
import com.seecoder.BlueWhale.vo.StoreVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Store {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "store_id")
    private Integer storeId;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "logo_url")
    private String logoUrl;

    @Basic
    @Column(name = "introduction")
    private String introduction;

    @Basic
    @Column(name="score")
    private Double score;

    @Basic
    @Column(name="times")
    private Integer times;

    public StoreVO toVO(){
        StoreVO storeVO = new StoreVO();
        storeVO.setStoreId(this.storeId);
        storeVO.setName(this.name);
        storeVO.setLogoUrl(this.logoUrl);
        storeVO.setIntroduction(this.introduction);
        storeVO.setScore(this.score);
        storeVO.setTimes(this.times);
        return storeVO;
    }

}
