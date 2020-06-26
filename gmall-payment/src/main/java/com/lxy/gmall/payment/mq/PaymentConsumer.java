package com.lxy.gmall.payment.mq;

import com.alibaba.dubbo.config.annotation.Reference;

import com.lxy.gmall.bean.PaymentInfo;
import com.lxy.gmall.service.PaymentService;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-25 0:59
 */

@Component
public class PaymentConsumer {

    @Reference
    private PaymentService paymentService;

    //消费检查是否支付成功的消息队列
    @JmsListener(destination = "PAYMENT_RESULT_CHECK_QUEUE",containerFactory = "jmsQueueListener")
    public void consumeSkuDeduct(MapMessage mapMessage) throws JMSException {
       //通过mapMassage获取
        String outTradeNo = mapMessage.getString("outTradeNo");
        int delaySec = mapMessage.getInt("delaySec");
        int checkCount = mapMessage.getInt("checkCount");

        //创建一个paymentInfo对象
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOutTradeNo(outTradeNo);
        //获取orderId
        PaymentInfo paymentInfoQuery = paymentService.getPaymentInfo(paymentInfo);
        //其他参数没有值
        //判断是否支付成功
        boolean result = paymentService.checkPayment(paymentInfoQuery);
        System.out.println("检查结果： "+result);
        //支付失败
        if(!result && checkCount>0){
            System.out.println("检查次数： "+checkCount);
            //调用发送消息的方法即可
            paymentService.sendDelayPaymentResult(outTradeNo, delaySec, checkCount-1);
            //
        }

    }
}
