package com.dineroFacil.prueba.dto.converter;

import org.springframework.stereotype.Component;

import com.dineroFacil.prueba.dto.ClienteDTO;
import com.dineroFacil.prueba.dto.ClienteRequestDTO;
import com.dineroFacil.prueba.entity.Ciudad;
import com.dineroFacil.prueba.entity.Cliente;

@Component
public class ClienteDtoConverte {
	
	public ClienteDTO clienteDTO(Cliente cliente) {
		
		return new ClienteDTO(
				cliente.getIdCliente(),
				cliente.getTipoDocumento(),
				cliente.getNumeroDocumento(),
				cliente.getApellidos(),
				cliente.getResidencia(),
				cliente.getCiudad().getNombreCiudad(),
				cliente.getTelefono()
	);
		
	}
	
	public Cliente clienteRequestDtoToEntity(ClienteRequestDTO clienteRequestDTO, Ciudad ciudad) {
	    Cliente cliente = new Cliente();
	    
	    cliente.setTipoDocumento(clienteRequestDTO.getTipoDocumento());
	    cliente.setNumeroDocumento(clienteRequestDTO.getNumeroDocumento());
	    cliente.setApellidos(clienteRequestDTO.getApellidos());
	    cliente.setResidencia(clienteRequestDTO.getResidencia());
	    cliente.setCiudad(ciudad);
	    cliente.setTelefono(clienteRequestDTO.getTelefono());
	    
	    return cliente;
	}
	

}
