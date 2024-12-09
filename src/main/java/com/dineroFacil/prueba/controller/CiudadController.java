package com.dineroFacil.prueba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dineroFacil.prueba.config.ApiRoutes;
import com.dineroFacil.prueba.dto.CiudadRequestDTO;
import com.dineroFacil.prueba.entity.Ciudad;
import com.dineroFacil.prueba.services.CiudadService;


@RestController
@RequestMapping(ApiRoutes.CIUDAD)
public class CiudadController {
	
	@Autowired
	CiudadService ciudadService;

    @GetMapping
    public ResponseEntity<List<Ciudad>> getAllCiudades() {
        List<Ciudad> ciudades = ciudadService.findAllCiudades();
        return new ResponseEntity<>(ciudades, HttpStatus.OK);
    }


    @GetMapping("/{idCiudad}")
    public ResponseEntity<Ciudad> getCiudadById(@PathVariable("idCiudad") Long idCiudad) {
        Ciudad ciudad = ciudadService.findIdCiudad(idCiudad);
        return new ResponseEntity<Ciudad>(ciudad, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Ciudad> createCiudad(@RequestBody Ciudad ciudad) {
        Ciudad ciudadCreada = ciudadService.createCiudad(ciudad);
        return new  ResponseEntity<Ciudad>(ciudadCreada, HttpStatus.OK);
    }


    @PutMapping("/{idCiudad}")
    public ResponseEntity<Ciudad> updateCiudad(@PathVariable("idCiudad") Long idCiudad, @RequestBody CiudadRequestDTO ciudadRequestDTO) {
        Ciudad ciudadActualizada = ciudadService.updateCiudad(idCiudad, ciudadRequestDTO);
        return new ResponseEntity<Ciudad>(ciudadActualizada, HttpStatus.OK);
    }


    @DeleteMapping("/{idCiudad}")
    public ResponseEntity<Boolean> deleteCiudad(@PathVariable("idCiudad") Long idCiudad) {
    	Boolean ciudadEliminada=ciudadService.deleteCiudad(idCiudad);
        return new ResponseEntity<Boolean>(ciudadEliminada, HttpStatus.OK);
    }

}
