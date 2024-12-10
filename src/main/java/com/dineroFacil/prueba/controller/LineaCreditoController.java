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
import com.dineroFacil.prueba.entity.LineaCredito;
import com.dineroFacil.prueba.services.LineaCreditoService;

@RestController
@RequestMapping(ApiRoutes.LINEACREDITO)
public class LineaCreditoController {
	
	@Autowired
	LineaCreditoService lineaCreditoService;
	
	
	 @GetMapping
	    public ResponseEntity<List<LineaCredito>> getAllLineaCredito() {
	        List<LineaCredito> lineaCredito = lineaCreditoService.findAllLineaCredito();
	        return new ResponseEntity<>(lineaCredito, HttpStatus.OK);
	    }


	    @GetMapping("/{idLinea}")
	    public ResponseEntity<LineaCredito> getLineaCreditoById(@PathVariable("idLinea") Long idLinea) {
	    	LineaCredito lineaCredito = lineaCreditoService.findIdLineaCredito(idLinea);
	        return new ResponseEntity<LineaCredito>(lineaCredito, HttpStatus.OK);
	    }


	    @PostMapping
	    public ResponseEntity<LineaCredito> createLineaCredito(@RequestBody LineaCredito lineaCredito) {
	    	LineaCredito lineaCreditoSave = lineaCreditoService.createLineaCredito(lineaCredito);
	        return new  ResponseEntity<LineaCredito>(lineaCreditoSave, HttpStatus.OK);
	    }


	    @PutMapping("/{idLinea}")
	    public ResponseEntity<LineaCredito> updateLineaCredito(@PathVariable("idLinea") Long idLinea, @RequestBody LineaCredito lineaCredito) {
	    	LineaCredito lineaCreditoUpdate = lineaCreditoService.updateLineaCredito(idLinea, lineaCredito);
	        return new ResponseEntity<LineaCredito>(lineaCreditoUpdate, HttpStatus.OK);
	    }


	    @DeleteMapping("/{idLinea}")
	    public ResponseEntity<Boolean> deleteLineaCredito(@PathVariable("idLinea") Long idLinea) {
	    	Boolean lineaEliminada=lineaCreditoService.deleteLineaCredito(idLinea);
	        return new ResponseEntity<Boolean>(lineaEliminada, HttpStatus.OK);
	    }

	
}
