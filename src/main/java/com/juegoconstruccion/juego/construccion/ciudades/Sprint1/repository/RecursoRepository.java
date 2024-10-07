package com.juegoconstruccion.juego.construccion.ciudades.Sprint1.repository;

import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.model.Ciudad;
import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.model.Recurso;
import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.model.Tipo_recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {
    List<Recurso> findByTipoRecursosAndCiudad(Tipo_recurso tipoRecursos, Ciudad ciudad);
    List<Recurso> findByTipoRecursos(Tipo_recurso tipoRecursos);
}
