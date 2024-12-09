package com.dineroFacil.prueba.exceptions;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String path;
    private String metodoEnError;
    private String mensaje;
    private String error;

    public ErrorResponse() {
    	
    }
}
