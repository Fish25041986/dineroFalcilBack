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

import com.dineroFacil.prueba.dto.CiudadRequestDTO;
import com.dineroFacil.prueba.entity.Ciudad;
import com.dineroFacil.prueba.exceptions.InformationRelationalExceptions;
import com.dineroFacil.prueba.exceptions.NoContentException;
import com.dineroFacil.prueba.exceptions.ResourceAlreadyExistsException;
import com.dineroFacil.prueba.exceptions.ResourceNotFoundException;
import com.dineroFacil.prueba.repository.IciudadRepository;
import com.dineroFacil.prueba.services.CiudadService;
import com.dineroFacil.prueba.utils.Components.ValidateFindCiudadById;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class CiudadServiceTest {

    @InjectMocks
    private CiudadService ciudadService;

    @Mock
    private IciudadRepository ciudadRepository;

    @Mock
    private ValidateFindCiudadById validateFindCiudadById;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        // Puedes realizar inicializaciones si es necesario
    }

    @Test
    void testFindAllCiudades_NoContent() {
        // Configurar el mock para que el repositorio no tenga ciudades
        when(ciudadRepository.findAll()).thenReturn(Collections.emptyList());

        // Ejecutar y verificar que se lanza la excepción NoContentException
        NoContentException exception = assertThrows(NoContentException.class, () -> {
            ciudadService.findAllCiudades();
        });

        assertEquals("No se encontraron datos registrados.", exception.getMessage());
    }

    @Test
    void testCreateCiudad_AlreadyExists() {
        // Crear objeto de ciudad
        Ciudad ciudad = new Ciudad();
        ciudad.setNombreCiudad("Ciudad Test");
        ciudad.setCodigoCiudad("12345");

        // Configurar el mock para que devuelva true (ciudad ya existe)
        when(ciudadRepository.existsByNombreOrCodigo(anyString(), anyString())).thenReturn(true);

        // Ejecutar y verificar que se lanza la excepción ResourceAlreadyExistsException
        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            ciudadService.createCiudad(ciudad);
        });

        assertEquals("Ya existe una ciudad con el mismo nombre o código", exception.getMessage());
    }

    @Test
    void testUpdateCiudad_CityNotFound() {
        // Datos de la ciudad a actualizar
        Long ciudadId = 1L;
        CiudadRequestDTO ciudadRequestDTO = new CiudadRequestDTO();
        ciudadRequestDTO.setNombreCiudad("Ciudad Actualizada");
        ciudadRequestDTO.setCodigoCiudad("12345");

        // Configurar el mock para que la ciudad no exista
        when(validateFindCiudadById.findCiudadById(ciudadId)).thenThrow(new ResourceNotFoundException("Ciudad no encontrada", "updateCiudad"));

        // Ejecutar y verificar que se lanza la excepción ResourceNotFoundException
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            ciudadService.updateCiudad(ciudadId, ciudadRequestDTO);
        });

        assertEquals("Ciudad no encontrada", exception.getMessage());
    }

    @Test
    void testDeleteCiudad_WithRelatedRecords() {
        // ID de la ciudad a eliminar
        Long ciudadId = 1L;

        // Configurar el mock para que haya registros relacionados
        when(ciudadRepository.existeClienteEnCiudad(ciudadId)).thenReturn(true);

        // Ejecutar y verificar que se lanza la excepción InformationRelationalExceptions
        InformationRelationalExceptions exception = assertThrows(InformationRelationalExceptions.class, () -> {
            ciudadService.deleteCiudad(ciudadId);
        });

        assertEquals("No se puede eliminar la ciudad debido a información relacionada", exception.getMessage());
    }

    @Test
    void testDeleteCiudad_Success() {
        // ID de la ciudad a eliminar
        Long ciudadId = 1L;

        // Configurar el mock para que no haya registros relacionados
        when(ciudadRepository.existeClienteEnCiudad(ciudadId)).thenReturn(false);
        doNothing().when(ciudadRepository).deleteById(ciudadId);

        // Ejecutar y verificar que el resultado sea true (ciudad eliminada)
        Boolean result = ciudadService.deleteCiudad(ciudadId);

        assertTrue(result);
        verify(ciudadRepository, times(1)).deleteById(ciudadId);
    }
}