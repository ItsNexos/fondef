package com.fondef.seguimientoregistros.util;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.fondef.seguimientoregistros.Repositorio.RepositorioSeguimiento;
import com.fondef.seguimientoregistros.model.entity.Seguimiento;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final RepositorioSeguimiento repositorioSeguimiento;

    @SuppressWarnings("null")
    @Override
    public void run(String... args) throws Exception {
        log.info("Cargando data...");
        if (repositorioSeguimiento.findAll().size() == 0) {
            repositorioSeguimiento.saveAll(
                    List.of(
                            Seguimiento.builder().dia(25).hora("18:30").cantidad(1L).build(),
                            Seguimiento.builder().dia(12).hora("10:00").cantidad(1L).build(),
                            Seguimiento.builder().dia(20).hora("14:45").cantidad(1L).build(),
                            Seguimiento.builder().dia(15).hora("12:30").cantidad(1L).build()
                    )
            );
        }
        log.info("Data loaded.");
    }
}