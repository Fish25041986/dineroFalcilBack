package com.dineroFacil.prueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dineroFacil.prueba.repository.IcreditoRepository;

@Service
public class CreditoService {
	
	@Autowired
	IcreditoRepository creditoRepository;

}
