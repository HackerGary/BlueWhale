package com.seecoder.BlueWhale.po;

import com.seecoder.BlueWhale.enums.CouponTypeEnum;
import com.seecoder.BlueWhale.vo.CouponVO;
import com.seecoder.BlueWhale.vo.OrderVO;
import com.seecoder.BlueWhale.vo.ProductVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "`coupon`",indexes = {@Index(columnList = "user_id")})
public class Coupon {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="coupon_id")
    Integer couponId;

    @Basic
    @Column(name="coupon_name")
    String couponName;

    @Basic
    @Column(name="deadline")
    Date deadline;

    @Basic
    @Column(name="coupon_type")
    @Enumerated(EnumType.STRING)
    CouponTypeEnum couponType;

    @Basic
    @Column(name="coupongroup_id")
    Integer coupongroupId;

    @Basic
    @Column(name="discount_range")
    Double discountRange;

    @Basic
    @Column(name="discount_price")
    Double discountPrice;


    @Basic
    @Column(name="user_id")
    Integer userId;

    @Basic
    @Column(name="store_id")
    Integer storeId;//为-1则是全局优惠劵

    @Basic
    @Column(name="valid")
    Boolean valid;
    public CouponVO toVO()
    { CouponVO couponVO = new CouponVO();
       couponVO.setCouponId(this.couponId);
       couponVO.setCouponName(this.couponName);
       couponVO.setDeadline(this.deadline);
       couponVO.setCouponType(this.couponType);
       couponVO.setCoupongroupId(this.coupongroupId);
       couponVO.setDiscountRange(this.discountRange);
       couponVO.setDiscountPrice(this.discountPrice);
       couponVO.setUserId(this.userId);
       couponVO.setStoreId(this.storeId);
       couponVO.setValid(this.valid);
        return couponVO;

    }
}
