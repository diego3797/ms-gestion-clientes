package com.prueba.ms_gestion_clientes.utils;

import com.prueba.ms_gestion_clientes.dto.ClienteDTO;
import com.prueba.ms_gestion_clientes.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    /**
     * Calcula la fecha esperada de vida de un cliente.
     *
     * @param cliente El cliente para el cual calcular la fecha esperada de vida.
     * @return La fecha esperada de vida del cliente.
     */
    @Mapping(target = "fechaEsperada", ignore = true)
    @Mapping(target = "fechaRegistro", source = "fechaRegistro", dateFormat = "dd/MM/yyyy HH:mm:ss")
    @Mapping(target = "fechaNacimiento", expression = "java(new java.text.SimpleDateFormat(\"yyyy-MM-dd\").format(cliente.getFechaNacimiento()))")
    ClienteDTO toClienteDTOCreate(Cliente cliente);

    /**
     * Convierte un DTO ClienteDTO a una entidad Cliente.
     *
     * @param clienteDTO El DTO ClienteDTO a convertir.
     * @return La entidad Cliente resultante.
     * @throws ParseException Si ocurre un error al parsear la fecha de nacimiento.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "fechaNacimiento", expression = "java(new java.text.SimpleDateFormat(\"yyyy-MM-dd\").parse(clienteDTO.getFechaNacimiento()))")
    Cliente toCliente(ClienteDTO clienteDTO) throws ParseException;
}
