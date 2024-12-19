package com.seecoder.BlueWhale.po;

import com.seecoder.BlueWhale.enums.CouponTypeEnum;
import com.seecoder.BlueWhale.vo.CouponGroupVO;
import com.seecoder.BlueWhale.vo.CouponVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "`coupon_group`",indexes = {@Index(columnList = "store_id")})
public class CouponGroup {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="coupon_group_id")
    Integer coupongroupId;


    @Basic
    @Column(name="coupon_group_name")
    String couponGroupName;

    @Basic
    @Column(name="deadline")
    Date deadline;

    @Basic
    @Column(name="discount_range")
    Double discountRange;

    @Basic
    @Column(name="discount_price")
    Double discountPrice;

    @Basic
    @Column(name="coupon_type")
    @Enumerated(EnumType.STRING)
    CouponTypeEnum couponType;

    @Basic
    @Column(name="coupon_sum")
    Integer couponSum;

    @Basic
    @Column(name="coupon_left")
    Integer couponLeft;


    @Basic
    @Column(name="store_id")
    Integer storeId;//为-1则是全局优惠劵
    public CouponGroupVO toVO()
    { CouponGroupVO couponGroupVO = new CouponGroupVO();
        couponGroupVO.setCoupongroupId(this.coupongroupId);
        couponGroupVO.setCouponGroupName(this.couponGroupName);
        couponGroupVO.setDeadline(this.deadline);
        couponGroupVO.setDiscountRange(this.discountRange);
        couponGroupVO.setDiscountPrice(this.discountPrice);
        couponGroupVO.setCouponType(this.couponType);
        couponGroupVO.setCouponSum(this.couponSum);
        couponGroupVO.setCouponLeft(this.couponLeft);
        couponGroupVO.setStoreId(this.storeId);
        return couponGroupVO;

    }
}
