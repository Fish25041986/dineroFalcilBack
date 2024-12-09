package com.dineroFacil.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dineroFacil.prueba.entity.LineaCredito;

@Repository
public interface IlineaCreditoRepository extends JpaRepository<LineaCredito, Long>{

}
