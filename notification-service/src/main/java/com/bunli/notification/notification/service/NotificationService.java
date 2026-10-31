package com.bunli.notification.service;

import com.bunli.notification.dto.NotificationRequest;
import com.bunli.notification.dto.NotificationResponse;
import com.bunli.notification.dto.OrderConfirmedEvent;

public interface NotificationService {

     void handlerOrderConfirmationEvent(OrderConfirmedEvent confirmedEvent);

     NotificationResponse sendNotification(NotificationRequest notificationRequest);
}
