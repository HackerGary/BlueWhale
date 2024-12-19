package com.seecoder.BlueWhale.vo;

import com.seecoder.BlueWhale.enums.OrderStatusEnum;
import com.seecoder.BlueWhale.enums.PickGoodTypeEnum;
import com.seecoder.BlueWhale.po.Order;
import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.repository.ProductRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OrderVO {

    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Double pricesum;
    private Date time;
    private PickGoodTypeEnum pickGoodType;
    private OrderStatusEnum orderStatusEnum;
    private String receiver;
    private String telephone;
    private String address;
    private Integer userId;
    private Integer storeId;
    private String productName;


    public Order toPO(){
        Order order = new Order();
        order.setOrderId(this.orderId);
        order.setProductId(this.productId);
        order.setQuantity(this.quantity);
        order.setPricesum(this.pricesum);
        order.setTime(this.time);
        order.setPickGoodType(this.pickGoodType);
        order.setOrderStatusEnum(this.orderStatusEnum);
        order.setReceiver(this.receiver);
        order.setTelephone(this.telephone);
        order.setAddress(this.address);
        order.setUserId(this.userId);
        order.setStoreId(this.storeId);
        order.setProductName(this.productName);
        return order;
    }
}
