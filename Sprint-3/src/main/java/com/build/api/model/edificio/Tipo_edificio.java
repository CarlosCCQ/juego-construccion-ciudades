package com.build.api.model.edificio;

public enum Tipo_edificio {
    CLASE_ALTA, CLASE_MEDIA, CLASE_BAJA;
    public Tipo_edificio getNext() {
      return switch (this) {
        case CLASE_BAJA -> CLASE_MEDIA;
        case CLASE_MEDIA -> CLASE_ALTA;
        default -> throw new IllegalStateException("No hay mejoras disponibles para el tipo " + this);
      };
    }
    public static Tipo_edificio fromString(String tipo) {
        try {
            return Tipo_edificio.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("El tipo '" + tipo + "' no es un valor válido para Tipo_edificio.");
            return null;
        }
    }
}
