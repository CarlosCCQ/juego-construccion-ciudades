package com.juegoconstruccion.juego.construccion.ciudades.Sprint1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.model.edificio.Edificio;

public interface EdificioRepository extends JpaRepository<Edificio , Long> {
    
}
