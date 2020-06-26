package com.lxy.gmall.payment.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.zookeeper.server.quorum.LearnerSyncRequest;

import javax.jms.*;

/**
 * @author 茉莉的小棉袄
 * @create 2020-06-23 18:32
 */

public class ConsumerTest {

    public static void main(String[] args) throws JMSException {

         /*
            1.创建连接工厂
            2.创建链接
            3.打开链接
            4.创建session
            5.创建队列
            6.创建消息消费者
            7.消费消息
         */


        //创建工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://192.168.119.133:61616");
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //第一个参数表示是否开启事务
        //第二个参数表示开启/关闭事务的相应配置参数
        //Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);//开启事务必须提交
        Queue lxy = session.createQueue("lxy-true");

        MessageConsumer consumer = session.createConsumer(lxy);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //如何将消息获取到
                if(message instanceof  TextMessage){
                    try {
                        String text = ((TextMessage) message).getText();
                        System.out.println("获取的而消息："+text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

    }
}
