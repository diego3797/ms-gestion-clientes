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

    @Mapping(target = "fechaEsperada", ignore = true)
    @Mapping(target = "fechaRegistro", source = "fechaRegistro", dateFormat = "dd/MM/yyyy HH:mm:ss")
    @Mapping(target = "fechaNacimiento", expression = "java(new java.text.SimpleDateFormat(\"yyyy-MM-dd\").format(cliente.getFechaNacimiento()))")
    ClienteDTO toClienteDTOCreate(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "fechaNacimiento", expression = "java(new java.text.SimpleDateFormat(\"yyyy-MM-dd\").parse(clienteDTO.getFechaNacimiento()))")
    Cliente toCliente(ClienteDTO clienteDTO) throws ParseException;
}
