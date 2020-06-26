package com.lxy.gmall.payment.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-23 16:46
 */

public class ProducerTest {

    public static void main(String[] args) throws JMSException {
        /*
            1.创建连接工厂
            2.创建链接
            3.打开链接
            4.创建session
            5.创建队列
            6.创建消息提供者
            7.创建消息对象
            8.发送消息
            9.关闭
         */

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.119.133:61616");
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //第一个参数表示是否开启事务
        //第二个参数表示开启/关闭事务的相应配置参数
        //Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);//开启事务必须提交
        Queue lxy = session.createQueue("lxy-true");


        MessageProducer producer = session.createProducer(lxy);
        ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
        activeMQTextMessage.setText("困死了 特别困");
        producer.send(activeMQTextMessage);

        session.commit();
        producer.close();
        session.close();
        connection.close();

    }

}
