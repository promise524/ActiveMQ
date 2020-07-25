package com.mykj.dzy.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class JmsProduce {

    public static final String ACTIVEMQ_URL = "tcp://192.168.181.129:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        sendMessage();
    }

    public static void sendMessage() throws JMSException {

        //创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //通过连接工厂获得connection并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        //启动连接
        connection.start();
        //创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建消息发送的目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        //创建消息生产者
        MessageProducer producer = session.createProducer(queue);
        //通过使用MessageProducer消息生产者将消息发送到MQ的队列中
        for (int i = 1; i < 4; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("发送的消息：message-----"+i);
            producer.send(textMessage);
        }
        System.out.println("************消息发送成功**************");
        //关闭资源,顺着开启，倒着关闭
        producer.close();
        session.close();
        connection.close();
    }


}
