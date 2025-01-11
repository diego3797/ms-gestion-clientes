package com.prueba.ms_gestion_clientes.service.impl;

import com.prueba.ms_gestion_clientes.dto.ClienteDTO;
import com.prueba.ms_gestion_clientes.model.Cliente;
import com.prueba.ms_gestion_clientes.repository.ClienteRepository;
import com.prueba.ms_gestion_clientes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente create(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Double> findAverageAge() {
        return clienteRepository.findAverageAge();
    }

    @Override
    public Optional<Double> findAgeStandardDeviation() {
        return clienteRepository.findAgeStandardDeviation();
    }

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
