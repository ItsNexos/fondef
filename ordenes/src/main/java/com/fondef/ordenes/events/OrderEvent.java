package com.fondef.ordenes.events;

import com.fondef.ordenes.modelo.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
