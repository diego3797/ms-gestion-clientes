package com.prueba.ms_gestion_clientes.repository;

import com.prueba.ms_gestion_clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Encuentra la edad promedio de todos los clientes.
     *
     * @return Un Optional que contiene la edad promedio de los clientes.
     */
    @Query("SELECT AVG(c.edad) FROM Cliente c")
    Optional<Double> obtenerEdadPromedio();

    /**
     * Encuentra la desviación estándar de la edad de todos los clientes.
     *
     * @return Un Optional que contiene la desviación estándar de la edad de los clientes.
     */
    @Query("SELECT STDDEV_POP(c.edad) FROM Cliente c")
    Optional<Double> obtenerEdadDesviacionEstandar();
}
