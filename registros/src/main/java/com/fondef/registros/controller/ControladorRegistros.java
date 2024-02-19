package com.fondef.registros.controller;

import com.fondef.registros.model.entity.dtos.RegistroRequest;
import com.fondef.registros.model.entity.dtos.RegistroResponse;
import com.fondef.registros.services.RegistroServices;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/registro")
@RequiredArgsConstructor
public class ControladorRegistros {

    private final RegistroServices registroService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addProduct(@RequestBody RegistroRequest registroRequest) {
        this.registroService.addRegistros(registroRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<RegistroResponse> getAllProducts() {
        return this.registroService.getAllProducts();
    }
}
