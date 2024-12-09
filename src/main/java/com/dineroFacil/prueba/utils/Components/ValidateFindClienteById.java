package com.dineroFacil.prueba.utils.Components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dineroFacil.prueba.entity.Cliente;
import com.dineroFacil.prueba.exceptions.ResourceNotFoundException;
import com.dineroFacil.prueba.repository.IclienteRepository;

@Component
public class ValidateFindClienteById {
	
	@Autowired
	private IclienteRepository clienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(ValidateFindCiudadById.class);

    public Cliente findClienteById(Long idCliente) {
        return clienteRepository.findById(idCliente)
            .orElseThrow(() -> {
                logger.error("No existen registros con el identificador proporcionado en Clientes");
                return new ResourceNotFoundException("No se encontraron datos registrados.", "findClienteById");
            });
    }

}
