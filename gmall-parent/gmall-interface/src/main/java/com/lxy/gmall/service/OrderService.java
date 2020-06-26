package com.lxy.gmall.service;

import com.lxy.gmall.bean.OrderInfo;
import com.lxy.gmall.bean.enums.ProcessStatus;

import java.util.List;
import java.util.Map;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-21 11:17
 */

public interface OrderService {
    //保存订单
    String saveOrder(OrderInfo orderInfo);

    //生成流水号
    String getTradeNo(String userId);

    //比较流水号
    boolean checkTradeCode(String userId,String tradeCodeNo);

    //删除流水号
    void  delTradeCode(String userId);

    //查询是否有足够的库存
    boolean checkStock(String skuId, Integer skuNum);

    //通过orderId查询订单金额
    OrderInfo getOrderInfo(String orderId);

    //更新订单状态
    void updateOrderStatus(String orderId, ProcessStatus processStatus);

    //发送消息给库存
    void sendOrderStatus(String orderId);

    //查询过期订单
    List<OrderInfo> getExpiredOrderList();


    //处理过期订单
    void execExpiredOrder(OrderInfo orderInfo);

    //将orderInfo转换为map
    Map initWareOrder(OrderInfo orderInfo);

    //拆单接口
    List<OrderInfo> orderSplit(String orderId, String wareSkuMap);
}
