package com.fondef.seguimientoregistros.services;

import com.fondef.seguimientoregistros.Repositorio.RepositorioSeguimiento;
import com.fondef.seguimientoregistros.model.entity.Seguimiento;
import com.fondef.seguimientoregistros.model.entity.dtos.BaseResponse;
import com.fondef.seguimientoregistros.model.entity.dtos.OrderItemRequest;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SeguimientoServices {

    private final RepositorioSeguimiento repositorioSeguimiento;

    public boolean existe(int dia) {
        var seguimiento = repositorioSeguimiento.findByDia(dia);

        return seguimiento.filter(value -> value.getCantidad() > 0).isPresent();
    }

    public BaseResponse areInStock(List<OrderItemRequest> orderItems) {

        var errorList = new ArrayList<String>();

        List<Integer> dias = orderItems.stream().map(OrderItemRequest::getDia).toList();

        List<Seguimiento> seguimientoList = repositorioSeguimiento.findByDiaIn(dias);

        orderItems.forEach(orderItem -> {
            var seguimiento = seguimientoList.stream().filter(value -> value.getDia()==orderItem.getDia()).findFirst();
            if (seguimiento.isEmpty()) {
                errorList.add("Registro con el dia: " + orderItem.getDia() + " no existe");
            }// } else if (seguimiento.get().getCantidad() < orderItem.getCantidad()) {
            //     errorList.add("Product with sku " + orderItem.getDia() + " has insufficient quantity");
            // }
        });

        return errorList.size() > 0 ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }
}