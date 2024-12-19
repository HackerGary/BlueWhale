package com.seecoder.BlueWhale.serviceImpl.strategy;

import com.seecoder.BlueWhale.vo.CouponVO;

public class FillReductionCouponCalculateStrategy implements CalculateStrategy{

    @Override
    public Double calculate(CouponVO couponVO, Double price) {
        Double range=couponVO.getDiscountRange();
        if(price>=range){
            return price-couponVO.getDiscountPrice();
        }
        return price;
    }
}
