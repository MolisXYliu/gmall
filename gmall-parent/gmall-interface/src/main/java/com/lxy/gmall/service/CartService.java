package com.lxy.gmall.service;

import com.lxy.gmall.bean.CartInfo;

import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-19 21:44
 */

public interface CartService {

    //写方法 skuNum skuId userId
    void  addToCart(String skuId,String userId,Integer skuNum);

    //根据用户Id查询购物车数据
    List<CartInfo> getCartList(String userId);

    //合并购物车
    List<CartInfo> mergeToCartList(List<CartInfo> cartListCK, String userId);

    //修改商品状态
    void checkCart(String skuId, String isChecked, String userId);

    //根据用户Id查询购物车列表
    List<CartInfo> getCartCheckedList(String userId);

    //通过userId查询实时价格
    List<CartInfo> loadCartCache(String userId);
}
