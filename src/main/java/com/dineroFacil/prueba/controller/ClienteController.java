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
import com.dineroFacil.prueba.dto.ClienteDTO;
import com.dineroFacil.prueba.dto.ClienteRequestDTO;
import com.dineroFacil.prueba.entity.Cliente;
import com.dineroFacil.prueba.services.ClienteService;

@RestController
@RequestMapping(ApiRoutes.CLIENTE)
public class ClienteController {
	
	@Autowired
	ClienteService clienteService;
	
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        List<ClienteDTO> cliente = clienteService.findAllCliente();
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }


    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable("idCliente") Long idCliente) {
    	ClienteDTO cliente = clienteService.findIdCliente(idCliente);
        return new ResponseEntity<ClienteDTO>(cliente, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {
    	Cliente clienteCreado = clienteService.createCliente(clienteRequestDTO);
        return new  ResponseEntity<Cliente>(clienteCreado, HttpStatus.OK);
    }


    @PutMapping("/{idCliente}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable("idCliente") Long idCliente, @RequestBody ClienteRequestDTO clienteRequestDTO) {
    	Cliente clientedActualizado = clienteService.updateCliente(idCliente, clienteRequestDTO);
        return new ResponseEntity<Cliente>(clientedActualizado, HttpStatus.OK);
    }


    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Boolean> deleteCliente(@PathVariable("idCliente") Long idCliente) {
    	Boolean clienteEliminado=clienteService.deleteCliente(idCliente);
        return new ResponseEntity<Boolean>(clienteEliminado, HttpStatus.OK);
    }


}
