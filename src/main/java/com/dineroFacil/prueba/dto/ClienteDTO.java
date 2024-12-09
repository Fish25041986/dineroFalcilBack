package com.dineroFacil.prueba.dto;

import lombok.Data;

@Data
public class ClienteDTO {
	
	private Long idCliente;
	private String tipoDocumento;
	private String numeroDocumento;
	private String apellidos;
	private String residencia;
	private String nombreCiudad;
	private String telefono;
	
	
	public ClienteDTO(Long idCliente, String tipoDocumento, String numeroDocumento, String apellidos, String residencia,
			String nombreCiudad, String telefono) {
		super();
		this.idCliente = idCliente;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.apellidos = apellidos;
		this.residencia = residencia;
		this.nombreCiudad = nombreCiudad;
		this.telefono = telefono;
	}

	
	

}
