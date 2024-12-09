package com.dineroFacil.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dineroFacil.prueba.entity.Credito;

@Repository
public interface IcreditoRepository extends JpaRepository<Credito, Long>{

}
