package com.build.api.repository;


import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.recurso.Recurso;
import com.build.api.model.recurso.Tipo_recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {
    List<Recurso> findByTipoRecursosAndCiudad(Tipo_recurso tipoRecursos, Ciudad ciudad);
    List<Recurso> findByTipoRecursos(Tipo_recurso tipoRecursos);

  List<Recurso> findByCiudad(Ciudad ciudad);
}
