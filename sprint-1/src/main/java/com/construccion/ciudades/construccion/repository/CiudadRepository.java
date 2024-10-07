package com.construccion.ciudades.construccion.repository;

import com.construccion.ciudades.construccion.model.ciudad.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    List<Ciudad> findByNombre(String nombreCiudad);
}
