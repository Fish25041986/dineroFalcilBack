package com.dineroFacil.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dineroFacil.prueba.entity.Referencia;

@Repository
public interface IreferenciaRepository extends JpaRepository<Referencia, Long>{

}
