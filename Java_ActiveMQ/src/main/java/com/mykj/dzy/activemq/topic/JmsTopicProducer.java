package com.mykj.dzy.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsTopicProducer {

    public static final String ACTIVEMQ_URL = "tcp://192.168.181.129:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException {
        sendTopic();
    }

    public static void sendTopic() throws JMSException {
        //创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //通过连接工厂获得connection并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        //启动连接
        connection.start();
        //创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建消息发送的目的地
        Topic topic = session.createTopic(TOPIC_NAME);
        //创建消息的发送者
        MessageProducer producer = session.createProducer(topic);
        for (int i = 1; i < 4; i++) {
            TextMessage textMessage = session.createTextMessage("发送的主题消息：topic message ------ " + i);
            producer.send(textMessage);
        }
        System.out.println("************主题消息发送成功**************");
        //关闭资源,顺着开启，倒着关闭
        producer.close();
        session.close();
        connection.close();
    }

}
