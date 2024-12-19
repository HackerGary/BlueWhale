package com.seecoder.BlueWhale.po;

import com.seecoder.BlueWhale.enums.OrderStatusEnum;
import com.seecoder.BlueWhale.enums.PickGoodTypeEnum;
import com.seecoder.BlueWhale.enums.RoleEnum;
import com.seecoder.BlueWhale.vo.OrderVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "`order`",indexes = {@Index(columnList = "user_id"),@Index(columnList = "store_id")})
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name ="order_id")
    private Integer orderId;

    @Basic
    @Column(name="product_id")
    private Integer productId;

    @Basic
    @Column(name="quantity")
    private Integer quantity;

    @Basic
    @Column(name="time")
    private Date time;

    @Basic
    @Column(name="pricesum")
    private Double pricesum;


    @Basic
    @Column(name = "pick_good_type")
    @Enumerated(EnumType.STRING)
    private PickGoodTypeEnum pickGoodType;

    @Basic
    @Column(name = "order_status_enum")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatusEnum;



    @Basic
    @Column(name="receiver")
    private String receiver;


    @Basic
    @Column(name="telephone")
    private String telephone;


    @Basic
    @Column(name="address")
    private String address;

    @Basic
    @Column(name="user_id")
    private Integer userId;

    @Basic
    @Column(name="store_id")
    private Integer storeId;

    @Basic
    @Column(name="product_name")
    private String productName;
    public OrderVO toVO()
    {  OrderVO orderVO=new OrderVO();
       orderVO.setOrderId(this.orderId);
       orderVO.setProductId(this.productId);
       orderVO.setQuantity(this.quantity);
       orderVO.setPricesum(this.pricesum);
       orderVO.setTime(this.time);
       orderVO.setPickGoodType(this.pickGoodType);
       orderVO.setOrderStatusEnum(this.orderStatusEnum);
        orderVO.setReceiver(this.receiver);
        orderVO.setTelephone(this.telephone);
        orderVO.setAddress(this.address);
        orderVO.setUserId(this.userId);
        orderVO.setStoreId(this.storeId);
        orderVO.setProductName(this.productName);
       return orderVO;

    }


}
