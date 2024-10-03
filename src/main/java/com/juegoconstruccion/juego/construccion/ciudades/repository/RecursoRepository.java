package com.juegoconstruccion.juego.construccion.ciudades.repository;

import com.juegoconstruccion.juego.construccion.ciudades.model.Recurso;
import com.juegoconstruccion.juego.construccion.ciudades.model.Tipo_recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecursoRepository extends JpaRepository {
    List<Recurso> findByTipoRecursosAndCiudad(Tipo_recurso tipoRecursos, Ciudad ciudad);
    List<Recurso> findByTipoRecursos(Tipo_recurso tipoRecursos);
}
