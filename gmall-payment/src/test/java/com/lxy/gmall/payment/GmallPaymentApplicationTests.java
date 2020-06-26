package com.lxy.gmall.payment;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lxy.gmall.config.ActiveMQUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.*;


@SpringBootTest
class GmallPaymentApplicationTests {

    @Autowired
    private ActiveMQUtil activeMQUtil;


    @Test
    void contextLoads() {
    }

    /*@Test
    public void testM() throws JMSException {
        Connection connection = activeMQUtil.getConnection();
        //ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.119.133:61616");
        //Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //第一个参数表示是否开启事务
        //第二个参数表示开启/关闭事务的相应配置参数
        //Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);//开启事务必须提交
        Queue lxy = session.createQueue("lxy-tools");


        MessageProducer producer = session.createProducer(lxy);
        ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
        activeMQTextMessage.setText("睡醒了，要吃饭了！");
        producer.send(activeMQTextMessage);

        session.commit();
        producer.close();
        session.close();
        connection.close();
    }*/

}
