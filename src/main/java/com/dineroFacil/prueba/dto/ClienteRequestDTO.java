package com.dineroFacil.prueba.dto;

import lombok.Data;

@Data
public class ClienteRequestDTO {
	
	private String tipoDocumento;
	private String numeroDocumento;
	private String apellidos;
	private String residencia;
	private Long idCiudad;
	private String telefono;
	
	
	
	
	public ClienteRequestDTO(String tipoDocumento, String numeroDocumento, String apellidos, String residencia,
			Long idCiudad, String telefono) {
		super();
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.apellidos = apellidos;
		this.residencia = residencia;
		this.idCiudad = idCiudad;
		this.telefono = telefono;
	}

}
