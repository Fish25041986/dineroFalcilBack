package com.dineroFacil.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dineroFacil.prueba.entity.InformacionLaboral;

@Repository
public interface IinformacionLaboralRepository extends JpaRepository<InformacionLaboral, Long>{

}
