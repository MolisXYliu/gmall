package com.lxy.gmall.service;

import com.lxy.gmall.bean.PaymentInfo;

import java.util.Map;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-22 20:12
 */

public interface PaymentService {

    //保存交易记录
    void savePaymentInfo(PaymentInfo paymentInfo);

    //根据out_trade_no查询
    PaymentInfo getPaymentInfo(PaymentInfo paymentInfoQuery);

    //更新订单状态
    void updatePaymentInfo(String out_trade_no, PaymentInfo paymentInfo);

    //退款
    boolean refund(String orderId);

    //微信支付
    Map createNative(String orderId, String s);

    //消息对列 发送消息给订单
    void sendPaymentResult(PaymentInfo paymentInfo,String result);

    //根据out_trade_no查询交易记录
    boolean checkPayment(PaymentInfo paymentInfoQuery);

    /**
     *  每隔15s主动去支付宝询问该笔订单是否支付成功
     * @param outTradeNo 第三方交易编号
     * @param delaySec   每隔多长时间查询一次
     * @param checkCount 查询的次数
     */
    void sendDelayPaymentResult(String outTradeNo,int delaySec ,int checkCount);

    //根据订单Id关闭交易记录的状态
    void closePayment(String orderId);
}
