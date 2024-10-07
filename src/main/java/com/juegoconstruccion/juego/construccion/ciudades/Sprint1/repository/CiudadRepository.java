package com.juegoconstruccion.juego.construccion.ciudades.Sprint1.repository;

import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    List<Ciudad> findByNombre(String nombreCiudad);
}