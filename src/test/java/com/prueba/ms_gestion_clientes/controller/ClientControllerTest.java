package com.prueba.ms_gestion_clientes.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.prueba.ms_gestion_clientes.dto.ClienteDTO;
import com.prueba.ms_gestion_clientes.model.Cliente;
import com.prueba.ms_gestion_clientes.service.ClienteService;
import com.prueba.ms_gestion_clientes.utils.ClienteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

class ClientControllerTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createClientSuccessfully() throws ParseException {
        ClienteDTO clienteDto = new ClienteDTO();
        Cliente cliente = new Cliente();
        ClienteDTO createdClienteDto = new ClienteDTO();

        when(clienteMapper.toCliente(clienteDto)).thenReturn(cliente);
        when(clienteService.create(cliente)).thenReturn(cliente);
        when(clienteMapper.toClienteDTOCreate(cliente)).thenReturn(createdClienteDto);

        ResponseEntity<ClienteDTO> response = clientController.createClient(clienteDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdClienteDto, response.getBody());
    }

    @Test
    void createClientThrowsException() throws ParseException {
        ClienteDTO clienteDto = new ClienteDTO();
        Cliente cliente = new Cliente();

        when(clienteMapper.toCliente(clienteDto)).thenReturn(cliente);
        when(clienteService.create(cliente)).thenThrow(new RuntimeException("Error"));

        assertThrows(ResponseStatusException.class, () -> clientController.createClient(clienteDto));
    }

    @Test
    void getAllClients_Success() throws ParseException {
        // Datos simulados

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Cliente  cliente1 = Cliente.builder()
                .id(1L)
                .nombre("Juan")
                .apellidoPaterno("Perez")
                .apellidoMaterno("Lopez")
                .edad(30)
                .fechaNacimiento(formatter.parse("1993-01-15"))
                .fechaRegistro(LocalDateTime.of(2023, 1, 1, 10, 30))
                .build();

        Cliente cliente2 = Cliente.builder()
                .id(2L)
                .nombre("Maria")
                .apellidoPaterno("Gomez")
                .apellidoMaterno("Diaz")
                .edad(25)
                .fechaNacimiento(formatter.parse("1998-05-20"))
                .fechaRegistro(LocalDateTime.of(2023, 2, 15, 14, 45))
                .build();

        List<Cliente> clientesSimulados = Arrays.asList(cliente1, cliente2);

        // Mock del método calcularFechaEsperada
        when(clienteService.findAll()).thenReturn(clientesSimulados);
        when(clienteService.calcularFechaEsperada(cliente1)).thenReturn(LocalDate.of(2063, 1, 15));
        when(clienteService.calcularFechaEsperada(cliente2)).thenReturn(LocalDate.of(2063, 5, 20));

        // Llamada al método bajo prueba
        ResponseEntity<List<ClienteDTO>> response = clientController.getAllClients();

        // Verificaciones del estado de la respuesta
        assertNotNull(response, "La respuesta no debe ser nula");
        assertEquals(200, response.getStatusCodeValue(), "El código de estado debe ser 200 OK");
        assertNotNull(response.getBody(), "El cuerpo de la respuesta no debe ser nulo");

        List<ClienteDTO> clienteDTOs = response.getBody();
        assertEquals(2, clienteDTOs.size(), "El tamaño de la lista debe ser 2");



        // Validar ClienteDTO 1
        ClienteDTO dto1 = clienteDTOs.get(0);
        assertEquals(1L, dto1.getId(), "El ID del cliente 1 debe ser 1");
        assertEquals("Juan", dto1.getNombre(), "El nombre del cliente 1 debe ser Juan");
        assertEquals("Perez", dto1.getApellidoPaterno(), "El apellido paterno del cliente 1 debe ser Perez");
        assertEquals("Lopez", dto1.getApellidoMaterno(), "El apellido materno del cliente 1 debe ser Lopez");
        assertEquals(30, dto1.getEdad(), "La edad del cliente 1 debe ser 30");
        assertEquals("1993-01-15" , dto1.getFechaNacimiento(), "La fecha de nacimiento debe coincidir");
        assertEquals("01/01/2023 10:30:00", dto1.getFechaRegistro(), "La fecha de registro debe coincidir");
        assertEquals("2063-01-15", dto1.getFechaEsperada(), "La fecha esperada debe coincidir");

        // Validar ClienteDTO 2
        ClienteDTO dto2 = clienteDTOs.get(1);
        assertEquals(2L, dto2.getId(), "El ID del cliente 2 debe ser 2");
        assertEquals("Maria", dto2.getNombre(), "El nombre del cliente 2 debe ser Maria");
        assertEquals("Gomez", dto2.getApellidoPaterno(), "El apellido paterno del cliente 2 debe ser Gomez");
        assertEquals("Diaz", dto2.getApellidoMaterno(), "El apellido materno del cliente 2 debe ser Diaz");
        assertEquals(25, dto2.getEdad(), "La edad del cliente 2 debe ser 25");
        assertEquals("1998-05-20", dto2.getFechaNacimiento(), "La fecha de nacimiento debe coincidir");
        assertEquals("15/02/2023 14:45:00", dto2.getFechaRegistro(), "La fecha de registro debe coincidir");
        assertEquals("2063-05-20", dto2.getFechaEsperada(), "La fecha esperada debe coincidir");

        // Verificar interacción con los mocks
        verify(clienteService, times(1)).findAll();
        verify(clienteService, times(1)).calcularFechaEsperada(cliente1);
        verify(clienteService, times(1)).calcularFechaEsperada(cliente2);
    }

    @Test
    void getAllClientsThrowsException() {
        when(clienteService.findAll()).thenThrow(new RuntimeException("Error"));

        assertThrows(ResponseStatusException.class, () -> clientController.getAllClients());
    }

    @Test
    void getMetricsSuccessfully() {
        Optional<Double> averageAge = Optional.of(30.0);
        Optional<Double> standardDeviation = Optional.of(5.0);

        when(clienteService.obtenerEdadPromedio()).thenReturn(averageAge);
        when(clienteService.obtenerEdadDesviacionEstandar()).thenReturn(standardDeviation);

        ResponseEntity<String> response = clientController.getMetrics();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Promedio de edad: 30.0"));
        assertTrue(response.getBody().contains("Desviación estándar: 5.0"));
    }

    @Test
    void getMetricsThrowsException() {
        when(clienteService.obtenerEdadPromedio()).thenThrow(new RuntimeException("Error"));

        assertThrows(ResponseStatusException.class, () -> clientController.getMetrics());
    }
}