package com.seecoder.BlueWhale.controller;

import com.seecoder.BlueWhale.service.OrderService;
import com.seecoder.BlueWhale.vo.OrderVO;
import com.seecoder.BlueWhale.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("/createOrder")
    public ResultVO<Integer> CreateOrder(@RequestBody OrderVO orderVO)
       {
           return ResultVO.buildSuccess(orderService.CreateOrder(orderVO));
       }
    @GetMapping("/payOrder")
    public ResultVO<String>PayOrder(@RequestParam("orderId")Integer orderId,@RequestParam("couponId")Integer couponId)
    {
        return ResultVO.buildSuccess(orderService.PayOrder(orderId,couponId));
    }
    @GetMapping("/deliverOrder")
    public ResultVO<Boolean> DeliverOrder(@RequestParam("orderId")Integer orderId)
    {
        return ResultVO.buildSuccess(orderService.DeliverOrder(orderId));
    }
    @GetMapping("/receiveOrder")
    public ResultVO<Boolean> ReceiveOrder(@RequestParam("orderId")Integer orderId)
    {
        return ResultVO.buildSuccess(orderService.ReceiveOrder(orderId));
    }

    @GetMapping("/refundOrder")
    public ResultVO<Boolean> RefundOrder(@RequestParam("orderId")Integer orderId)
    {
        return ResultVO.buildSuccess((orderService.RefundOrder(orderId)));
    }
    @GetMapping("/getAllOrder")
    public ResultVO<List<OrderVO>> getAllOrder()
    {return ResultVO.buildSuccess(orderService.getAllOrder());}
    @GetMapping("/getOrderByUserId")
    public ResultVO<List<OrderVO>> getOrderByUserId(@RequestParam("userId") Integer userId)
    {return ResultVO.buildSuccess(orderService.getOrderByUserId(userId));}
    @GetMapping("/getOrderByStoreId")
    public ResultVO<List<OrderVO>> getOrderByStoreId(@RequestParam("storeId")Integer storeId)
    {
        return ResultVO.buildSuccess(orderService.getOrderByStoreId(storeId));
    };
    @GetMapping("/getOrderStatement")
    public ResultVO<String> getOrderStatement(@RequestParam("userId")Integer userId) throws IOException {
        return ResultVO.buildSuccess(orderService.getOrderStatement(userId));
    }
}