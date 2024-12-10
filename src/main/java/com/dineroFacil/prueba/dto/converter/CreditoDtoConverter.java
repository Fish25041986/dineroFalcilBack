package com.dineroFacil.prueba.dto.converter;

import org.springframework.stereotype.Component;

import com.dineroFacil.prueba.dto.CreditoDto;
import com.dineroFacil.prueba.entity.Credito;

@Component
public class CreditoDtoConverter {

	public CreditoDto creditoDto(Credito credito) {
		
		return new CreditoDto(
				credito.getIdCredito(),
				credito.getCliente().getApellidos(),
				credito.getLineaCredito().getNombreLinea(),
				credito.getMontoSolicitado(),
				credito.getPlazoSolicitado(),
				credito.getEstado().name()
				);
		
	}
}
