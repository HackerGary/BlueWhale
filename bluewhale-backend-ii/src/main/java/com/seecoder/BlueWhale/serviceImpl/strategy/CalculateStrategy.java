package com.seecoder.BlueWhale.serviceImpl.strategy;

import com.seecoder.BlueWhale.vo.CouponVO;

public interface CalculateStrategy {
    Double calculate(CouponVO couponVO, Double price);
}
