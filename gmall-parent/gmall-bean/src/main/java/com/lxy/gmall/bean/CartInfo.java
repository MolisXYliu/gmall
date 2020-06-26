package com.lxy.gmall.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-19 21:39
 */

@Data
public class CartInfo implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    String id;
    @Column
    String userId;
    @Column
    String skuId;

    //数据库中的加入购物车时的价格
    @Column
    BigDecimal cartPrice;
    @Column
    Integer skuNum;
    @Column
    String imgUrl;
    @Column
    String skuName;

    // 实时价格 商品详情中价格
    @Transient
    BigDecimal skuPrice;
    // 下订单的时候，商品是否勾选
    @Transient
    String isChecked="0";

}
