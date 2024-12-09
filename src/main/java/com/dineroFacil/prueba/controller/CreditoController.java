package com.dineroFacil.prueba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.dineroFacil.prueba.services.CreditoService;

@RestController
public class CreditoController {
	
	@Autowired
	CreditoService creditoService;

}
