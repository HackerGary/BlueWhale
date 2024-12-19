package com.seecoder.BlueWhale.service;

import com.seecoder.BlueWhale.po.Order;
import com.seecoder.BlueWhale.vo.CommentVO;
import com.seecoder.BlueWhale.vo.OrderVO;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface OrderService {
      Integer CreateOrder(OrderVO orderVO);

      String PayOrder(Integer orderId,Integer couponId);

      void PaySuccess(Integer orderId);
      Boolean DeliverOrder(Integer orderId);

      Boolean ReceiveOrder(Integer orderId);

      Boolean RefundOrder(Integer orderId);

      List<OrderVO> getAllOrder();
      List<OrderVO> getOrderByUserId(Integer userId);
      List<OrderVO> getOrderByStoreId(Integer storeId);

      String getOrderStatement(Integer userId) throws IOException;
}
