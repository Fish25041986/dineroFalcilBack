package com.dineroFacil.prueba.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	 //Metodo para cunado no se haya información en los find all
	 @ExceptionHandler(NoContentException.class)
	    public ResponseEntity<ErrorResponse> handleNoContentException(NoContentException ex, WebRequest request) {
	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setTimestamp(LocalDateTime.now());
	        errorResponse.setStatus(HttpStatus.NO_CONTENT.value());
	        errorResponse.setPath(request.getDescription(false));
	        errorResponse.setMensaje(ex.getMensaje());
	        errorResponse.setMetodoEnError(ex.getMetodoEnError());
	        errorResponse.setError(ex.getMessage());

	        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
	    }
	 
	 //Metodo para cunado no se haya información en los findById
	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setTimestamp(LocalDateTime.now());
	        errorResponse.setStatus(HttpStatus.NO_CONTENT.value());
	        errorResponse.setPath(request.getDescription(false));
	        errorResponse.setMensaje(ex.getMensaje());
	        errorResponse.setMetodoEnError(ex.getMetodoEnError());
	        errorResponse.setError(ex.getMessage());

	        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
	    }
	 
	 //Metodo para cuando se intenta elimnar informacion y esta tiene valores relacionados
	 @ExceptionHandler(InformationRelationalExceptions.class)
	    public ResponseEntity<ErrorResponse> handleInformationRelationalExceptions(InformationRelationalExceptions ex, WebRequest request) {
	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setTimestamp(LocalDateTime.now());
	        errorResponse.setStatus(HttpStatus.CONFLICT.value());
	        errorResponse.setPath(request.getDescription(false));
	        errorResponse.setMensaje(ex.getMensaje());
	        errorResponse.setMetodoEnError(ex.getMetodoEnError());
	        errorResponse.setError(ex.getMessage());

	        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	    }
	 
	 //Metodo para cuando ocurre un error internoal guardar. modificar o elimanr
	 @ExceptionHandler(InternaServerErrorException.class)
	    public ResponseEntity<ErrorResponse> handleInternaServerErrorException(InternaServerErrorException ex, WebRequest request) {
	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setTimestamp(LocalDateTime.now());
	        errorResponse.setStatus(HttpStatus.CONFLICT.value());
	        errorResponse.setPath(request.getDescription(false));
	        errorResponse.setMensaje(ex.getMensaje());
	        errorResponse.setMetodoEnError(ex.getMetodoEnError());
	        errorResponse.setError(ex.getMessage());

	        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	    }
	 
	 //Metodo para cuando existe datos iguales a los que estan entrando
	 @ExceptionHandler(ResourceAlreadyExistsException.class)
	    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setTimestamp(LocalDateTime.now());
	        errorResponse.setStatus(HttpStatus.CONFLICT.value());
	        errorResponse.setPath(request.getDescription(false));
	        errorResponse.setMensaje(ex.getMensaje());
	        errorResponse.setMetodoEnError(ex.getMetodoEnError());
	        errorResponse.setError(ex.getMessage());

	        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	    }
	 
	 
	 

}
