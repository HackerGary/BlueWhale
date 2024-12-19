package com.seecoder.BlueWhale;

import com.seecoder.BlueWhale.enums.OrderStatusEnum;
import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.repository.*;
import com.seecoder.BlueWhale.serviceImpl.*;
import com.seecoder.BlueWhale.util.TokenUtil;
import com.seecoder.BlueWhale.vo.OrderVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class BlueWhaleOrderTests {

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
    OrderRepository orderRepository;
    @Autowired
    OrderServiceImpl orderService;

    @Test
    @Order(0)
    public void initTest() {//init to clear database

        orderRepository.deleteAllInBatch();
    }

    @Test
    @Order(1)
    public void createOrderTests() {

        OrderVO orderVO = new OrderVO();
        orderVO.setOrderStatusEnum(OrderStatusEnum.UNPAID);
        orderVO.setQuantity(30);
        orderVO.setProductId(1);
        orderVO.setPricesum(300.0);
        orderVO.setProductName("delicious_cake");
        orderVO.setAddress("home");
        orderVO.setTelephone("13487343258");
        orderVO.setTime(new Date());
        orderVO.setReceiver("zinc");
        Assertions.assertEquals(orderService.CreateOrder(orderVO), 1);
    }

    @Test
    @Order(2)
    public void DealOrderTests() {
        orderService.PayOrder(1,1);
        orderService.PaySuccess(1);
        try {
            orderService.ReceiveOrder(1);
        } catch (BlueWhaleException ErrorStatus) {
            Assertions.assertEquals("状态错误!", ErrorStatus.getMessage());
        }
        orderService.DeliverOrder(1);
        Assertions.assertEquals(orderRepository.findByOrderId(1).getOrderStatusEnum(),OrderStatusEnum.UNGET);
        orderService.ReceiveOrder(1);
        Assertions.assertEquals(orderRepository.findByOrderId(1).getOrderStatusEnum(),OrderStatusEnum.UNCOMMENT);
    }
}
