package com.fondef.registros.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "Registro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class Registro {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //hora, dia, lugar, pm2.5, PM10, O3, CO
    private String sector;
    private int dia;
    private String hora;
    private int pmDos;
    private int pmDiez;
    private int ozono;
    private int carbono;


    @Override
    public String toString() {
        return "Registro[id=" + id + ", sector=" + sector + ", dia=" + dia + ", hora=" + hora + "]";
    }
    
}
