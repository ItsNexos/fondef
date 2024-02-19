package com.fondef.notification.events;

import com.fondef.notification.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
