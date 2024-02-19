package com.fondef.registros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fondef.registros.model.entity.Registro;

public interface RepositorioRegistros extends JpaRepository<Registro, Long>{
    
}
