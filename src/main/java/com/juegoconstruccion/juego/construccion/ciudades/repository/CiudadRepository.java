package com.juegoconstruccion.juego.construccion.ciudades.repository;

import com.juegoconstruccion.juego.construccion.ciudades.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    List<Ciudad> findByNombre(String nombreCiudad);
}