package com.dineroFacil.prueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dineroFacil.prueba.repository.IlineaCreditoRepository;

@Service
public class LineaCreditoService {
	
	@Autowired
	IlineaCreditoRepository lineaCreditoRepository;

}
