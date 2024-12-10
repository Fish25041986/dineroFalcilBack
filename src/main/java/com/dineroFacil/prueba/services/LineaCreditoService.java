package com.dineroFacil.prueba.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dineroFacil.prueba.entity.LineaCredito;
import com.dineroFacil.prueba.exceptions.InformationRelationalExceptions;
import com.dineroFacil.prueba.exceptions.InternaServerErrorException;
import com.dineroFacil.prueba.exceptions.NoContentException;
import com.dineroFacil.prueba.exceptions.ResourceAlreadyExistsException;
import com.dineroFacil.prueba.repository.IlineaCreditoRepository;
import com.dineroFacil.prueba.utils.Components.ValidateFindLineaCreditoById;

import jakarta.transaction.Transactional;

@Service
public class LineaCreditoService {
	
	@Autowired
	IlineaCreditoRepository lineaCreditoRepository;
	
	@Autowired
	ValidateFindLineaCreditoById validateFindLineaCreditoById;
	
    private static final Logger logger = LoggerFactory.getLogger(CiudadService.class);
	
    /**
     * Obtiene todas las Lineas registradas.
     * 
     * @return Lista de todas las lineas.
     * @throws NoContentException Si no se encuentran lineas registradas.
     */
	public List<LineaCredito> findAllLineaCredito(){
		List<LineaCredito> lineasCreditos= lineaCreditoRepository.findAll();
		if (lineasCreditos.isEmpty()) {
            logger.error("No existen registros actualmente en LineaCreditos");
            throw new NoContentException("No se encontraron datos registrados.", "findAllLineaCredito");
		}
		return lineasCreditos;
	}
	
	
    /**
     * Obtiene una linea por su ID.
     * 
     * @param idLinea El ID de la linea a buscar.
     * @return La linea correspondiente al ID proporcionado.
     * @throws ResourceNotFoundException Si no se encuentra la linea con el ID especificado.
     */
	public LineaCredito findIdLineaCredito(Long idLinea){
		return validateFindLineaCreditoById.findLineaCreditoById(idLinea);
	}
	
	
    /**
     * Crea una nueva Linea.
     * 
     * @param lineaCredito Objeto LineaCredito a guardar.
     * @return La linea guardada.
     * @throws ResourceAlreadyExistsException Si ya existe una linea con el mismo nombre.
     * @throws InternaServerErrorException Si ocurre un error al intentar guardar la linea.
     */
	@Transactional
	public LineaCredito createLineaCredito(LineaCredito lineaCredito) {
		
	   LineaCredito lineaCreditoSave;
		
	   if (lineaCreditoRepository.existsByNombreLinea(lineaCredito.getNombreLinea())) {
	       logger.error("No existen registros actualmente en LineaCreditos");
	   throw new ResourceAlreadyExistsException("Ya existe una Linea de credito con el mismo nombre.", "findAllLineaCredito");
	   }
	       
		try {
		   lineaCreditoSave=lineaCreditoRepository.save(lineaCredito);
		} catch (Exception e) {
            logger.error("Ocurrió un error al intentar guardar la Linea de credito");
            throw new InternaServerErrorException("Fallo el proceso de guardar la linea de credito", "createLineaCredito");
		}
       
        return lineaCreditoSave;
		
	}
	
	
    /**
     * Actualiza los datos de una linea creadito existente.
     * 
     * @param idLinea El ID de la linea a actualizar.
     * @param LineaCredito Objeto con los nuevos datos de la linea.
     * @return La linea actualizada.
     * @throws ResourceNotFoundException Si no se encuentra la linea con el ID especificado.
     * @throws InternaServerErrorException Si ocurre un error al intentar actualizar la linea.
     */
	@Transactional
	public LineaCredito updateLineaCredito(Long idLinea, LineaCredito lineaCredito) {
		
		//valido si existe un registro con el id proporcionado
		LineaCredito updateLinea = validateFindLineaCreditoById.findLineaCreditoById(idLinea);
		
		// Actualizar los campos
		updateLinea.setNombreLinea(lineaCredito.getNombreLinea());
		updateLinea.setMontoMinimo(lineaCredito.getMontoMinimo());
		updateLinea.setMontoMaximo(lineaCredito.getMontoMaximo());
		
		//Guarda la modificación de la linea Credito
		try {
			lineaCreditoRepository.save(updateLinea);
		} catch (Exception e) {
            logger.error("Ocurrió un error al intentar actualizar la Linea de credito");
            throw new InternaServerErrorException("Fallo el proceso de actualizar la linea de credito", "updateLineaCredito");
		}
		
		return updateLinea;
		
	}
	
	
    /**
     * Elimina una linea.
     * 
     * @param idLinea El ID de la linea a eliminar.
     * @return true si la linea se eliminó exitosamente, false en caso contrario.
     * @throws InformationRelationalExceptions Si existen registros relacionados que impiden la eliminación.
     * @throws InternaServerErrorException Si ocurre un error al intentar eliminar la ciudad.
     */
	@Transactional
	public Boolean deleteLineaCredito(Long idLinea) {
		
		//valido si existe un registro con el id proporcionado
		validateFindLineaCreditoById.findLineaCreditoById(idLinea);
		
		//Valido que no exista información relacionada con el dato a eliminar
		if (lineaCreditoRepository.existsByLineaCredito(idLinea) == 1) {
		    logger.error("No se puede eliminar la linea de credito ya que existen registros relacionados");
		    throw new InformationRelationalExceptions("No se puede eliminar linea de credito debido a información relacionada", "deleteLineaCredito");
		}
		
		// elimina la linea de la base de datos
		try {
			lineaCreditoRepository.deleteById(idLinea);
			return true;
		} catch (Exception e) {
			logger.error("Ocurrió un error al intentar eliminar la linea de credito");
            throw new InternaServerErrorException("Fallo el proceso de eliminar la linea de credito", "deleteLineaCredito");
		}
		
	}


}
