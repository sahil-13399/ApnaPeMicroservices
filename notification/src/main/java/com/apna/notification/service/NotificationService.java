package com.apna.notification.service;

import com.apna.clients.notification.NotificationRequest;
import com.apna.notification.dto.Notification;
import com.apna.notification.repository.NotificationRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

  private final NotificationRepository notificationRepository;

  public void send(NotificationRequest notificationRequest) {
    notificationRepository.save(
        Notification.builder()
            .toCustomerId(notificationRequest.getToCustomerId())
            .toCustomerEmail(notificationRequest.getToCustomerName())
            .sender(notificationRequest.getSenderName())
            .message(notificationRequest.getMessage())
            .sentAt(LocalDateTime.now())
            .amount(notificationRequest.getAmount())
            .build()
    );
  }
}