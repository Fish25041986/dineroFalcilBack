package pruebasUnitarias;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

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

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private IclienteRepository clienteRepository;

    @Mock
    private ValidateFindClienteById validateFindClienteById;

    @Mock
    private ValidateFindCiudadById validateFindCiudadById;

    @Mock
    private Logger logger;

    @Mock
    private ClienteDtoConverte clienteDtoConverte;

    @BeforeEach
    void setUp() {
        // Inicialización si es necesario.
    }

    @Test
    void testFindAllCliente_NoContent() {
        // Configurar el mock para que el repositorio no tenga clientes
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

        // Ejecutar y verificar que se lanza la excepción NoContentException
        NoContentException exception = assertThrows(NoContentException.class, () -> {
            clienteService.findAllCliente();
        });

        assertEquals("No se encontraron datos registrados.", exception.getMessage());
    }

    @Test
    void testFindIdCliente_NotFound() {
        // Configurar el mock para lanzar ResourceNotFoundException
        Long idCliente = 1L;
        when(validateFindClienteById.findClienteById(idCliente)).thenThrow(new ResourceNotFoundException("Cliente no encontrado", "findIdCliente"));

        // Ejecutar y verificar que se lanza la excepción ResourceNotFoundException
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.findIdCliente(idCliente);
        });

        assertEquals("Cliente no encontrado", exception.getMessage());
    }

    @Test
    void testCreateCliente_AlreadyExists() {
        // Crear clienteRequestDTO
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("CC", "123456789", "John Doe", "Calle 123", 1L, "1234567890");

        // Configurar el mock para que el cliente ya exista
        when(clienteRepository.existsByNumeroDocumento(clienteRequestDTO.getNumeroDocumento())).thenReturn(true);

        // Ejecutar y verificar que se lanza la excepción ResourceAlreadyExistsException
        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            clienteService.createCliente(clienteRequestDTO);
        });

        assertEquals("Ya existe un cliente con el mismo numero de cedula", exception.getMessage());
    }

    @Test
    void testCreateCliente_Success() {
        // Crear clienteRequestDTO
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("CC", "123456789", "John Doe", "Calle 123", 1L, "1234567890");

        // Configurar el mock para la validación de la ciudad
        Ciudad ciudad = new Ciudad();
        ciudad.setIdCiudad(1L);
        when(validateFindCiudadById.findCiudadById(clienteRequestDTO.getIdCiudad())).thenReturn(ciudad);

        // Configurar el mock para la conversión del DTO a entidad
        Cliente cliente = new Cliente();
        when(clienteDtoConverte.clienteRequestDtoToEntity(clienteRequestDTO, ciudad)).thenReturn(cliente);

        // Configurar el mock para guardar el cliente
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Ejecutar y verificar que el cliente fue guardado correctamente
        Cliente clienteGuardado = clienteService.createCliente(clienteRequestDTO);
        assertNotNull(clienteGuardado);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testUpdateCliente_NotFound() {
        // Datos de clienteRequestDTO
        Long idCliente = 1L;
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("CC", "987654321", "Jane Doe", "Calle 456", 1L, "0987654321");

        // Configurar el mock para que no se encuentre el cliente
        when(validateFindClienteById.findClienteById(idCliente)).thenThrow(new ResourceNotFoundException("Cliente no encontrado", "updateCliente"));

        // Ejecutar y verificar que se lanza la excepción ResourceNotFoundException
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.updateCliente(idCliente, clienteRequestDTO);
        });

        assertEquals("Cliente no encontrado", exception.getMessage());
    }

    @Test
    void testUpdateCliente_Success() {
        // Datos de clienteRequestDTO
        Long idCliente = 1L;
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("CC", "987654321", "Jane Doe", "Calle 456", 1L, "0987654321");

        // Configurar mocks
        Cliente clienteExistente = new Cliente();
        Ciudad ciudad = new Ciudad();
        ciudad.setIdCiudad(1L);
        when(validateFindClienteById.findClienteById(idCliente)).thenReturn(clienteExistente);
        when(validateFindCiudadById.findCiudadById(clienteRequestDTO.getIdCiudad())).thenReturn(ciudad);

        // Ejecutar y verificar que el cliente se actualiza correctamente
        Cliente clienteActualizado = clienteService.updateCliente(idCliente, clienteRequestDTO);
        assertNotNull(clienteActualizado);
        assertEquals(clienteRequestDTO.getNumeroDocumento(), clienteActualizado.getNumeroDocumento());
    }

    @Test
    void testDeleteCliente_WithRelatedRecords() {
        // ID de cliente a eliminar
        Long idCliente = 1L;

        // Configurar el mock para que existan registros relacionados
        when(clienteRepository.existsByCredito(idCliente)).thenReturn(true);

        // Ejecutar y verificar que se lanza la excepción InformationRelationalExceptions
        InformationRelationalExceptions exception = assertThrows(InformationRelationalExceptions.class, () -> {
            clienteService.deleteCliente(idCliente);
        });

        assertEquals("No se puede eliminar el cliented debido a información relacionada", exception.getMessage());
    }

    @Test
    void testDeleteCliente_Success() {
        // ID de cliente a eliminar
        Long idCliente = 1L;

        // Configurar el mock para que no existan registros relacionados
        when(clienteRepository.existsByCredito(idCliente)).thenReturn(false);
        doNothing().when(clienteRepository).deleteById(idCliente);

        // Ejecutar y verificar que el cliente se elimina correctamente
        Boolean result = clienteService.deleteCliente(idCliente);
        assertTrue(result);
        verify(clienteRepository, times(1)).deleteById(idCliente);
    }
}