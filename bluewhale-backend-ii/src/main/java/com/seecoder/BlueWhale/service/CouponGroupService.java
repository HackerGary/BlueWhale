package com.seecoder.BlueWhale.service;

import com.seecoder.BlueWhale.vo.CouponGroupVO;
import com.seecoder.BlueWhale.vo.CouponVO;

import java.util.List;

public interface CouponGroupService {

    Boolean CreateCouponGroup(CouponGroupVO couponGroupVO);
    List<CouponGroupVO> getAllCouponGroup();

    List<CouponGroupVO> getCouponGroupByStoreId(Integer storeId);

    Boolean ReceiveCoupon(Integer couponGroupId, Integer userId);

}
