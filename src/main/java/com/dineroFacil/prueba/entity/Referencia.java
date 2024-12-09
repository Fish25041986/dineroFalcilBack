package com.dineroFacil.prueba.entity;

import java.io.Serializable;
import com.dineroFacil.prueba.utils.enums.TipoReferencia;

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
@Table(name = "Referencias")
public class Referencia implements Serializable{
	
	private static final long serialVersionUID = -4310027227752446841L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idReferencia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCliente", nullable = false)
	private Cliente cliente;
	
	@Column(name = "tipoReferencia", nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	private TipoReferencia tipoReferencia;
	
	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;
	
	@Column(name = "direccion", nullable = false, length = 255)
	private String direccion;
	
	@Column(name = "telefono", nullable = false, length = 20)
	private String telefono;
	
	@Column(name = "ciudad", nullable = false, length = 100)
	private String ciudad;
	
	@Column(name = "email", nullable = false, length = 100)
	private String email;
	
	@Column(name = "parentesco", length = 50)
	private String parentesco;

}
