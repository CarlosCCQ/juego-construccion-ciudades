package com.juegoconstruccion.juego.construccion.ciudades.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juegoconstruccion.juego.construccion.ciudades.model.edificio.Edificio;

public interface EdificioRepository extends JpaRepository<Edificio , Long> {
    
}
