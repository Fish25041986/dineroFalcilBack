package pruebasUnitarias;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dineroFacil.prueba.dto.ClienteDTO;
import com.dineroFacil.prueba.dto.ClienteRequestDTO;
import com.dineroFacil.prueba.dto.converter.ClienteDtoConverte;
import com.dineroFacil.prueba.entity.Ciudad;
import com.dineroFacil.prueba.entity.Cliente;
import com.dineroFacil.prueba.exceptions.InformationRelationalExceptions;
import com.dineroFacil.prueba.exceptions.NoContentException;
import com.dineroFacil.prueba.exceptions.ResourceAlreadyExistsException;
import com.dineroFacil.prueba.exceptions.ResourceNotFoundException;
import com.dineroFacil.prueba.repository.IclienteRepository;
import com.dineroFacil.prueba.services.ClienteService;
import com.dineroFacil.prueba.utils.Components.ValidateFindCiudadById;
import com.dineroFacil.prueba.utils.Components.ValidateFindClienteById;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
	 
    @Mock
    private IclienteRepository clienteRepository;

    @Mock
    private ValidateFindClienteById validateFindClienteById;

    @Mock
    private ValidateFindCiudadById validateFindCiudadById;

    @InjectMocks
    private ClienteService clienteService;


    @Test
    public void testUpdateCliente_WhenClientExists() {
        // Mock de la validación de cliente
        Cliente clienteMock = new Cliente();
        when(validateFindClienteById.findClienteById(1L)).thenReturn(clienteMock);

        // Mock de la validación de ciudad
        Ciudad ciudad = new Ciudad();  // Asegúrate de crear el objeto adecuado de Ciudad
        when(validateFindCiudadById.findCiudadById(anyLong())).thenReturn(ciudad);

        // Datos para actualizar
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO(
            "CC", "123456789", "Doe", "Bogotá", 1L, "3001234567");

        // Mock del save
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);

        // Llamada al servicio
        ClienteDTO clienteDTO = clienteService.updateCliente(1L, clienteRequestDTO);

        // Verifica que el clienteDTO no sea nulo
        assertNotNull(clienteDTO);
    }

    @Test
    public void testUpdateCliente_WhenClientNotFound() {
        // Mock de la validación de cliente para que lance excepción
        when(validateFindClienteById.findClienteById(1L)).thenThrow(ResourceNotFoundException.class);

        // Datos para actualizar
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO(
            "CC", "123456789", "Doe", "Bogotá", 1L, "3001234567");

        // Verifica que se lanza ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.updateCliente(1L, clienteRequestDTO);
        });
    }

    @Test
    public void testDeleteCliente_WhenClientExists() {
        // Mock de la validación de cliente
        when(validateFindClienteById.findClienteById(1L)).thenReturn(new Cliente());

        // Mock de existsByCredito para que devuelva 0 (sin registros relacionados)
        when(clienteRepository.existsByCredito(1L)).thenReturn(0L);

        // Mock del delete
        doNothing().when(clienteRepository).deleteById(anyLong());

        // Llamada al servicio
        Boolean result = clienteService.deleteCliente(1L);

        // Verifica que el resultado sea true
        assertTrue(result);
    }

    @Test
    public void testDeleteCliente_WhenClientHasRelatedRecords() {
        // Mock de la validación de cliente
        when(validateFindClienteById.findClienteById(1L)).thenReturn(new Cliente());

        // Mock de existsByCredito para que devuelva 1 (con registros relacionados)
        when(clienteRepository.existsByCredito(1L)).thenReturn(1L);

        // Verifica que se lanza InformationRelationalExceptions cuando hay registros relacionados
        assertThrows(InformationRelationalExceptions.class, () -> {
            clienteService.deleteCliente(1L);
        });
    }



    @Test
    public void testFindAllCliente_WhenNoClientsExist() {
        // Mock de findAll para que devuelva una lista vacía
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

        // Verifica que se lanza NoContentException cuando no hay clientes
        assertThrows(NoContentException.class, () -> {
            clienteService.findAllCliente();
        });
    }



    @Test
    public void testFindIdCliente_WhenClientNotFound() {
        // Mock de la validación de cliente para que lance excepción
        when(validateFindClienteById.findClienteById(1L)).thenThrow(ResourceNotFoundException.class);

        // Verifica que se lanza ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.findIdCliente(1L);
        });
    }
	
}