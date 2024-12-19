package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.enums.OrderStatusEnum;
import com.seecoder.BlueWhale.po.Order;
import com.seecoder.BlueWhale.po.Product;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
     Order findByOrderId(Integer OrderId);
     List<Order> findByUserId(Integer userId);

     List<Order> findByStoreId(Integer storeId);

     List<Order> findAll();

     List<Order>findByOrderStatusEnum(OrderStatusEnum orderStatus);
}
