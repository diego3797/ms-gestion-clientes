package com.prueba.ms_gestion_clientes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
public class ClienteDTO {

    private Long id;

    private String nombre;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private int edad;

    private String fechaNacimiento;

    private String fechaRegistro;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fechaEsperada;

}
