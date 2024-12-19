package com.seecoder.BlueWhale.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: DingXiaoyu
 * @Date: 0:17 2023/11/26
 *
 * 这个类实现了WebMvcConfigurer接口，
 * 表示会被SpringBoot接受，
 * 这个类的作用是配置拦截器。
 * addInterceptors方法配置了拦截器，
 * 添加了loginInterceptor作为拦截器，
 * 并且设置除了register和login的所有接口都需要通过该拦截器。
*/
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginInterceptor;

    @Autowired
    IsManagerInterceptor isManagerInterceptor;

    @Autowired
    IsStaffInterceptor isStaffInterceptor;

    @Autowired
    IsCustomerInterceptor isCustomerInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/users")
                .addPathPatterns("/api/images")
                .order(1);
        registry.addInterceptor(isManagerInterceptor)
                .addPathPatterns("/api/stores/createStore")
                .order(2);
        registry.addInterceptor(isStaffInterceptor)
                .addPathPatterns("/api/products/createProduct")
                .addPathPatterns("/api/products/addInventory")
                .order(3);
        registry.addInterceptor(isCustomerInterceptor)
                .addPathPatterns("/api/coupons/receiveCoupon")
                .order(4);
    }
}
