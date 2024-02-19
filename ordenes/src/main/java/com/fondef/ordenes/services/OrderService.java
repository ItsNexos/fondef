package com.fondef.ordenes.services;


import lombok.RequiredArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fondef.ordenes.RepositorioOrder.RepositorioOrder;
import com.fondef.ordenes.events.OrderEvent;
import com.fondef.ordenes.modelo.dtos.BaseResponse;
import com.fondef.ordenes.modelo.dtos.OrderItemRequest;
import com.fondef.ordenes.modelo.dtos.OrderItemsResponse;
import com.fondef.ordenes.modelo.dtos.OrderRequest;
import com.fondef.ordenes.modelo.dtos.OrderResponse;
import com.fondef.ordenes.modelo.entity.Order;
import com.fondef.ordenes.modelo.entity.OrderItems;
import com.fondef.ordenes.modelo.enums.OrderStatus;
import com.fondef.ordenes.utils.JsonUtils;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RepositorioOrder orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObservationRegistry observationRegistry;


    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Observation inventoryObservation = Observation.createNotStarted("seguimientoregistros", observationRegistry);

        //Chequea por seguimiento de los registros
        return inventoryObservation.observe(() -> {
            @SuppressWarnings("null")
            BaseResponse result = this.webClientBuilder.build()
                    .post()
                    .uri("lb://seguimientoregistros/api/seguimiento/disponibilidad")
                    .bodyValue(orderRequest.getOrderItems())
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            if (result != null && !result.hasErrors()) {
                Order order = new Order();
                order.setOrderNumber(UUID.randomUUID().toString());
                order.setOrderItems(orderRequest.getOrderItems().stream()
                        .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order))
                        .toList());
                var savedOrder = this.orderRepository.save(order);
                this.kafkaTemplate.send("orders-topic", JsonUtils.toJson(
                        new OrderEvent(savedOrder.getOrderNumber(), savedOrder.getOrderItems().size(), OrderStatus.PLACED)
                ));
                return mapToOrderResponse(savedOrder);
            } else {
                throw new IllegalArgumentException("Algunos de los registros pedidos no existen");
            }    
        });

    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();

        return orders.stream().map(this::mapToOrderResponse).toList();

    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(order.getId(), order.getOrderNumber()
                , order.getOrderItems().stream().map(this::mapToOrderItemRequest).toList());
    }

    private OrderItemsResponse mapToOrderItemRequest(OrderItems orderItems) {
        return new OrderItemsResponse(orderItems.getId(), orderItems.getDia(), orderItems.getHora(), orderItems.getCantidad());
    }

    private OrderItems mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Order order) {
        return OrderItems.builder()
                .id(orderItemRequest.getId())
                .dia(orderItemRequest.getDia())
                .hora(orderItemRequest.getHora())
                .cantidad(orderItemRequest.getCantidad())
                .order(order)
                .build();
    }
}