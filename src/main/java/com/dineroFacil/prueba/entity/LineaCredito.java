package com.dineroFacil.prueba.entity;


import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "LineaCreditos")
public class LineaCredito implements Serializable{
	
	private static final long serialVersionUID = -4310027227752446841L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idLineaCredito;
	
	@Column(name = "nombreLinea", nullable = false, length = 100)
	private String nombreLinea;
	
	@Column(name = "montoMinimo", nullable = false, precision = 12, scale = 2)
	private BigDecimal montoMinimo;
	
	@Column(name = "montoMaximo", nullable = false, precision = 12, scale = 2)
	private BigDecimal montoMaximo;
	
	@Column(name = "plazoMaximo", nullable = false)
	private Integer plazoMaximo;

}
