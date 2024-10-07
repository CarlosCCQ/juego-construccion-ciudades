package com.construccion.ciudades.construccion.repository;

import com.construccion.ciudades.construccion.model.ciudad.Ciudad;
import com.construccion.ciudades.construccion.model.recurso.Recurso;
import com.construccion.ciudades.construccion.model.recurso.Tipo_recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {
    List<Recurso> findByTipoRecursosAndCiudad(Tipo_recurso tipoRecursos, Ciudad ciudad);
    List<Recurso> findByTipoRecursos(Tipo_recurso tipoRecursos);
}
