package com.dineroFacil.prueba.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreditoDto {

	private Long idCredito;
	private String ApellidoCliente;
	private String lineaCredito;
	private BigDecimal montoSolicitado;
	private Integer plazoSolicitado;
	private String estado;
	
	public CreditoDto(Long idCredito, String apellidoCliente, String lineaCredito, BigDecimal montoSolicitado,
			Integer plazoSolicitado, String estado) {
		super();
		this.idCredito = idCredito;
		ApellidoCliente = apellidoCliente;
		this.lineaCredito = lineaCredito;
		this.montoSolicitado = montoSolicitado;
		this.plazoSolicitado = plazoSolicitado;
		this.estado = estado;
	}
	
	
	
}
