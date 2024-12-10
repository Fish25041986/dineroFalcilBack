package pruebasUnitarias;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dineroFacil.prueba.entity.Ciudad;
import com.dineroFacil.prueba.exceptions.InformationRelationalExceptions;
import com.dineroFacil.prueba.exceptions.NoContentException;
import com.dineroFacil.prueba.exceptions.ResourceAlreadyExistsException;
import com.dineroFacil.prueba.repository.IciudadRepository;
import com.dineroFacil.prueba.services.CiudadService;
import com.dineroFacil.prueba.utils.Components.ValidateFindCiudadById;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CiudadServiceTest {

    @Mock
    private IciudadRepository ciudadRepository;

    @Mock
    private ValidateFindCiudadById validateFindCiudadById;

    @InjectMocks
    private CiudadService ciudadService;

    @Test
    void findAllCiudades_ReturnsListOfCiudades_WhenCiudadesExist() {
        List<Ciudad> ciudades = List.of(
                new Ciudad(1L, "Ciudad1", "123"),
                new Ciudad(2L, "Ciudad2", "456")
        );
        Mockito.when(ciudadRepository.findAll()).thenReturn(ciudades);

        List<Ciudad> result = ciudadService.findAllCiudades();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Ciudad1", result.get(0).getNombreCiudad());
    }

    @Test
    void findAllCiudades_ThrowsNoContentException_WhenNoCiudadesExist() {
        // Configurar el mock para que devuelva una lista vacía
        when(ciudadRepository.findAll()).thenReturn(Collections.emptyList());

        // Validar que se lance la excepción con los valores correctos
        NoContentException exception = assertThrows(NoContentException.class, () -> {
            ciudadService.findAllCiudades();
        });

        assertEquals("No se encontraron datos registrados.", exception.getMensaje());
        assertEquals("findAllCiudades", exception.getMetodoEnError());

        // Verificar que se llame al repositorio
        verify(ciudadRepository).findAll();
    }

    @Test
    void findIdCiudad_ReturnsCiudad_WhenCiudadExists() {
        Ciudad ciudad = new Ciudad(1L, "Ciudad1", "C1");
        Mockito.when(validateFindCiudadById.findCiudadById(1L)).thenReturn(ciudad);

        Ciudad result = ciudadService.findIdCiudad(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Ciudad1", result.getNombreCiudad());
    }

    @Test
    void createCiudad_SavesCiudad_WhenValidCiudadIsProvided() {
        Ciudad nuevaCiudad = new Ciudad(null, "CiudadNueva", "CN");
        Mockito.when(ciudadRepository.existsByNombreOrCodigo("CiudadNueva", "CN")).thenReturn(false);
        Mockito.when(ciudadRepository.save(nuevaCiudad)).thenReturn(new Ciudad(1L, "CiudadNueva", "CN"));

        Ciudad result = ciudadService.createCiudad(nuevaCiudad);

        Assertions.assertNotNull(result.getIdCiudad());
        Assertions.assertEquals("CiudadNueva", result.getNombreCiudad());
    }

    @Test
    void createCiudad_ThrowsResourceAlreadyExistsException_WhenCiudadExists() {
        // Configurar el mock para que `existsByNombreOrCodigo` devuelva true
        Ciudad ciudadDuplicada = new Ciudad(1L,"Ciudad Existente", "C123");
        when(ciudadRepository.existsByNombreOrCodigo(ciudadDuplicada.getNombreCiudad(), ciudadDuplicada.getCodigoCiudad())).thenReturn(true);

        // Validar que se lance la excepción con los valores correctos
        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            ciudadService.createCiudad(ciudadDuplicada);
        });

        assertEquals("Ya existe una ciudad con el mismo nombre o código", exception.getMensaje());
        assertEquals("createCiudad", exception.getMetodoEnError());

        // Verificar que se llame al repositorio
        verify(ciudadRepository).existsByNombreOrCodigo(ciudadDuplicada.getNombreCiudad(), ciudadDuplicada.getCodigoCiudad());
    }

    @Test
    void deleteCiudad_DeletesCiudad_WhenNoRelationsExist() {
        Long idCiudad = 1L;
        Mockito.doNothing().when(ciudadRepository).deleteById(idCiudad);
        Mockito.when(ciudadRepository.existeClienteEnCiudad(idCiudad)).thenReturn(false);
        Mockito.when(validateFindCiudadById.findCiudadById(idCiudad)).thenReturn(new Ciudad(idCiudad, "Ciudad", "C"));

        Boolean result = ciudadService.deleteCiudad(idCiudad);

        Assertions.assertTrue(result);
    }

    @Test
    void deleteCiudad_ThrowsInformationRelationalExceptions_WhenRelationsExist() {
        // Configurar el mock para que `existeClienteEnCiudad` devuelva true
        Long idCiudad = 1L;
        when(ciudadRepository.existeClienteEnCiudad(idCiudad)).thenReturn(true);

        // Validar que se lance la excepción con los valores correctos
        InformationRelationalExceptions exception = assertThrows(InformationRelationalExceptions.class, () -> {
            ciudadService.deleteCiudad(idCiudad);
        });

        assertEquals("No se puede eliminar la ciudad debido a información relacionada", exception.getMensaje());
        assertEquals("deleteCiudad", exception.getMetodoEnError());

        // Verificar que se llame al repositorio
        verify(ciudadRepository).existeClienteEnCiudad(idCiudad);
    }
}