package com.fondef.ordenes.modelo.dtos;

import java.util.List;


public record OrderResponse(Long id, String orderNumber, List<OrderItemsResponse> orderItems) {
}