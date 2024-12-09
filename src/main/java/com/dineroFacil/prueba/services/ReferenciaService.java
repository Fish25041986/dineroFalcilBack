package com.dineroFacil.prueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dineroFacil.prueba.repository.IreferenciaRepository;

@Service
public class ReferenciaService {
	
	@Autowired
	IreferenciaRepository referenciaRepository;

}
