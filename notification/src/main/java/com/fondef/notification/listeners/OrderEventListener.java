package com.fondef.notification.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fondef.notification.events.OrderEvent;
import com.fondef.notification.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderEventListener {

    @KafkaListener(topics = "orders-topic")
    public void handleOrdersNotifications(String message) {
        var orderEvent = JsonUtils.fromJson(message, OrderEvent.class);

        //Send email to customer, send SMS to customer, etc.
        //Notify another service...

        log.info("Orden {} evento recibido para la orden: {} con {} registros", orderEvent.orderStatus(), orderEvent.orderNumber(), orderEvent.itemsCount());
    }
}