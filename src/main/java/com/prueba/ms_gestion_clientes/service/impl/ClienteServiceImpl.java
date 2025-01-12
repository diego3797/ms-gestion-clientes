package com.prueba.ms_gestion_clientes.service.impl;

import com.prueba.ms_gestion_clientes.model.Cliente;
import com.prueba.ms_gestion_clientes.repository.ClienteRepository;
import com.prueba.ms_gestion_clientes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

/**
 * Encuentra la desviación estándar de la edad de todos los clientes.
 *
 * @return Un Optional que contiene la desviación estándar de la edad de los clientes.
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Crea un nuevo cliente.
     *
     * @param cliente El cliente a crear.
     * @return El cliente creado.
     */
    @Override
    public Cliente create(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Encuentra todos los clientes.
     *
     * @return Una lista de todos los clientes.
     */
    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    /**
     * Encuentra la edad promedio de todos los clientes.
     *
     * @return Un Optional que contiene la edad promedio de los clientes.
     */
    @Override
    public Optional<Double> obtenerEdadPromedio() {
        return clienteRepository.obtenerEdadPromedio();
    }

    /**
     * Encuentra la desviación estándar de la edad de todos los clientes.
     *
     * @return Un Optional que contiene la desviación estándar de la edad de los clientes.
     */
    @Override
    public Optional<Double> obtenerEdadDesviacionEstandar() {
        return clienteRepository.obtenerEdadDesviacionEstandar();
    }


    /**
     * Calcula la fecha esperada de vida de un cliente.
     *
     * @param cliente El cliente para el cual calcular la fecha esperada de vida.
     * @return La fecha esperada de vida del cliente.
     */
    @Override
    public LocalDate calcularFechaEsperada(Cliente cliente) {

        int esperanzaDeVida = 73;

        // Fecha de nacimiento
        LocalDate fechaNacimiento = cliente.getFechaNacimiento().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();

        if (edad < esperanzaDeVida) {
            return fechaNacimiento.plusYears(esperanzaDeVida);
        } else {
            return LocalDate.now();
        }
    }
}
