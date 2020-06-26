package com.lxy.gmall.order.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lxy.gmall.bean.*;
import com.lxy.gmall.config.LoginRequire;
import com.lxy.gmall.service.CartService;
import com.lxy.gmall.service.ManageService;
import com.lxy.gmall.service.OrderService;
import com.lxy.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-10 9:38
 */

@Controller
public class Ordercontroller {

 /*   @RequestMapping("trade")
    public String trade(){
        //返回一个视图名称叫index.html
        return "index";

    }*/


    //@Autowired
    @Reference
    UserService userService;

    @Reference
    private CartService cartService;

    @Reference
    private OrderService orderService;

    @Reference
    private ManageService manageService;

    @RequestMapping("trade")
   // @ResponseBody//返回json字符串 fastJson.jar 直接将数据显示到页面
    @LoginRequire(autoRedirect = true)
    public String trade(HttpServletRequest request){
        String userId = (String) request.getAttribute("userId");
        //返回一个视图名称叫index.html
        //return userService.getUserAddressList(userId);
        List<UserAddress> userAddressList = userService.getUserAddressList(userId);
        request.setAttribute("userAddressList",userAddressList);

        //展示送货清单
        //数据来源 勾选的购物车 user:1:checked
        List<CartInfo>cartInfoList=cartService.getCartCheckedList(userId);
        //声明一个集合来存储订单明细
        ArrayList<OrderDetail> orderDetailList = new ArrayList<>();
        //将集合数据赋值OrderDetail
        for (CartInfo cartInfo : cartInfoList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setSkuId(cartInfo.getSkuId());
            orderDetail.setSkuName(cartInfo.getSkuName());
            orderDetail.setImgUrl(cartInfo.getImgUrl());
            orderDetail.setSkuNum(cartInfo.getSkuNum());
            orderDetail.setOrderPrice(cartInfo.getCartPrice());
            orderDetailList.add(orderDetail);

        }

        //总金额
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderDetailList(orderDetailList);
        //调用计算总金额的方法
        orderInfo.sumTotalAmount();

        request.setAttribute("totalAmount", orderInfo.getTotalAmount());
        //保存送货清单集合
        request.setAttribute("orderDetailList",orderDetailList);

        String tradeNo = orderService.getTradeNo(userId);
        request.setAttribute("tradeNo",tradeNo);
        return "trade";
    }

    //http://order.gmall.com/submitOrder
    @RequestMapping("submitOrder")
    @LoginRequire(autoRedirect = true)
    public String submitOrder(HttpServletRequest request,OrderInfo orderInfo){

        String userId = (String) request.getAttribute("userId");
        //orderInfo中还缺少orderId
        orderInfo.setUserId(userId);

        //判断是否是重复提交
        //先获取页面的流水号
        String tradeNo = (String) request.getParameter("tradeNo");
        //调用比较方法
        boolean result = orderService.checkTradeCode(userId, tradeNo);

        //是重复提交
        if(!result){
            request.setAttribute("errMsg", "订单已提交，不能重复提交！");
            return "tradeFail";
        }

        //验证库存 /hasStock?skuId=10221&num=2
        List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList) {
            boolean flag = orderService.checkStock(orderDetail.getSkuId(), orderDetail.getSkuNum());
            if(!flag){
                request.setAttribute("errMsg", orderDetail.getSkuName()+"商品库存不足！");
                return "tradeFail";
            }
            //获取skuInfo对象
            SkuInfo skuInfo = manageService.getSkuInfo(orderDetail.getSkuId());
            int res = skuInfo.getPrice().compareTo(orderDetail.getOrderPrice());
            if(res!=0){
                request.setAttribute("errMsg", orderDetail.getSkuName()+"商品价格不匹配！");
                //重新查询实时价格并修改缓存
                cartService.loadCartCache(userId);
                return "tradeFail";
            }





//            BigDecimal res = skuInfo.getPrice().subtract(orderDetail.getOrderPrice());
//            if(res.equals(0)){
//
//            }

        }




        //调用服务层
        String orderId=orderService.saveOrder(orderInfo);

        //删除流水号
        orderService.delTradeCode(userId);
        //支付
        return "redirect://payment.gmall.com/index?orderId="+orderId;
    }

    //http://order.gmall.com/orderSplit
    @RequestMapping("orderSplit")
    @ResponseBody
    public String orderSplit(HttpServletRequest request){
        String orderId = request.getParameter("orderId");
        String wareSkuMap = request.getParameter("wareSkuMap");
        //返回子订单集合
        List<OrderInfo> orderInfoList=orderService.orderSplit(orderId,wareSkuMap);

        //创建一个集合来存储map
        ArrayList<Map> mapArrayList = new ArrayList<>();
        //循环遍历
        for (OrderInfo orderInfo : orderInfoList) {
            Map map = orderService.initWareOrder(orderInfo);
            mapArrayList.add(map);
        }
        return JSON.toJSONString(mapArrayList);
    }






}
