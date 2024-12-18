package com.dineroFacil.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dineroFacil.prueba.entity.Cliente;

@Repository
public interface IclienteRepository extends JpaRepository<Cliente, Long>{
	
	@Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.numeroDocumento = :numeroDocumento")
	boolean existsByNumeroDocumento(@Param("numeroDocumento") String numeroDocumento);
    
	@Query(value = "SELECT EXISTS (SELECT 1 FROM credito WHERE idCliente = :idCliente)", nativeQuery = true)
	Long existsByCredito(@Param("idCliente") Long idCliente);

}
