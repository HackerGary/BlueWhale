package com.seecoder.BlueWhale.serviceImpl.strategy;

import com.seecoder.BlueWhale.vo.CouponVO;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @Author: DingXiaoyu
 * @Date: 22:35 2023/12/19
 * “蓝鲸券”使用规则：
 * 0-100元区间打九五折；
 * 100-200元区间打九折；
 * 200-300元区间打八五折；
 * 300-400元区间打八折；
 * 400-500元区间打七五折；
 * 500元以上区间打七折。
 */
public class SpecialCouponCalculateStrategy implements CalculateStrategy {
    Map<Integer, Function<Double, Double>> logicMap = new HashMap<>();

    public SpecialCouponCalculateStrategy() {
        logicMap.put(0, input -> {
            Double x = input * (0.95);
            Double result = Double.parseDouble(String.format("%.2f", x));
            return result;
        });
        logicMap.put(1, input -> {
            Double x = input * (0.90);
            Double result = Double.parseDouble(String.format("%.2f", x));
            return result;
        });
        logicMap.put(2, input -> {
            Double x = input * (0.85);
            Double result = Double.parseDouble(String.format("%.2f", x));
            return result;
        });
        logicMap.put(3, input -> {
            Double x = input * (0.80);
            Double result = Double.parseDouble(String.format("%.2f", x));
            return result;
        });
        logicMap.put(4, input -> {
            Double x = input * (0.75);
            Double result = Double.parseDouble(String.format("%.2f", x));
            return result;
        });
        logicMap.put(5, input -> {
            Double x = input * (0.70);
            Double result = Double.parseDouble(String.format("%.2f", x));
            return result;
        });
    }


    @Override
    public Double calculate(CouponVO couponVO, Double price) {

        int num;
        num = (int) (price / 100);

        num = Math.min(num, 5);

        return logicMap.get(num).apply(price);
    }
}
