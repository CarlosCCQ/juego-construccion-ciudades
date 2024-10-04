package com.juegoconstruccion.juego.construccion.ciudades.service.edificio;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juegoconstruccion.juego.construccion.ciudades.dto.EdificioDto;
import com.juegoconstruccion.juego.construccion.ciudades.model.Ciudad;
import com.juegoconstruccion.juego.construccion.ciudades.model.Recurso;
import com.juegoconstruccion.juego.construccion.ciudades.model.Tipo_recurso;
import com.juegoconstruccion.juego.construccion.ciudades.model.edificio.Edificio;
import com.juegoconstruccion.juego.construccion.ciudades.repository.CiudadRepository;
import com.juegoconstruccion.juego.construccion.ciudades.repository.EdificioRepository;
import com.juegoconstruccion.juego.construccion.ciudades.repository.RecursoRepository;
import com.juegoconstruccion.juego.construccion.ciudades.service.RecursoService;
import com.juegoconstruccion.juego.construccion.ciudades.model.edificio.Tipo_edificio;

/**
 * Service class for handling the business logic related to buildings (Edificio).
 * It provides methods to create, update, delete, and retrieve buildings.
 */
@Service
public class EdificioService implements IEdificioService {

    @Autowired
    private EdificioRepository edificioRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    private final RecursoService recursoService;

    /**
     * Constructor for EdificioService.
     * @param recursoService Dependency on RecursoService to manage resources.
     */
    public EdificioService(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    // Static map defining the cost of constructing each type of building
    private static final Map<Tipo_edificio, Map<Tipo_recurso, Integer>> COSTO_EDIFICIOS = Map.of(
            Tipo_edificio.CLASE_ALTA, Map.of(Tipo_recurso.PIEDRA, 10, Tipo_recurso.ORO, 5, Tipo_recurso.AGUA, 3),
            Tipo_edificio.CLASE_MEDIA, Map.of(Tipo_recurso.PIEDRA, 7, Tipo_recurso.ORO, 4, Tipo_recurso.AGUA, 2),
            Tipo_edificio.CLASE_BAJA, Map.of(Tipo_recurso.PIEDRA, 5, Tipo_recurso.ORO, 2, Tipo_recurso.AGUA, 1)
    );

    /**
     * Retrieves all buildings from the repository and converts them to DTOs.
     * @return List of EdificioDto representing all buildings.
     */
    @Override
    public List<EdificioDto> obtenerTodosLosEdificios() {
        return edificioRepository.findAll().stream()
                .map(this::convertirAEdificioDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a building by its ID and converts it to DTO.
     * @param id The ID of the building.
     * @return The EdificioDto representing the building.
     */
    @Override
    public EdificioDto obtenerEdificioPorId(Long id) {
        Edificio edificio = edificioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edificio no encontrado"));
        return convertirAEdificioDto(edificio);
    }

    /**
     * Creates a new building if the city's resources are sufficient.
     * @param edificioDto The DTO containing the building information.
     * @return The newly created EdificioDto.
     */
    @Override
    public EdificioDto crearEdificio(EdificioDto edificioDto) {
        Ciudad ciudad = ciudadRepository.findById(edificioDto.getCiudadId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        // Get the resource cost for the type of building
        Map<Tipo_recurso, Integer> costo = COSTO_EDIFICIOS.get(edificioDto.getTipoEdificio());

        // Check if the city has enough resources to build the building
        if (!verificarRecursosParaConstruir(ciudad.getId(), costo)) {
            throw new RuntimeException("Recursos insuficientes para construir el edificio");
        }

        // Create the building entity
        Edificio edificio = new Edificio();
        edificio.setNombre(edificioDto.getNombre());
        edificio.setTipoEdificio(edificioDto.getTipoEdificio());
        edificio.setCosto(costo);
        edificio.setConstruccionCompleta(false);
        edificio.setCiudad(ciudad);

        // Deduct the resources from the city
        restarRecursosDeLaCiudad(ciudad, costo);

        return convertirAEdificioDto(edificioRepository.save(edificio));
    }

    /**
     * Updates an existing building by its ID.
     * @param id The ID of the building to update.
     * @param edificioDto The DTO containing the updated building information.
     * @return The updated EdificioDto.
     */
    @Override
    public EdificioDto actualizarEdificio(Long id, EdificioDto edificioDto) {
        Edificio edificio = edificioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edificio no encontrado"));

        edificio.setNombre(edificioDto.getNombre());
        edificio.setTipoEdificio(edificioDto.getTipoEdificio());
        edificio.setConstruccionCompleta(edificioDto.isConstruccionCompleta());

        return convertirAEdificioDto(edificioRepository.save(edificio));
    }

    /**
     * Deletes a building by its ID.
     * @param id The ID of the building to delete.
     */
    @Override
    public void eliminarEdificio(Long id) {
        edificioRepository.deleteById(id);
    }

    /**
     * Verifies if a city has enough resources to build a building.
     * @param ciudadId The ID of the city.
     * @param costo The resource cost for the building.
     * @return true if the city has enough resources, false otherwise.
     */
    @Override
    public boolean verificarRecursosParaConstruir(Long ciudadId, Map<Tipo_recurso, Integer> costo) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        // Check if the city has the required amount of each resource
        for (Map.Entry<Tipo_recurso, Integer> entry : costo.entrySet()) {
            Tipo_recurso tipoRecursos = entry.getKey();
            Integer cantidadNecesaria = entry.getValue();

            Recurso recurso = recursoRepository.findByTipoRecursosAndCiudad(tipoRecursos, ciudad)
                    .stream().findFirst().orElseThrow(() -> new RuntimeException("Recurso no encontrado para la ciudad"));

            if (recurso.getCantidad() < cantidadNecesaria) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deducts resources from a city based on the cost of constructing a building.
     * @param ciudad The city from which resources will be deducted.
     * @param costo The resource cost for the building.
     */
    private void restarRecursosDeLaCiudad(Ciudad ciudad, Map<Tipo_recurso, Integer> costo) {
        for (Map.Entry<Tipo_recurso, Integer> entry : costo.entrySet()) {
            Tipo_recurso tipoRecursos = entry.getKey();
            Integer cantidad = entry.getValue();

            // Find the resource in the city's resources and deduct the required amount
            Recurso recurso = ciudad.getRecursos().stream()
                    .filter(r -> r.getTipoRecursos() == tipoRecursos)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Recurso no encontrado en la ciudad"));

            recurso.setCantidad(recurso.getCantidad() - cantidad);
            recursoRepository.save(recurso);
        }
    }

    /**
     * Converts an Edificio entity to an EdificioDto.
     * @param edificio The building entity to convert.
     * @return The EdificioDto representing the building.
     */
    private EdificioDto convertirAEdificioDto(Edificio edificio) {
        return new EdificioDto(
                edificio.getId(),
                edificio.getNombre(),
                edificio.getTipoEdificio(),
                edificio.getCosto(),
                edificio.isConstruccionCompleta(),
                edificio.getCiudad().getId()
        );
    }
}
