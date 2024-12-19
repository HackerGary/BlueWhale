package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.enums.CouponTypeEnum;
import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.Coupon;
import com.seecoder.BlueWhale.po.CouponGroup;
import com.seecoder.BlueWhale.po.Order;
import com.seecoder.BlueWhale.repository.CouponRepository;
import com.seecoder.BlueWhale.repository.OrderRepository;
import com.seecoder.BlueWhale.service.CouponService;
import com.seecoder.BlueWhale.serviceImpl.strategy.*;
import com.seecoder.BlueWhale.vo.CouponGroupVO;
import com.seecoder.BlueWhale.vo.CouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponRepository couponRepository;
    @Autowired
    OrderRepository orderRepository;
    SpecialCouponCalculateStrategy specialCouponCalculateStrategy=new SpecialCouponCalculateStrategy();
   FillReductionCouponCalculateStrategy fillReductionCouponCalculateStrategy=new FillReductionCouponCalculateStrategy();

    @Override
    public Double previewCoupon(Integer couponId, Double price) {
        CouponVO couponVO=couponRepository.getCouponByCouponId(couponId).toVO();
    if(couponVO.getCouponType()==CouponTypeEnum.SPECIAL){
        return specialCouponCalculateStrategy.calculate(couponVO,price);
    }
        return fillReductionCouponCalculateStrategy.calculate(couponVO,price);
    }

    @Override
    public List<CouponVO> getCouponByUserId(Integer userId) {
        List<Coupon> coupon=couponRepository.getCouponByUserId(userId);
        List<CouponVO> couponVO = new ArrayList<>();
        for(int i=0;i<coupon.size();i++){
            couponVO.add(coupon.get(i).toVO());
        }
        return couponVO;

    }

    @Override
    public List<CouponVO> getUsefulCouponByUserIdAndStoreId(Integer userId, Integer storeId) {
        List<CouponVO> couponVO = new ArrayList<>();
        List<Coupon> coupon=couponRepository.getCouponByUserIdAndStoreId(userId,storeId);

        for(int i=0;i<coupon.size();i++){
            if(coupon.get(i).getDeadline().after(new Date())&&coupon.get(i).getValid()) {
                couponVO.add(coupon.get(i).toVO());
            }
        }
        List<Coupon> allcoupon=couponRepository.getCouponByUserIdAndStoreId(userId,-1);
        for(int i=0;i<allcoupon.size();i++){
            if(allcoupon.get(i).getDeadline().after(new Date())&&allcoupon.get(i).getValid()) {
                couponVO.add(allcoupon.get(i).toVO());
            }
        }
        return couponVO;
    }
}
