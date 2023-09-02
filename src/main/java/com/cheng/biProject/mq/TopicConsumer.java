package com.cheng.biProject.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class TopicConsumer {

  private static final String EXCHANGE_NAME = "topic-exchange";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, "topic");

      // 创建队列
    /**
     * 星号（*）可以精确匹配一个单词，而井号（#）可以替代0个或多个单词。
     * 我们前端使用井号进行匹配，例如"#.前端.#"，只要任务中包含了"前端"这个字符串，就会将任务发送给前端。后端和产品同理。
     * 对于匹配的需求，使用星号可能存在一些限制。星号在匹配时占据一个单词的位置，并且中间的两个点之间的单词是固定的，这导致了无法匹配的情况。
     */
      String queueName1 = "frontend_queue";
      channel.queueDeclare(queueName1, true, false, false, null);
      channel.queueBind(queueName1, EXCHANGE_NAME, "#.前端.#");

      // 创建队列
      String queueName2 = "backend_queue";
      channel.queueDeclare(queueName2, true, false, false, null);
      channel.queueBind(queueName2, EXCHANGE_NAME, "#.后端.#");

      // 创建队列
      String queueName3 = "product_queue";
      channel.queueDeclare(queueName3, true, false, false, null);
      channel.queueBind(queueName3, EXCHANGE_NAME, "#.产品.#");

      System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

      DeliverCallback xiaoaDeliverCallback = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
          System.out.println(" [xiaoa] Received '" +
                  delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
      };

      DeliverCallback xiaobDeliverCallback = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
          System.out.println(" [xiaob] Received '" +
                  delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
      };

      DeliverCallback xiaocDeliverCallback = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
          System.out.println(" [xiaoc] Received '" +
                  delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
      };

      channel.basicConsume(queueName1, true, xiaoaDeliverCallback, consumerTag -> {
      });
      channel.basicConsume(queueName2, true, xiaobDeliverCallback, consumerTag -> {
      });
      channel.basicConsume(queueName3, true, xiaocDeliverCallback, consumerTag -> {
      });
  }
}
