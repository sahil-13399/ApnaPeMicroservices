package com.apna.customer.service;

import com.apna.clients.notification.NotificationRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender {
  private RabbitTemplate rabbitTemplate;

  @Autowired
  public RabbitMqSender(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }
  @Value("${spring.rabbitmq.exchange}")
  private String exchange;
  @Value("${spring.rabbitmq.routingkey}")
  private String routingkey;
  public void send(NotificationRequest notificationRequest){
    rabbitTemplate.convertAndSend(exchange,routingkey, notificationRequest);
  }
}
