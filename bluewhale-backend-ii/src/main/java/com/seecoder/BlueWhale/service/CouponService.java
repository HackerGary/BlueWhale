package com.seecoder.BlueWhale.service;

import com.seecoder.BlueWhale.vo.CouponVO;

import java.util.List;

public interface CouponService {
   Double previewCoupon(Integer couponId,Double price);

    List <CouponVO>  getCouponByUserId(Integer userId);

    List <CouponVO>  getUsefulCouponByUserIdAndStoreId(Integer userId,Integer storeId);




}
