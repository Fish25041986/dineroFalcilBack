package com.dineroFacil.prueba.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class ResourceAlreadyExistsException extends RuntimeException{
	
	private String metodoEnError;
	private String mensaje;

	public ResourceAlreadyExistsException(String mensaje, String metodoEnError) {
        this.mensaje = mensaje;
        this.metodoEnError = metodoEnError;
    }

}
