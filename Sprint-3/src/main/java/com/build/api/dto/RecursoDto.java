package com.build.api.dto;


import com.build.api.model.recurso.Tipo_recurso;
import lombok.Data;

@Data
public class RecursoDto {
    private Long id;
    private Tipo_recurso tipoRecursos;
    private int cantidad;
    private Long ciudadId;

    public RecursoDto(){}

    public RecursoDto(Long id, Tipo_recurso tipoRecursos, int cantidad, Long ciudadId) {
        this.id = id;
        this.tipoRecursos = tipoRecursos;
        this.cantidad = cantidad;
        this.ciudadId = ciudadId;
    }
}
