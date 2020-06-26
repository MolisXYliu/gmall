package com.lxy.gmall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lxy.gmall.bean.CartInfo;
import com.lxy.gmall.bean.SkuInfo;
import com.lxy.gmall.config.CookieUtil;
import com.lxy.gmall.service.ManageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-20 0:19
 */

@Component
public class CartCookieHandler {

    // 定义购物车名称
    private String cookieCartName = "CART";

    // 设置cookie 过期时间
    private int COOKIE_CART_MAXAGE=7*24*3600;

    @Reference
    private ManageService manageService;

    //添加购物车
    public void addToCart(HttpServletRequest request, HttpServletResponse response, String skuId, String userId, int skuNum) {

      /*
        1.先查看购物车中是否有商品
        2.true 数量相加
        3.false 直接添加
       */

        //从cookie中获取购物车数据
        String cookieValue = CookieUtil.getCookieValue(request, cookieCartName, true);
        //声明一个集合
        List<CartInfo> cartInfoList=new ArrayList<>();
        //如果没有则直接添加到集合 借助一个boolean类型的变量来处理
        Boolean ifExist=false;
        //判断cookieValue不能为空
        if(StringUtils.isNotEmpty(cookieValue)){
            //该字符串中包含很多个Cartinfo实体类List<CartInfo>
             cartInfoList = JSON.parseArray(cookieValue, CartInfo.class);
            //判断是否有该商品
            for (CartInfo cartInfo : cartInfoList) {
                //比较添加商品的Id
                if(cartInfo.getSkuId().equals(skuId)){
                    //有商品
                    cartInfo.setSkuNum(cartInfo.getSkuNum()+skuNum);
                    //实时价格初始化
                    cartInfo.setSkuPrice(cartInfo.getCartPrice());
                    //将变量更改为true
                    ifExist=true;
                }
            }

        }

        //最后将所有没找的的商品都加入集合中
        //在购物车中没有该商品
        if(!ifExist){
            SkuInfo skuInfo = manageService.getSkuInfo(skuId);
            //将商品添加到集合中
            CartInfo cartInfo = new CartInfo();
            //属性赋值
            cartInfo.setSkuId(skuId);
            cartInfo.setCartPrice(skuInfo.getPrice());
            cartInfo.setSkuPrice(skuInfo.getPrice());
            cartInfo.setSkuName(skuInfo.getSkuName());
            cartInfo.setImgUrl(skuInfo.getSkuDefaultImg());

            cartInfo.setUserId(userId);
            cartInfo.setSkuNum(skuNum);

            //添加到集合中去
            cartInfoList.add(cartInfo);


        }

        //将最终的集合放入cookie中
        CookieUtil.setCookie(request, response, cookieCartName, JSON.toJSONString(cartInfoList), COOKIE_CART_MAXAGE, true);

    }

    public List<CartInfo> getCartList(HttpServletRequest request) {
        //未登录数据集合
        String cookieValue = CookieUtil.getCookieValue(request, cookieCartName, true);
        if(StringUtils.isNotEmpty(cookieValue)){
            List<CartInfo> cartInfoList = JSON.parseArray(cookieValue, CartInfo.class);
            return cartInfoList;
        }
        return null;
    }

    //删除购物车
    public void deleteCartCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, cookieCartName);
    }

    public void checkCart(HttpServletRequest request, HttpServletResponse response, String skuId, String isChecked) {
        //直接将isChecked值赋给购物车集合
        List<CartInfo> cartList = getCartList(request);
        if(cartList!=null && cartList.size()>0){
            for (CartInfo cartInfo : cartList) {
                if(cartInfo.getSkuId().equals(skuId)){
                    cartInfo.setIsChecked(isChecked);
                }
            }
        }
        //购物车集合写回cookie
        CookieUtil.setCookie(request, response, cookieCartName, JSON.toJSONString(cartList), COOKIE_CART_MAXAGE, true);
    }
}
