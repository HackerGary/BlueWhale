package com.seecoder.BlueWhale.vo;

import com.seecoder.BlueWhale.enums.CouponTypeEnum;
import com.seecoder.BlueWhale.po.CouponGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CouponGroupVO {

    Integer coupongroupId;

    String couponGroupName;

    Date deadline;

    Double discountRange;


    Double discountPrice;


    CouponTypeEnum couponType;


    Integer couponSum;


    Integer couponLeft;


    Integer storeId;
    public CouponGroup toPO()
    { CouponGroup couponGroup = new CouponGroup();
        couponGroup.setCoupongroupId(this.coupongroupId);
        couponGroup.setCouponGroupName(this.couponGroupName);
        couponGroup.setDeadline(this.deadline);
        couponGroup.setDiscountRange(this.discountRange);
        couponGroup.setDiscountPrice(this.discountPrice);
        couponGroup.setCouponType(this.couponType);
        couponGroup.setCouponSum(this.couponSum);
        couponGroup.setCouponLeft(this.couponLeft);
        couponGroup.setStoreId(this.storeId);
        return couponGroup;

    }
}
