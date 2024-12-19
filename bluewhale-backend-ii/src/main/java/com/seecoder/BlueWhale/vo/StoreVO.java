package com.seecoder.BlueWhale.vo;
import com.seecoder.BlueWhale.po.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreVO {
    private Integer storeId;

    private String name;

    private String logoUrl;

    private String introduction;

    private Double Score;

    private Integer times;

    public Store toPO(){
        Store store = new Store();
        store.setStoreId(this.storeId);
        store.setName(this.name);
        store.setLogoUrl(this.logoUrl);
        store.setIntroduction(this.introduction);
        store.setScore(this.Score);
        store.setTimes(this.times);
        return store;
    }

}
