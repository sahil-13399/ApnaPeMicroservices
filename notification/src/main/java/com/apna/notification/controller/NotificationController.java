package com.apna.notification.controller;

import com.apna.clients.notification.NotificationRequest;
import com.apna.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {

  private final NotificationService notificationService;

  @RabbitListener(queues = "${spring.rabbitmq.queue}")
  public void sendNotification(NotificationRequest notificationRequest) {
    log.info("New notification... {}", notificationRequest);
    notificationService.send(notificationRequest);
  }
}