package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.enums.OrderStatusEnum;
import com.seecoder.BlueWhale.po.Order;
import com.seecoder.BlueWhale.repository.OrderRepository;
import com.seecoder.BlueWhale.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@EnableScheduling
@Component
public class OrderCancelSchedule {
    @Autowired
     OrderService orderService;

    @Autowired
     OrderRepository orderRepository;

    long day=86400000;
    @Scheduled(cron = "0 0/1 * * * ?")
    public void cancelUnpaidOrders() {
        List<Order> unpaidOrders = orderRepository.findByOrderStatusEnum(OrderStatusEnum.UNPAID);
        unpaidOrders.forEach(order -> {
            if (order.getTime().getTime()+14*day<new Date().getTime()) {
                orderRepository.delete(order);
            }
        });

    }
}
