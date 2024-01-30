package com.hy.shop.order.model.vo;

import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Builder
@Getter
@Alias("Order")
public class Order {
    private int orderId;
    private Date orderDate;
    private int quantity;
    private int shippingFee;
    private int totalPrice;
    private String shippingMemo;
    private String payMethod;
    private String orderStatus;
    private String invoiceNumber;
    private int memberNo;
    private int addrId;
    private String impUid;
    private int couponId;
    private int cartId;
    private String memberNickname;
    private String address;
}
