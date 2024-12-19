package com.seecoder.BlueWhale.controller;

import com.seecoder.BlueWhale.repository.CouponGroupRepository;
import com.seecoder.BlueWhale.service.CouponGroupService;
import com.seecoder.BlueWhale.service.CouponService;
import com.seecoder.BlueWhale.vo.CouponGroupVO;
import com.seecoder.BlueWhale.vo.CouponVO;
import com.seecoder.BlueWhale.vo.OrderVO;
import com.seecoder.BlueWhale.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    CouponService couponService;

    @Autowired
    CouponGroupService couponGroupService;


    @PostMapping("/createCouponGroup")
    public ResultVO<Boolean> createCouponGroup(@RequestBody CouponGroupVO couponGroupVO)
    {
        return ResultVO.buildSuccess(couponGroupService.CreateCouponGroup(couponGroupVO));
    }

    @GetMapping("/getCouponGroupByStoreId")
    public ResultVO<List<CouponGroupVO>> getCouponGroupByStoreId(@RequestParam("storeId")Integer storeId)
    {
        return ResultVO.buildSuccess(couponGroupService.getCouponGroupByStoreId(storeId));
    }
    @GetMapping("/getAllCouponGroup")
    public ResultVO<List<CouponGroupVO>> getAllCouponGroup()
    {
        return ResultVO.buildSuccess(couponGroupService.getAllCouponGroup());
    }
    @GetMapping("/receiveCoupon")
    public ResultVO<Boolean> receiveCoupon(@RequestParam("couponGroupId")Integer couponGroupId,@RequestParam("userId")Integer userId)
    {
        return ResultVO.buildSuccess(couponGroupService.ReceiveCoupon(couponGroupId,userId));
    }
    @GetMapping("/getCouponByUserId")
    public ResultVO<List<CouponVO>> getCouponByUserId(@RequestParam("userId")Integer userId)
    {
        return ResultVO.buildSuccess(couponService.getCouponByUserId(userId));
    }

    @GetMapping("/getUsefulCouponByUserIdAndStoreId")
    public ResultVO<List<CouponVO>> getUsefulCouponByUserIdAndStoreId(@RequestParam("userId")Integer userId,@RequestParam("storeId")Integer storeId)
    {
        return ResultVO.buildSuccess(couponService.getUsefulCouponByUserIdAndStoreId(userId,storeId));
    }

    @GetMapping("/previewCoupon")
    public ResultVO<Double> previewCoupon(@RequestParam("price")Double price, @RequestParam("couponId")Integer couponId){
        return ResultVO.buildSuccess(couponService.previewCoupon(couponId,price));
    }
}
