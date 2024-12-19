package com.seecoder.BlueWhale;

import com.seecoder.BlueWhale.enums.GoodTypeEnum;
import com.seecoder.BlueWhale.enums.RoleEnum;
import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.po.Store;
import com.seecoder.BlueWhale.po.User;
import com.seecoder.BlueWhale.repository.*;
import com.seecoder.BlueWhale.service.StoreService;
import com.seecoder.BlueWhale.service.UserService;
import com.seecoder.BlueWhale.serviceImpl.OrderServiceImpl;
import com.seecoder.BlueWhale.serviceImpl.ProductServiceImpl;
import com.seecoder.BlueWhale.serviceImpl.StoreServiceImpl;
import com.seecoder.BlueWhale.serviceImpl.UserServiceImpl;
import com.seecoder.BlueWhale.util.TokenUtil;
import com.seecoder.BlueWhale.vo.ProductVO;
import org.aspectj.lang.annotation.Before;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class BlueWhaleApplicationTests {

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
	OrderServiceImpl orderService;

	@Test
	@Order(0)
    public void initTest()  {//init to clear database

	}
	@Test
	@Order(1)
	void exampleTest() throws Exception {
		userRepository.deleteAllInBatch();
		User user=new User();
		user.setId(1);
		user.setPhone("19552087876");
		user.setRole(RoleEnum.CUSTOMER);
		user.setCreateTime(new Date(System.currentTimeMillis()));
		user.setPassword("IyCNI9xbl3/RiOUBG/XTMw==");//这是密码123456加密后的密文
		Assert.assertTrue(userService.register(user.toVO()));
		String token = userService.login("19552087876","IyCNI9xbl3/RiOUBG/XTMw==");
		User token_user = tokenUtil.getUser(token);
		System.out.println(token_user.getPhone());
	}
	@Test
	@Order(2)
	void createStoreTest(){
		storeRepository.deleteAllInBatch();

		Store store = new Store();
		store.setStoreId(1);
		store.setIntroduction("This is a store");
		store.setName("store1");
		store.setLogoUrl("no_store_url");
		Assert.assertTrue(storeService.createStore(store.toVO()));

		Store shop = new Store();
		store.setStoreId(2);
		shop.setIntroduction("This is a store");
		shop.setName("store2");
		shop.setLogoUrl("no_store_url");
		Assert.assertTrue(storeService.createStore(shop.toVO()));

	}

	@Test
	@Order(3)
	void createProduct(){
		productRepository.deleteAllInBatch();

		Product product1 = new Product();
		product1.setName("coke");
		product1.setIntroduction("a very nice drink");
		product1.setQuantity(100);
		product1.setType(GoodTypeEnum.FOOD);
		product1.setPrice(3.0);
		product1.setStoreId(1);
		product1.setScore(0.0);
		product1.setTimes(0);


		Product product2 = new Product();
		product2.setName("juice");
		product2.setIntroduction("a very nice drink");
		product2.setQuantity(100);
		product2.setType(GoodTypeEnum.FOOD);
		product2.setPrice(3.);
		product2.setStoreId(2);
		product2.setScore(0.0);
		product2.setTimes(0);

		Product product3 = new Product();
		product3.setName("football");
		product3.setIntroduction("Chinese goot at");
		product3.setType(GoodTypeEnum.SPORTS);
		product3.setPrice(500.0);
		product3.setQuantity(10);
		product3.setStoreId(1);
		product3.setScore(0.0);
		product3.setTimes(0);

		Product product4 = new Product();
		product4.setName("football");
		product4.setIntroduction("Chinese goot at");
		product4.setType(GoodTypeEnum.SPORTS);
		product4.setPrice(500.0);
		product4.setQuantity(10);
		product4.setStoreId(2);
		product4.setScore(0.0);
		product4.setTimes(0);

		Product product5 = new Product();
		product5.setName("football");
		product5.setIntroduction("Chinese goot at");
		product5.setType(GoodTypeEnum.SPORTS);
		product5.setPrice(500.0);
		product5.setQuantity(10);
		product5.setStoreId(2);
		product5.setScore(0.0);
		product5.setTimes(0);

		Product product6 = new Product();
		product6.setName("basketball");
		product6.setIntroduction("Chinese goot at");
		product6.setType(GoodTypeEnum.SPORTS);
		product6.setPrice(500.0);
		product6.setQuantity(10);
		product6.setStoreId(2);
		product6.setScore(0.0);
		product6.setTimes(0);


		//create coke for store1
		productService.createProduct(product1.toVO(),new ArrayList<>());

		//create juice for store2
		productService.createProduct(product2.toVO(),new ArrayList<>());

		//create football for store1
		productService.createProduct(product3.toVO(),new ArrayList<>());

		//create football for store2 : test create same product for different store
		productService.createProduct(product4.toVO(),new ArrayList<>());

		//create football for store2 : test create same product for same store
		try{
			productService.createProduct(product5.toVO(),new ArrayList<>());
		}catch(BlueWhaleException productAlreadyExists){
			Assert.assertEquals("商品已经存在!",productAlreadyExists.getMessage());
		}

		//create basketball for store2, staff at different store : test create product for staff not belong
		try {
			productService.createProduct(product6.toVO(),new ArrayList<>());
		}catch (BlueWhaleException notYourStore){
			Assert.assertEquals("这不是你的商店！",notYourStore.getMessage());
		}

		List<ProductVO> productVOList = productService.getProductListByStoreId(1);
		String productNameStr = "";
		for (int i = 0;i<productVOList.size();i++){
			productNameStr+=productVOList.get(i).getName();
		}
		Assert.assertEquals("cokefootball",productNameStr);

	}


}
