package com.dineroFacil.prueba.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dineroFacil.prueba.dto.ClienteDTO;
import com.dineroFacil.prueba.dto.ClienteRequestDTO;
import com.dineroFacil.prueba.dto.converter.ClienteDtoConverte;
import com.dineroFacil.prueba.entity.Ciudad;
import com.dineroFacil.prueba.entity.Cliente;
import com.dineroFacil.prueba.exceptions.InformationRelationalExceptions;
import com.dineroFacil.prueba.exceptions.InternaServerErrorException;
import com.dineroFacil.prueba.exceptions.NoContentException;
import com.dineroFacil.prueba.exceptions.ResourceAlreadyExistsException;
import com.dineroFacil.prueba.repository.IclienteRepository;
import com.dineroFacil.prueba.utils.Components.ValidateFindCiudadById;
import com.dineroFacil.prueba.utils.Components.ValidateFindClienteById;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {
	
	@Autowired
	IclienteRepository clienteRepository;
	
	@Autowired
	ValidateFindClienteById validateFindClienteById;
	
    @Autowired
    private ValidateFindCiudadById validateFindCiudadById;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
	private final ClienteDtoConverte clienteDtoConverte = new ClienteDtoConverte();

    /**
     * Obtiene todas las clientes registrados.
     * 
     * @return Lista de todas los clientes.
     * @throws NoContentException Si no se encuentran clientes registrados.
     */
    public List<ClienteDTO> findAllCliente() {
        List<Cliente> clientes = clienteRepository.findAll();
        if (clientes.isEmpty()) {
            logger.error("No existen registros actualmente en cliente");
            throw new NoContentException("No se encontraron datos registrados.", "findAllCliente");
        }
	    return clientes.stream()
	            .map(clienteDtoConverte::clienteDTO)
	            .collect(Collectors.toList());
    }

    /**
     * Obtiene un cliente por su ID.
     * 
     * @param idCliente El ID del cliente a buscar.
     * @return El cliente correspondiente al ID proporcionado.
     * @throws ResourceNotFoundException Si no se encuentra un cliente con el ID especificado.
     */
    public ClienteDTO findIdCliente(Long idCliente) {
        // Validar que el cliente existe y obtenerlo
        Cliente cliente = validateFindClienteById.findClienteById(idCliente);
        
        // Convertir el Cliente a ClienteDTO
        return clienteDtoConverte.clienteDTO(cliente);
    }

    
    /**
     * Crea un nuev cliente.
     * 
     * @param cliente Objeto cliente a guardar.
     * @return El cliente guardado.
     * @throws ResourceAlreadyExistsException Si ya existe un cliente con el mismo numero de docuemnto.
     * @throws InternaServerErrorException Si ocurre un error al intentar guardar el cliente.
     */
    @Transactional
    public ClienteDTO createCliente(ClienteRequestDTO clienteRequestDTO) {
    	
    	Cliente clienteSave;
    	
        
        // Valido si ya existe un cliente con el mismo numero de cedula
        boolean existe = clienteRepository.existsByNumeroDocumento(clienteRequestDTO.getNumeroDocumento());
        if (existe) {
            logger.error("Ya existe un cliente con el mismo numero de cedula");
            throw new ResourceAlreadyExistsException("Ya existe un cliente con el mismo numero de cedula", "createCliente");
        }
        
    	// Validar existencia de la ciudad
        Ciudad ciudad = validateFindCiudadById.findCiudadById(clienteRequestDTO.getIdCiudad());
        
        //Convierto el dto a cliente
        Cliente cliente;
        try {
        	cliente=clienteDtoConverte.clienteRequestDtoToEntity(clienteRequestDTO, ciudad);
		} catch (Exception e) {
            logger.error("Ocurrió un error al convertir  el DTO entrante a cliente");
            throw new InternaServerErrorException("Ocurrió un error al convertir  el DTO entrante a cliente", "createCliente");
		}
        
        // Guardar el cliente y lo convierto a DTO
        ClienteDTO clienteDTO;
        try {
        	clienteSave = clienteRepository.save(cliente);
        	clienteDTO=clienteDtoConverte.clienteDTO(clienteSave);
        } catch (Exception e) {
            logger.error("Ocurrió un error al intentar guardar el cliente");
            throw new InternaServerErrorException("Fallo el proceso de guardar el cliente", "createCliente");
        }
        
        return clienteDTO;
    }

    
    /**
     * Actualiza los datos de un cliente existente.
     * 
     * @param idCliente El ID del cliente a actualizar.
     * @param clienteRequestDTO Objeto con los nuevos datos del cliente.
     * @return El cliente actualizado.
     * @throws ResourceNotFoundException Si no se encuentra el cliente con el ID especificado.
     * @throws InternaServerErrorException Si ocurre un error al intentar actualizar el cliente.
     */
    @Transactional
    public ClienteDTO updateCliente(Long idCliente, ClienteRequestDTO clienteRequestDTO) {
    	
        // Validar existencia del cliente
    	Cliente clienteToUpdate = validateFindClienteById.findClienteById(idCliente);
    	
    	// Validar existencia de la ciudad
        Ciudad ciudad = validateFindCiudadById.findCiudadById(clienteRequestDTO.getIdCiudad());

        // Actualizar los campos 
    	clienteToUpdate.setTipoDocumento(clienteRequestDTO.getTipoDocumento());
    	clienteToUpdate.setNumeroDocumento(clienteRequestDTO.getNumeroDocumento());  
    	clienteToUpdate.setApellidos(clienteRequestDTO.getApellidos()); 
    	clienteToUpdate.setResidencia(clienteRequestDTO.getResidencia()); 
    	clienteToUpdate.setCiudad(ciudad);
    	clienteToUpdate.setTelefono(clienteRequestDTO.getTelefono()); 

        //Guarda la modificación del cliente y convierto a DTO
    	ClienteDTO clienteDTO;
        try {
        	clienteRepository.save(clienteToUpdate);
        	clienteDTO=clienteDtoConverte.clienteDTO(clienteToUpdate);
        } catch (Exception e) {
            logger.error("Ocurrió un error al intentar modificar el cliente");
            throw new InternaServerErrorException("Fallo el proceso de modificar el cliente", "updateCliente");
        }

        return clienteDTO;
    }

    
    /**
     * Elimina un cliente.
     * 
     * @param idCliente El ID del cliente a eliminar.
     * @return true si el cliente se eliminó exitosamente, false en caso contrario.
     * @throws InformationRelationalExceptions Si existen registros relacionados que impiden la eliminación.
     * @throws InternaServerErrorException Si ocurre un error al intentar eliminar el cliente.
     */
    @Transactional
    public Boolean deleteCliente(Long idCliente) {
    	
        // Validar existencia del cliente
    	validateFindClienteById.findClienteById(idCliente);

        // Verificar si existen registros relacionados
        if (clienteRepository.existsByCredito(idCliente)==1) {
            logger.error("No se puede eliminar el Cliente ya que existen registros relacionados");
            throw new InformationRelationalExceptions("No se puede eliminar el cliented debido a información relacionada", "deleteCliente");
        }
 
        // elimina el cliente de la base de datos
        try {
        	clienteRepository.deleteById(idCliente);
            return true;
        } catch (Exception e) {
            logger.error("Ocurrió un error al intentar eliminar el cliente");
            throw new InternaServerErrorException("Fallo el proceso de eliminar el cliente", "deleteCliente");
        }
    }

}
