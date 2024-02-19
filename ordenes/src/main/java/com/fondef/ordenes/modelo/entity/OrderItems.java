package com.fondef.ordenes.modelo.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //dia, hora, cantidad(registros)
    private int dia;
    private String hora;
    private Long cantidad;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}