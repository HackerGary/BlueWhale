package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.Comment;
import com.seecoder.BlueWhale.po.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon,Integer> {


    List<Coupon> getCouponByUserIdAndCoupongroupId(Integer UserId,Integer CoupongroupId);

    List<Coupon> getCouponByUserId(Integer userId);

    List<Coupon> getCouponByUserIdAndStoreId(Integer userId,Integer storeId);

    Coupon getCouponByCouponId(Integer couponId);


}
