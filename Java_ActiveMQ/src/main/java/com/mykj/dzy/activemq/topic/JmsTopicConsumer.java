package com.mykj.dzy.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsTopicConsumer {

    public static final String ACTIVEMQ_URL = "tcp://192.168.181.129:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException {
        receiveTopic();
    }
    public static void receiveTopic() throws JMSException {
    //创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //通过连接工厂获得connection并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        //启动连接
        connection.start();
        //创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建消息接收的目的地
        Topic topic = session.createTopic(TOPIC_NAME);
        //创建消息的消费者
        MessageConsumer consumer = session.createConsumer(topic);
        //消息监听
        consumer.setMessageListener( message -> {
            if (null != message && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("*******已接收订阅的主题消息******** ："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        } );
    }
}
