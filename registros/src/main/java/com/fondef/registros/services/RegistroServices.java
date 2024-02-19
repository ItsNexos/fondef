package com.fondef.registros.services;

//import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;
import com.fondef.registros.model.entity.Registro;
import com.fondef.registros.model.entity.dtos.RegistroRequest;
import com.fondef.registros.model.entity.dtos.RegistroResponse;
import com.fondef.registros.repositories.RepositorioRegistros;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistroServices {

    private final RepositorioRegistros repositorioRegistros; 

    @SuppressWarnings("null")
    public void addRegistros(RegistroRequest registroRequest){
        var registro= Registro.builder()
            .sector(registroRequest.getSector())
            .dia(registroRequest.getDia())
            .hora(registroRequest.getHora())
            .pmDos(registroRequest.getPmDos())
            .pmDiez(registroRequest.getPmDiez())
            .ozono(registroRequest.getOzono())
            .carbono(registroRequest.getCarbono())
            .build();

            repositorioRegistros.save(registro);

            log.info("Registro agregado: {}", registro);
    } 

    
    public List<RegistroResponse> getAllProducts(){
        var registros = repositorioRegistros.findAll();

        return registros.stream().map(this::mapToRegistroResponse).toList();
    }
    private RegistroResponse mapToRegistroResponse(Registro registro) {
        return RegistroResponse.builder()
            .id(registro.getId())
            .sector(registro.getSector())
            .dia(registro.getDia())
            .hora(registro.getHora())
            .pmDos(registro.getPmDos())
            .pmDiez(registro.getPmDiez())
            .ozono(registro.getOzono())
            .carbono(registro.getCarbono())
            .build();
    }
}

