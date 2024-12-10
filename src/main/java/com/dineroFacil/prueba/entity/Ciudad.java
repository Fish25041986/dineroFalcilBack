package com.dineroFacil.prueba.entity;

import java.io.Serializable;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "Ciudades")
public class Ciudad implements Serializable{
	
	private static final long serialVersionUID = -4310027227752446841L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCiudad")
    private Long idCiudad;

    @Column(name = "nombreCiudad", nullable = false, length = 50)
    private String nombreCiudad;

    @Column(name = "codigoCiudad", length = 10)
    private String codigoCiudad;

	public Ciudad() {
		super();
	}
    
    

}
