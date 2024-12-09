package com.dineroFacil.prueba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.dineroFacil.prueba.services.InformacionLaboralService;

@RestController
public class InformacionLaboralController {
	
	@Autowired
	InformacionLaboralService informacionLaboralService;

}
