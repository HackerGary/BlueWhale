package com.seecoder.BlueWhale.controller;

import com.seecoder.BlueWhale.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ali")
public class AliPayController {
    @Autowired
    OrderService orderService;

    @PostMapping("/notify")
    public void notify(HttpServletRequest httpServletRequest){
        if (httpServletRequest.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = httpServletRequest.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, httpServletRequest.getParameter(name));
            }
            String tradeName = params.get("out_trade_no");
            Double paid = Double.parseDouble(params.get("total_amount"));
            //todo: 在这里完成回调接口
            orderService.PaySuccess(Integer.parseInt(tradeName));

                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
        }
    }

}
