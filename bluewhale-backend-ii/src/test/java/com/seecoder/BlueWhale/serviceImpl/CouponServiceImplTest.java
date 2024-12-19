package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.enums.CouponTypeEnum;
import com.seecoder.BlueWhale.po.Coupon;
import com.seecoder.BlueWhale.repository.CouponRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CouponServiceImplTest {

    @InjectMocks
    CouponServiceImpl couponService;

    @Mock
    CouponRepository couponRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void previewCoupon() {
        int couponId = 1;
        Double price = 100.0;
        Double res = 90.0;
        Coupon blueWhaleCoupon = new Coupon();
        blueWhaleCoupon.setCouponType(CouponTypeEnum.SPECIAL);
        Coupon fillReducCoupon = new Coupon();
        fillReducCoupon.setCouponType(CouponTypeEnum.FULL_REDUCTION);
        fillReducCoupon.setDiscountRange(100.0);
        fillReducCoupon.setDiscountPrice(10.0);

        //Mock Client
        when(couponRepository.getCouponByCouponId(1)).thenReturn(blueWhaleCoupon);
        when(couponRepository.getCouponByCouponId(2)).thenReturn(fillReducCoupon);

        Assert.assertEquals(res,couponService.previewCoupon(1,price));
        Assert.assertEquals(res,couponService.previewCoupon(2,price));
    }
}