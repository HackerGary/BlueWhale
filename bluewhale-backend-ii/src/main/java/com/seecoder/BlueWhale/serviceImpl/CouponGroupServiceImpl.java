package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.enums.CouponTypeEnum;
import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.Coupon;
import com.seecoder.BlueWhale.po.CouponGroup;
import com.seecoder.BlueWhale.repository.CouponGroupRepository;
import com.seecoder.BlueWhale.repository.CouponRepository;
import com.seecoder.BlueWhale.repository.StoreRepository;
import com.seecoder.BlueWhale.service.CouponGroupService;
import com.seecoder.BlueWhale.vo.CommentVO;
import com.seecoder.BlueWhale.vo.CouponGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CouponGroupServiceImpl implements CouponGroupService {
    @Autowired
    CouponGroupRepository couponGroupRepository;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    StoreRepository storeRepository;
    public void check(CouponGroupVO couponGroupVO)
    {
            if(couponGroupVO.getStoreId()!=-1&&storeRepository.findByStoreId(couponGroupVO.getStoreId())==null)
            {   throw BlueWhaleException.ErrorParam();
            }

    }
    @Override
    public Boolean CreateCouponGroup(CouponGroupVO couponGroupVO) {
        check(couponGroupVO);
        CouponGroup couponGroup=couponGroupVO.toPO();
       couponGroupRepository.save(couponGroup);
       return true;
    }

    @Override
    public List<CouponGroupVO> getAllCouponGroup() {
        List<CouponGroup> couponGroups=couponGroupRepository.findAll();
        List<CouponGroupVO> couponGroupVOS = new ArrayList<>();
        for(int i=0;i<couponGroups.size();i++){
            couponGroupVOS.add(couponGroups.get(i).toVO());
        }
        return couponGroupVOS;
    }

    @Override
    public List<CouponGroupVO> getCouponGroupByStoreId(Integer storeId) {
        List<CouponGroup> couponGroups=couponGroupRepository.getCouponGroupByStoreId(storeId);
        List<CouponGroupVO> couponGroupVOS = new ArrayList<>();
        for(int i=0;i<couponGroups.size();i++){
           couponGroupVOS.add(couponGroups.get(i).toVO());
        }
        return couponGroupVOS;
    }

    @Override
    public Boolean ReceiveCoupon(Integer couponGroupId,Integer userId) {

        CouponGroupVO couponGroupVO=couponGroupRepository.getCouponGroupByCoupongroupId(couponGroupId).toVO();
        if(!couponRepository.getCouponByUserIdAndCoupongroupId(userId,couponGroupVO.getCoupongroupId()).isEmpty())
        {
               throw BlueWhaleException.couponAlreadyReceive();
        }

        if(couponGroupVO.getCouponLeft()==0)
        {
                throw BlueWhaleException.couponLeftNotEnough();
        }

        CouponGroup couponGroup=couponGroupVO.toPO();
        couponGroup.setCouponLeft(couponGroupVO.getCouponLeft()-1);
        couponGroupRepository.save(couponGroup);
        Coupon newCoupon=new Coupon();
        newCoupon.setCouponType(couponGroupVO.getCouponType());
        newCoupon.setDiscountRange(couponGroupVO.getDiscountRange());
        newCoupon.setDiscountPrice(couponGroupVO.getDiscountPrice());
        newCoupon.setCoupongroupId(couponGroupVO.getCoupongroupId());
        newCoupon.setStoreId(couponGroupVO.getStoreId());
        newCoupon.setUserId(userId);
        newCoupon.setCouponName(couponGroup.getCouponGroupName());
        newCoupon.setDeadline(couponGroup.getDeadline());
        newCoupon.setValid(true);
        couponRepository.save(newCoupon);
        return true;
    }
}
