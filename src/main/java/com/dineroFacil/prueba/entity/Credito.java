package com.dineroFacil.prueba.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dineroFacil.prueba.utils.enums.EstadoCredito;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Credito")
public class Credito implements Serializable{
	
	private static final long serialVersionUID = -4310027227752446841L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCredito;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCliente", nullable = false)
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idLineaCredito", nullable = false)
	private LineaCredito lineaCredito;
	
	@Column(name = "montoSolicitado", nullable = false, precision = 12, scale = 2)
	private BigDecimal montoSolicitado;
	
	@Column(name = "plazoSolicitado", nullable = false)
	private Integer plazoSolicitado;
	
	@Column(name = "descripcionDestino", nullable = false, columnDefinition = "TEXT")
	private String descripcionDestino;
	
	@Column(name = "estado", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private EstadoCredito estado = EstadoCredito.Pendiente;


}
