package com.fondef.seguimientoregistros.Repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import com.fondef.seguimientoregistros.model.entity.Seguimiento;

import java.util.List;
import java.util.Optional;



public interface RepositorioSeguimiento extends JpaRepository<Seguimiento, Long> {
    Optional<Seguimiento> findByDia(int dia);

    List<Seguimiento> findByDiaIn(List<Integer> dias);
}
