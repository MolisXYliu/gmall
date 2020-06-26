package com.lxy.gmall.order.mq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lxy.gmall.bean.enums.ProcessStatus;
import com.lxy.gmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-23 21:42
 */

@Component
public class OrderConsumer {

    @Reference
    private OrderService orderService;

    //获取消息队列中的数据
    //表示监听的队列名
    @JmsListener(destination = "PAYMENT_RESULT_QUEUE",containerFactory = "jmsQueueListener")
    public void consumerPaymentResult(MapMessage mapMessage) throws JMSException {
        //通过mapMessage获取
        String orderId = mapMessage.getString("orderId");
        String result = mapMessage.getString("result");

        //支付成功
        if("success".equals(result)){
            //更新订单状态
            orderService.updateOrderStatus(orderId, ProcessStatus.PAID);
            //发送消息给库存
            orderService.sendOrderStatus(orderId);
            //更新订单状态
            orderService.updateOrderStatus(orderId, ProcessStatus.NOTIFIED_WARE);
        }
    }

    @JmsListener(destination = "SKU_DEDUCT_QUEUE",containerFactory = "jmsQueueListener")
    public void consumeSkuDeduct(MapMessage mapMessage) throws JMSException {
        String orderId = mapMessage.getString("orderId");
        String status = mapMessage.getString("status");
        if("DEDUCTED".equals(status)){
            orderService.updateOrderStatus(orderId, ProcessStatus.DELEVERED);
        }else{
            orderService.updateOrderStatus(orderId, ProcessStatus.STOCK_EXCEPTION);
        }
    }


}
