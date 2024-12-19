package com.seecoder.BlueWhale.vo;

import com.seecoder.BlueWhale.enums.CouponTypeEnum;
import com.seecoder.BlueWhale.po.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CouponVO {

    Integer couponId;

    String couponName;

    Date deadline;

    CouponTypeEnum couponType;


    Integer coupongroupId;


    Double discountRange;//此为满多少金额可减


    Double discountPrice;//此为减少金额


    Integer userId;


    Integer storeId;

    Boolean valid;
    public Coupon toPO()
    { Coupon coupon = new Coupon();
        coupon.setCouponId(this.couponId);
        coupon.setCouponName(this.couponName);
        coupon.setDeadline(this.deadline);
        coupon.setCouponType(this.couponType);
        coupon.setCoupongroupId(this.coupongroupId);
        coupon.setDiscountRange(this.discountRange);
        coupon.setDiscountPrice(this.discountPrice);
        coupon.setUserId(this.userId);
        coupon.setStoreId(this.storeId);
        coupon.setValid(this.valid);
        return coupon;

    }
}
