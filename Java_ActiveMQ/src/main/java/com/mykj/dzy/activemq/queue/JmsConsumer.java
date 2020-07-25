package com.mykj.dzy.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer {

    public static final String ACTIVEMQ_URL = "tcp://192.168.181.129:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {
        receiveMessage();
    }

    private static void receiveMessage() throws JMSException, IOException {
        //创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //通过连接工厂获得connection并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        //启动连接
        connection.start();
        //创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建消息接收的目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        //创建消息的消费者
        MessageConsumer consumer = session.createConsumer(queue);

        //消费者接收消息
/*      //方式一 同步阻塞方式（receive（））
        while (true){
            TextMessage message = (TextMessage) consumer.receive();
            if (null != message){
                System.out.println("消息的消费者接收到消息："+message.getText());
            }else {
                break;
            }
        }*/
        //方式二 监听
        consumer.setMessageListener(message -> {
            if (null != message && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println("消息的消费者接收到消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        /*consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (null != message && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        System.out.println("消息的消费者接收到消息："+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/

        System.in.read();

        //关闭资源,顺着开启，倒着关闭
        consumer.close();
        session.close();
        connection.close();

    }

}
