package com.dineroFacil.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dineroFacil.prueba.entity.Ciudad;

@Repository
public interface IciudadRepository  extends JpaRepository<Ciudad, Long>{
	
    // Consulta nativa para verificar si ya existe una ciudad con el mismo nombre o código
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Ciudad c WHERE c.nombreCiudad = :nombreCiudad OR c.codigoCiudad = :codigoCiudad")
	boolean existsByNombreOrCodigo(@Param("nombreCiudad") String nombreCiudad, @Param("codigoCiudad") String codigoCiudad);
    		
	//Consulta para verificar si existe informacion relacional
    @Query(value = "SELECT COUNT(*) > 0 FROM Clientes c WHERE c.idCiudad = :idCiudad", nativeQuery = true)
    boolean existeClienteEnCiudad(@Param("idCiudad") Long idCiudad);

}
