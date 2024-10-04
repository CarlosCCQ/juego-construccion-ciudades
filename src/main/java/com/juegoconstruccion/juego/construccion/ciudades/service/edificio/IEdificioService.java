package com.juegoconstruccion.juego.construccion.ciudades.service.edificio;

import java.util.List;
import java.util.Map;

import com.juegoconstruccion.juego.construccion.ciudades.dto.EdificioDto;
import com.juegoconstruccion.juego.construccion.ciudades.model.Tipo_recurso;

/**
 * Interfaz de servicio para la gestión de Edificios.
 * 
 * Esta interfaz define los métodos necesarios para operar sobre los datos de Edificios, 
 * como obtener, crear, actualizar y eliminar Edificios, además de verificar recursos disponibles 
 * para la construcción.
 */
public interface IEdificioService {

    /**
     * Obtiene una lista de todos los Edificios disponibles.
     * 
     * @return una lista de objetos EdificioDto que representan todos los Edificios.
     */
    List<EdificioDto> obtenerTodosLosEdificios();

    /**
     * Obtiene un Edificio específico basado en su ID.
     * 
     * @param id el identificador del Edificio.
     * @return el objeto EdificioDto correspondiente al ID proporcionado.
     */
    EdificioDto obtenerEdificioPorId(Long id);

    /**
     * Crea un nuevo Edificio en el sistema.
     * 
     * @param edificioDto los datos del Edificio a crear.
     * @return el objeto EdificioDto recién creado.
     */
    EdificioDto crearEdificio(EdificioDto edificioDto);

    /**
     * Actualiza un Edificio existente basado en su ID.
     * 
     * @param id el identificador del Edificio a actualizar.
     * @param edificioDto los nuevos datos del Edificio.
     * @return el objeto EdificioDto actualizado.
     */
    EdificioDto actualizarEdificio(Long id, EdificioDto edificioDto);

    /**
     * Elimina un Edificio del sistema basado en su ID.
     * 
     * @param id el identificador del Edificio a eliminar.
     */
    void eliminarEdificio(Long id);

    /**
     * Verifica si una Ciudad tiene los recursos necesarios para construir un Edificio.
     * 
     * @param ciudadId el identificador de la Ciudad.
     * @param costo un mapa que contiene el tipo de recurso y la cantidad necesaria.
     * @return true si la Ciudad tiene suficientes recursos, false de lo contrario.
     */
    boolean verificarRecursosParaConstruir(Long ciudadId, Map<Tipo_recurso, Integer> costo);
}
