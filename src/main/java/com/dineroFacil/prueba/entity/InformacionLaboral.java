package com.dineroFacil.prueba.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "InformacionLaboral")
public class InformacionLaboral implements Serializable{
	
	private static final long serialVersionUID = -4310027227752446841L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idInformacionLaboral;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCliente", nullable = false)
	private Cliente cliente;
	
	@Column(name = "nitEmpresa", nullable = false, length = 50)
	private String nitEmpresa;
	
	@Column(name = "nombreEmpresa", nullable = false, length = 100)
	private String nombreEmpresa;
	
	@Column(name = "direccion", nullable = false, length = 255)
	private String direccion;
	
	@Column(name = "telefono", nullable = false, length = 20)
	private String telefono;
	
	@Column(name = "cargo", nullable = false, length = 100)
	private String cargo;
	
	@Column(name = "fechaVinculacion", nullable = false)
	private LocalDate fechaVinculacion;

}
