package com.dineroFacil.prueba.utils.Components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dineroFacil.prueba.entity.LineaCredito;
import com.dineroFacil.prueba.exceptions.ResourceNotFoundException;
import com.dineroFacil.prueba.repository.IlineaCreditoRepository;


@Component
public class ValidateFindLineaCreditoById {
	
	@Autowired
	private IlineaCreditoRepository iLineaCreditoRepository;

    private static final Logger logger = LoggerFactory.getLogger(ValidateFindCiudadById.class);

    public LineaCredito findLineaCreditoById(Long idLinea) {
        return iLineaCreditoRepository.findById(idLinea)
            .orElseThrow(() -> {
                logger.error("No existen registros con el identificador proporcionado en LineaCreditos");
                return new ResourceNotFoundException("No se encontraron datos registrados.", "findLineaCreditoById");
            });
    }

}
