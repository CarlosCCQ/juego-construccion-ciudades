package com.build.api.dto;

import com.build.api.model.generador.Tipo_generador_recurso;
import com.build.api.model.recurso.Tipo_recurso;
import lombok.Data;

@Data
public class Genera_recursoDto {
    private Tipo_generador_recurso tipoGeneradorRecurso;
    private Long ciudadId;
    private Tipo_recurso tipoRecursoGenerado;
    private int capacidadGeneracion;

    public Genera_recursoDto() {}

    public Genera_recursoDto(Tipo_generador_recurso tipoGeneradorRecurso, Long ciudadId, Tipo_recurso tipoRecursoGenerado){
        this.tipoGeneradorRecurso = tipoGeneradorRecurso;
        this.ciudadId = ciudadId;
        this.tipoRecursoGenerado = tipoRecursoGenerado;
    }

}
