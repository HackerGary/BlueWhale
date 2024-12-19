package com.seecoder.BlueWhale;

import com.seecoder.BlueWhale.enums.CouponTypeEnum;
import com.seecoder.BlueWhale.enums.RoleEnum;
import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.CouponGroup;
import com.seecoder.BlueWhale.po.Store;
import com.seecoder.BlueWhale.po.User;
import com.seecoder.BlueWhale.repository.*;
import com.seecoder.BlueWhale.service.CouponGroupService;
import com.seecoder.BlueWhale.serviceImpl.*;
import com.seecoder.BlueWhale.util.TokenUtil;
import com.seecoder.BlueWhale.vo.CouponGroupVO;
import com.seecoder.BlueWhale.vo.ProductVO;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class BlueWhaleCouponTests {
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    StoreServiceImpl storeService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    CouponGroupRepository couponGroupRepository;
    @Autowired
    CouponServiceImpl couponService;
    @Autowired
    CouponGroupServiceImpl couponGroupService;

    @Test
    @Order(0)
    public void initTest(){//init to clear database
        couponRepository.deleteAllInBatch();
        couponGroupRepository.deleteAllInBatch();
        storeRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }
    @Test
    @Order(1)
    public void CouponGroupTest() throws Exception {
        User user1=new User();
        user1.setId(1);
        user1.setPhone("19552087876");
        user1.setRole(RoleEnum.CUSTOMER);
        user1.setCreateTime(new Date(System.currentTimeMillis()));
        user1.setPassword("123456");
        userService.register(user1.toVO());

        User user2=new User();
        user2.setId(2);
        user2.setPhone("13333333333");
        user2.setRole(RoleEnum.CUSTOMER);
        user2.setCreateTime(new Date(System.currentTimeMillis()));
        user2.setPassword("123456");
        userService.register(user2.toVO());

        Store store1 = new Store();
        store1.setStoreId(1);
        store1.setName("华硕旗舰店");
        store1.setLogoUrl(null);
        store1.setIntroduction("nothing");
         storeService.createStore(store1.toVO());


        CouponGroupVO couponGroup1=new CouponGroupVO();
        couponGroup1.setCouponGroupName("蓝鲸特惠券组");
        couponGroup1.setCoupongroupId(1);
        couponGroup1.setDeadline(new Date());
        couponGroup1.setDiscountRange(null);
        couponGroup1.setDiscountPrice(null);
        couponGroup1.setCouponType(CouponTypeEnum.SPECIAL);
        couponGroup1.setCouponSum(1);
        couponGroup1.setCouponLeft(1);
        couponGroup1.setStoreId(-1);

        CouponGroupVO couponGroup2=new CouponGroupVO();
        couponGroup2.setCouponGroupName("华硕商店满减券组1");
        couponGroup2.setCoupongroupId(2);
        couponGroup2.setDeadline(new Date());
        couponGroup2.setDiscountRange(100.00);
        couponGroup2.setDiscountPrice(20.00);
        couponGroup2.setCouponType(CouponTypeEnum.FULL_REDUCTION);
        couponGroup2.setCouponSum(200);
        couponGroup2.setCouponLeft(200);
        couponGroup2.setStoreId(1);

        CouponGroupVO couponGroup3=new CouponGroupVO();
        couponGroup3.setCouponGroupName("华硕商店满减券组2");
        couponGroup3.setCoupongroupId(3);
        couponGroup3.setDeadline(new Date());
        couponGroup3.setDiscountRange(200.00);
        couponGroup3.setDiscountPrice(25.00);
        couponGroup3.setCouponType(CouponTypeEnum.FULL_REDUCTION);
        couponGroup3.setCouponSum(30);
        couponGroup3.setCouponLeft(30);
        couponGroup3.setStoreId(1);

        Assertions.assertTrue(couponGroupService.CreateCouponGroup(couponGroup1));
        Assertions.assertTrue(couponGroupService.CreateCouponGroup(couponGroup2));
        Assertions.assertTrue(couponGroupService.CreateCouponGroup(couponGroup3));

        List<CouponGroupVO> couponGroupVOS1 = couponGroupService.getCouponGroupByStoreId(1);
        String CouponNameStr1 = "";
        for (int i = 0;i<couponGroupVOS1.size();i++){
           CouponNameStr1+=couponGroupVOS1.get(i).getCouponGroupName();
           CouponNameStr1+="\n";
        }
        System.out.println("华硕旗舰店优惠券:\n");
        System.out.println(CouponNameStr1);

        List<CouponGroupVO> couponGroupVOS2 = couponGroupService.getCouponGroupByStoreId(-1);
        String CouponNameStr2 = "";
        for (int i = 0;i<couponGroupVOS2.size();i++){
            CouponNameStr2+=couponGroupVOS2.get(i).getCouponGroupName();
            CouponNameStr2+="\n";
        }
        System.out.println("全局优惠券:\n");
        System.out.println(CouponNameStr2);
        couponGroupService.ReceiveCoupon(1,1);//先让用户1领取一张蓝鲸优惠券，蓝鲸优惠券数量清零
        couponGroupService.ReceiveCoupon(2,1);

    }
    @Test
    @Order(2)
    public void CouponTest(){
        try{
            couponGroupService.ReceiveCoupon(1,1);
        }catch(BlueWhaleException couponAlreadyReceive){
            Assertions.assertEquals("此优惠券已经领取过!",couponAlreadyReceive.getMessage());
        }
        try{
            couponGroupService.ReceiveCoupon(1,2);
        }catch(BlueWhaleException couponLeftNotEnough){
            Assertions.assertEquals("优惠券已被一抢而空!",couponLeftNotEnough.getMessage());
        }

    }
    @Test
    @Order(3)
    public void CouponCountTest(){

        Assertions.assertEquals(couponService.previewCoupon(1,100.0),90.0);
        Assertions.assertEquals(couponService.previewCoupon(1,500.0),350.0);
        Assertions.assertEquals(couponService.previewCoupon(1,1000.0),700.0);
        Assertions.assertEquals(couponService.previewCoupon(1,250.0),250.0*0.85);
        Assertions.assertEquals(couponService.previewCoupon(2,1000.0),980.0);
    }
}
