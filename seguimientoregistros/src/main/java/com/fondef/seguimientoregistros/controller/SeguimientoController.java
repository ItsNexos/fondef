package com.fondef.seguimientoregistros.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.fondef.seguimientoregistros.model.entity.dtos.BaseResponse;
import com.fondef.seguimientoregistros.model.entity.dtos.OrderItemRequest;
import com.fondef.seguimientoregistros.services.SeguimientoServices;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/seguimiento")
@RequiredArgsConstructor
public class SeguimientoController {

    private final SeguimientoServices seguimientoServices;

    @GetMapping("/{dia}")
    @ResponseStatus(HttpStatus.OK)
    public boolean existe(@PathVariable("dia") int dia) {
        return seguimientoServices.existe(dia);
    }

    @PostMapping("/disponibilidad")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse areInStock(@RequestBody List<OrderItemRequest> orderItems) {
        return seguimientoServices.areInStock(orderItems);
    }    
}
