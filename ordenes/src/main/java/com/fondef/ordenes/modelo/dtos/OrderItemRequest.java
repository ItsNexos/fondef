package com.fondef.ordenes.modelo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {

    private Long id;
    private int dia;
    private String hora;
    private Long cantidad;
}