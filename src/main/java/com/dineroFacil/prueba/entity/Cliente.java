package com.dineroFacil.prueba.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "Clientes")
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = -4310027227752446841L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCliente;
	
	@Column(name = "tipoDocumento", nullable = false, length = 50)
	private String tipoDocumento;
	
	@Column(name = "numeroDocumento", nullable = false, unique = true, length = 50)
	private String numeroDocumento;
	
	@Column(name = "apellidos", nullable = false, length = 100)
	private String apellidos;
	
	@Column(name = "residencia", nullable = false, length = 255)
	private String residencia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCiudad", nullable = false)
	private Ciudad ciudad;
	
	@Column(name = "telefono", nullable = false, length = 20)
	private String telefono;

	public Cliente() {
		super();
	}

	
}
