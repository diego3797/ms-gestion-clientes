package com.prueba.ms_gestion_clientes.service;

import com.prueba.ms_gestion_clientes.dto.ClienteDTO;
import com.prueba.ms_gestion_clientes.model.Cliente;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClienteService {

    Cliente create(Cliente cliente);

    List<Cliente> findAll();

    Optional<Double> findAverageAge();

    Optional<Double> findAgeStandardDeviation();

    LocalDate calcularFechaEsperada(Cliente cliente);
}
