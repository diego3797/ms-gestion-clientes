package com.prueba.ms_gestion_clientes.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.prueba.ms_gestion_clientes.model.Cliente;
import com.prueba.ms_gestion_clientes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createClienteSuccessfully() {
        Cliente cliente = new Cliente();
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente result = clienteService.create(cliente);

        assertEquals(cliente, result);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void findAllClientesSuccessfully() {
        List<Cliente> clientes = List.of(new Cliente(), new Cliente());
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.findAll();

        assertEquals(clientes, result);
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void obtenerEdadPromedioSuccessfully() {
        Optional<Double> edadPromedio = Optional.of(30.0);
        when(clienteRepository.obtenerEdadPromedio()).thenReturn(edadPromedio);

        Optional<Double> result = clienteService.obtenerEdadPromedio();

        assertEquals(edadPromedio, result);
        verify(clienteRepository, times(1)).obtenerEdadPromedio();
    }

    @Test
    void obtenerEdadDesviacionEstandarSuccessfully() {
        Optional<Double> desviacionEstandar = Optional.of(5.0);
        when(clienteRepository.obtenerEdadDesviacionEstandar()).thenReturn(desviacionEstandar);

        Optional<Double> result = clienteService.obtenerEdadDesviacionEstandar();

        assertEquals(desviacionEstandar, result);
        verify(clienteRepository, times(1)).obtenerEdadDesviacionEstandar();
    }

    @Test
    void calcularFechaEsperadaForYoungCliente() {
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(Date.from(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        LocalDate expectedDate = LocalDate.of(2000, 1, 1).plusYears(73);
        LocalDate result = clienteService.calcularFechaEsperada(cliente);

        assertEquals(expectedDate, result);
    }

    @Test
    void calcularFechaEsperadaForOldCliente() {
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(Date.from(LocalDate.of(1940, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        LocalDate expectedDate = LocalDate.now();
        LocalDate result = clienteService.calcularFechaEsperada(cliente);

        assertEquals(expectedDate, result);
    }
}