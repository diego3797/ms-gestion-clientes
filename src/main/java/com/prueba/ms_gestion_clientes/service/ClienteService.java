package com.prueba.ms_gestion_clientes.service;

import com.prueba.ms_gestion_clientes.model.Cliente;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClienteService {

    /**
     * Crea un nuevo cliente.
     *
     * @param cliente El cliente a crear.
     * @return El cliente creado.
     */
    Cliente create(Cliente cliente);

    /**
     * Encuentra todos los clientes.
     *
     * @return Una lista de todos los clientes.
     */
    List<Cliente> findAll();

    /**
     * Obtiene la edad promedio de todos los clientes.
     *
     * @return Un Optional que contiene la edad promedio de los clientes.
     */
    Optional<Double> obtenerEdadPromedio();

    /**
     * Obtiene la desviación estándar de la edad de todos los clientes.
     *
     * @return Un Optional que contiene la desviación estándar de la edad de los clientes.
     */
    Optional<Double> obtenerEdadDesviacionEstandar();

    /**
     * Calcula la fecha esperada de vida de un cliente.
     *
     * @param cliente El cliente para el cual calcular la fecha esperada de vida.
     * @return La fecha esperada de vida del cliente.
     */
    LocalDate calcularFechaEsperada(Cliente cliente);
}
