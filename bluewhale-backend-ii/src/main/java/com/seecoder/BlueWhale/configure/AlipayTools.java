package com.seecoder.BlueWhale.configure;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.seecoder.BlueWhale.exception.BlueWhaleException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Getter
@Setter
public class AlipayTools {

    private String serverUrl;

    private String appId;

    private String appPrivateKey;

    private String alipayPublicKey;

    private String notifyUrl;

    private static String format="json";

    private static String charset="utf-8";

    private static String signType="RSA2";

    /**
     * @Author: DingXiaoyu
     * @Date: 11:25 2024/1/31
     * 使用支付宝沙箱
     * 使用时可以根据自己的需要做修改，包括参数名、返回值、具体实现
     * 在bizContent中放入关键的信息：tradeName、price、name
     * 返回的form是一个String类型的html页面
    */
    public String pay(String tradeName, String name , Double price){
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl,appId,appPrivateKey,format,charset,alipayPublicKey,signType);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(notifyUrl);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", tradeName);
        bizContent.put("total_amount", price);
        bizContent.put("subject", name);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());
        String form;
        try  {
            form = alipayClient.pageExecute(request).getBody();
        }  catch  (Exception e) {
            throw BlueWhaleException.payError();
        }
        System.out.println(form);
        return form;
    }

    public boolean refund(String tradeId,Double price){
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl,appId,appPrivateKey,format,charset,alipayPublicKey,signType);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", tradeId);
        bizContent.put("refund_amount", price);
        request.setBizContent(bizContent.toString());
        String result;
        try {
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            result = response.getBody();
            System.out.println(result);
            if(response.isSuccess()){
                System.out.println("退款成功");
                return true;
            } else {
                System.out.println("退款失败");
                throw BlueWhaleException.refundError();
            }
        }catch (Exception e){
            throw BlueWhaleException.refundError();
        }
    }
}