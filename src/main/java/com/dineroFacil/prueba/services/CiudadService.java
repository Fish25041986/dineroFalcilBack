package com.dineroFacil.prueba.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dineroFacil.prueba.dto.CiudadRequestDTO;
import com.dineroFacil.prueba.entity.Ciudad;
import com.dineroFacil.prueba.exceptions.InformationRelationalExceptions;
import com.dineroFacil.prueba.exceptions.InternaServerErrorException;
import com.dineroFacil.prueba.exceptions.NoContentException;
import com.dineroFacil.prueba.exceptions.ResourceAlreadyExistsException;
import com.dineroFacil.prueba.repository.IciudadRepository;
import com.dineroFacil.prueba.utils.Components.ValidateFindCiudadById;

import jakarta.transaction.Transactional;

@Service
public class CiudadService {

    private final IciudadRepository ciudadRepository;
    private final ValidateFindCiudadById validateFindCiudadById;

    public CiudadService(IciudadRepository ciudadRepository, ValidateFindCiudadById validateFindCiudadById) {
        this.ciudadRepository = ciudadRepository;
        this.validateFindCiudadById = validateFindCiudadById;
    }

    private static final Logger logger = LoggerFactory.getLogger(CiudadService.class);

    /**
     * Obtiene todas las ciudades registradas.
     * 
     * @return Lista de todas las ciudades.
     * @throws NoContentException Si no se encuentran ciudades registradas.
     */
    public List<Ciudad> findAllCiudades() {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        if (ciudades.isEmpty()) {
            logger.error("No existen registros actualmente en Ciudades");
            throw new NoContentException("No se encontraron datos registrados.", "findAllCiudades");
        }
        return ciudades;
    }

    /**
     * Obtiene una ciudad por su ID.
     * 
     * @param idCiudad El ID de la ciudad a buscar.
     * @return La ciudad correspondiente al ID proporcionado.
     * @throws ResourceNotFoundException Si no se encuentra la ciudad con el ID especificado.
     */
    public Ciudad findIdCiudad(Long idCiudad) {
        return validateFindCiudadById.findCiudadById(idCiudad);
    }

    
    /**
     * Crea una nueva ciudad.
     * 
     * @param ciudad Objeto ciudad a guardar.
     * @return La ciudad guardada.
     * @throws ResourceAlreadyExistsException Si ya existe una ciudad con el mismo nombre o código.
     * @throws InternaServerErrorException Si ocurre un error al intentar guardar la ciudad.
     */
    @Transactional
    public Ciudad createCiudad(Ciudad ciudad) {
    	
        Ciudad ciudadSave;
        
        // Validar si ya existe una ciudad con el mismo nombre o código
        boolean existe = ciudadRepository.existsByNombreOrCodigo(ciudad.getNombreCiudad(), ciudad.getCodigoCiudad());
        if (existe) {
        	System.out.println(existe);
            logger.error("Ya existe una ciudad con el mismo nombre o código");
            throw new ResourceAlreadyExistsException("Ya existe una ciudad con el mismo nombre o código", "createCiudad");
        }
        
        // Guardar la ciudad
        try {
            ciudadSave = ciudadRepository.save(ciudad);
        } catch (Exception e) {
            logger.error("Ocurrió un error al intentar guardar la ciudad");
            throw new InternaServerErrorException("Fallo el proceso de guardar la ciudad", "createCiudad");
        }
        
        return ciudadSave;
    }

    
    /**
     * Actualiza los datos de una ciudad existente.
     * 
     * @param idCiudad El ID de la ciudad a actualizar.
     * @param ciudadRequestDTO Objeto con los nuevos datos de la ciudad.
     * @return La ciudad actualizada.
     * @throws ResourceNotFoundException Si no se encuentra la ciudad con el ID especificado.
     * @throws InternaServerErrorException Si ocurre un error al intentar actualizar la ciudad.
     */
    @Transactional
    public Ciudad updateCiudad(Long idCiudad, CiudadRequestDTO ciudadRequestDTO) {
    	
        // Validar existencia de la ciudad
        Ciudad ciudadToUpdate = validateFindCiudadById.findCiudadById(idCiudad);

        // Actualizar los campos
        ciudadToUpdate.setNombreCiudad(ciudadRequestDTO.getNombreCiudad());
        ciudadToUpdate.setCodigoCiudad(ciudadRequestDTO.getCodigoCiudad());

        //Guarda la modificación de la ciudad
        try {
            ciudadRepository.save(ciudadToUpdate);
        } catch (Exception e) {
            logger.error("Ocurrió un error al intentar modificar la ciudad");
            throw new InternaServerErrorException("Fallo el proceso de modificar la ciudad", "updateCiudad");
        }

        return ciudadToUpdate;
    }

    
    /**
     * Elimina una ciudad.
     * 
     * @param idCiudad El ID de la ciudad a eliminar.
     * @return true si la ciudad se eliminó exitosamente, false en caso contrario.
     * @throws InformationRelationalExceptions Si existen registros relacionados que impiden la eliminación.
     * @throws InternaServerErrorException Si ocurre un error al intentar eliminar la ciudad.
     */
    @Transactional
    public Boolean deleteCiudad(Long idCiudad) {
    	
    	// Validar existencia de la ciudad
        validateFindCiudadById.findCiudadById(idCiudad);

        // Verificar si existen registros relacionados
        if (ciudadRepository.existeClienteEnCiudad(idCiudad)) {
            logger.error("No se puede eliminar la ciudad ya que existen registros relacionados");
            throw new InformationRelationalExceptions("No se puede eliminar la ciudad debido a información relacionada", "deleteCiudad");
        }
 
        // elimina la ciudad de la base de datos
        try {
            ciudadRepository.deleteById(idCiudad);
            return true;
        } catch (Exception e) {
            logger.error("Ocurrió un error al intentar eliminar la ciudad");
            throw new InternaServerErrorException("Fallo el proceso de eliminar la ciudad", "deleteCiudad");
        }
    }
}