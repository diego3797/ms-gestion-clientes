package com.prueba.ms_gestion_clientes.repository;

import com.prueba.ms_gestion_clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT AVG(c.edad) FROM Cliente c")
    Optional<Double> findAverageAge();

    @Query("SELECT STDDEV_POP(c.edad) FROM Cliente c")
    Optional<Double> findAgeStandardDeviation();
}
