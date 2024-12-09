package com.dineroFacil.prueba.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class InternaServerErrorException extends RuntimeException{
	
	private String metodoEnError;
	private String mensaje;

	public InternaServerErrorException(String mensaje, String metodoEnError) {
        this.mensaje = mensaje;
        this.metodoEnError = metodoEnError;
    }

}
