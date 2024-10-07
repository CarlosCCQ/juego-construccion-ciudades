package com.construccion.ciudades.construccion.repository;

import com.construccion.ciudades.construccion.model.edificio.Edificio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EdificioRepository extends JpaRepository<Edificio, Long> {
}
