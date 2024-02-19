package com.fondef.registros.model.entity.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Builder
@Setter
@Getter
public class RegistroResponse {
    private long id;
    private String sector;
    private int dia;
    private String hora;
    private int pmDos;
    private int pmDiez;
    private int ozono;
    private int carbono;
}


