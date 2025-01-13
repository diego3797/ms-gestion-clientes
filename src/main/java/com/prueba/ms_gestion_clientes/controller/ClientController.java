package com.prueba.ms_gestion_clientes.controller;

import com.prueba.ms_gestion_clientes.dto.ClienteDTO;
import com.prueba.ms_gestion_clientes.model.Cliente;
import com.prueba.ms_gestion_clientes.service.ClienteService;
import com.prueba.ms_gestion_clientes.utils.ClienteMapper;
import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cliente")
public class ClientController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteMapper clienteMapper;


    /**
     * Crea un nuevo cliente.
     *
     * @param clienteDto El DTO del cliente a crear.
     * @return El DTO del cliente creado.
     */
    @Operation(
            operationId = "createClient",
            summary = "Create a new client",
            description = "Create a new client",
            tags = { "client" },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successful operation",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ClienteDTO.class)),
                                    @Content(mediaType = "application/xml",
                                            schema = @Schema(implementation = ClienteDTO.class))
                            }),
                    @ApiResponse(responseCode = "405", description = "Invalid input")
            }
    )
    @PostMapping
    public ResponseEntity<ClienteDTO> createClient(@RequestBody ClienteDTO clienteDto) {
        try {
            Cliente cli = clienteMapper.toCliente(clienteDto);
            ClienteDTO cliDto = clienteMapper.toClienteDTOCreate(clienteService.create(cli));
            return ResponseEntity.status(HttpStatus.CREATED).body(cliDto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error al crear el cliente", e);
        }
    }

    /**
     * Obtiene una lista de todos los clientes.
     *
     * @return Una lista de DTOs de clientes.
     */
    @Operation(
            operationId = "getAllClients",
            summary = "List all clients",
            description = "List all clients",
            tags = { "client" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClienteDTO.class)))
            }
    )
    @GetMapping("/listar")
    public ResponseEntity<List<ClienteDTO>> getAllClients() {
        try {
            List<Cliente> clientes = clienteService.findAll();

            List<ClienteDTO> clientesDTO = clientes.stream().map(cliente -> {
                ClienteDTO clienteDTO = new ClienteDTO();
                clienteDTO.setId(cliente.getId());
                clienteDTO.setNombre(cliente.getNombre());
                clienteDTO.setApellidoPaterno(cliente.getApellidoPaterno());
                clienteDTO.setApellidoMaterno(cliente.getApellidoMaterno());
                clienteDTO.setEdad(cliente.getEdad());
                clienteDTO.setFechaNacimiento(new java.text.SimpleDateFormat("yyyy-MM-dd").format(cliente.getFechaNacimiento()));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                clienteDTO.setFechaRegistro(cliente.getFechaRegistro().format(formatter));

                // Calcular la fecha esperada
                LocalDate fechaEsperada = clienteService.calcularFechaEsperada(cliente);
                clienteDTO.setFechaEsperada(fechaEsperada.toString());

                return clienteDTO;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(clientesDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener los clientes", e);
        }


    }

    /**
     * Obtiene las métricas de los clientes.
     *
     * @return Una cadena con el promedio de edad y la desviación estándar.
     */
    @Operation(
            operationId = "getMetrics",
            summary = "Get metrics of clients",
            description = "Get metrics of clients",
            tags = { "client" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClienteDTO.class)))
            }
    )
    @GetMapping("/metricas")
    public ResponseEntity<String> getMetrics() {
        try {
            Optional<Double> averageAge = clienteService.obtenerEdadPromedio();
            Optional<Double> standardDeviation = clienteService.obtenerEdadDesviacionEstandar();

            if (averageAge.isPresent() && standardDeviation.isPresent()) {
                return ResponseEntity.ok(
                        "Promedio de edad: " + averageAge.get() + ", Desviación estándar: " + standardDeviation.get()
                );
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se pudieron calcular las métricas");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al calcular métricas", e);
        }
    }

}
