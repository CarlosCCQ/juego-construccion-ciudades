package com.build.api.repository;


import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.edificio.Edificio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdificioRepository extends JpaRepository<Edificio, Long> {
  List<Edificio> findByCiudad(Ciudad ciudad);
}
