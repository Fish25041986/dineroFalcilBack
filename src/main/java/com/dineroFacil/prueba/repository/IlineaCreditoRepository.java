package com.dineroFacil.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dineroFacil.prueba.entity.LineaCredito;

@Repository
public interface IlineaCreditoRepository extends JpaRepository<LineaCredito, Long>{
	
	//Verificar si existe un registro por nombre
    boolean existsByNombreLinea(String nombreLinea);
    
    //Verificar si existe información relacionada
    @Query(value = "SELECT EXISTS(SELECT 1 FROM credito c WHERE c.idLineaCredito = :idLineaCredito)", nativeQuery = true)
    Integer existsByLineaCredito(@Param("idLineaCredito") Long idLineaCredito);
}
