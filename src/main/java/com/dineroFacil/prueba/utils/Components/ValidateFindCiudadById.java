package com.dineroFacil.prueba.utils.Components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dineroFacil.prueba.entity.Ciudad;
import com.dineroFacil.prueba.exceptions.ResourceNotFoundException;
import com.dineroFacil.prueba.repository.IciudadRepository;

@Component
public class ValidateFindCiudadById {
    
    @Autowired
    private IciudadRepository ciudadRepository;

    private static final Logger logger = LoggerFactory.getLogger(ValidateFindCiudadById.class);

    public Ciudad findCiudadById(Long idCiudad) {
        return ciudadRepository.findById(idCiudad)
            .orElseThrow(() -> {
                logger.error("No existen registros con el identificador proporcionado en Ciudades");
                return new ResourceNotFoundException("No se encontraron datos registrados.", "findIdCiudad");
            });
    }
}
