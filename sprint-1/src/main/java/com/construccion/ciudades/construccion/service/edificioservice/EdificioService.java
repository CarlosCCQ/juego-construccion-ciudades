package com.construccion.ciudades.construccion.service.edificioservice;

import com.construccion.ciudades.construccion.dto.EdificioDto;
import com.construccion.ciudades.construccion.model.ciudad.Ciudad;
import com.construccion.ciudades.construccion.model.edificio.Edificio;
import com.construccion.ciudades.construccion.model.edificio.Tipo_edificio;
import com.construccion.ciudades.construccion.model.recurso.Recurso;
import com.construccion.ciudades.construccion.model.recurso.Tipo_recurso;
import com.construccion.ciudades.construccion.repository.CiudadRepository;
import com.construccion.ciudades.construccion.repository.EdificioRepository;
import com.construccion.ciudades.construccion.repository.RecursoRepository;
import com.construccion.ciudades.construccion.service.recursoservice.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EdificioService implements IEdificioService{
    @Autowired
    private EdificioRepository edificioRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    private final RecursoService recursoService;

    public EdificioService(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    private static final Map<Tipo_edificio, Map<Tipo_recurso, Integer>> COSTO_EDIFICIOS = Map.of(
            Tipo_edificio.CLASE_ALTA, Map.of(Tipo_recurso.PIEDRA, 10, Tipo_recurso.ORO, 5, Tipo_recurso.AGUA, 3),
            Tipo_edificio.CLASE_MEDIA, Map.of(Tipo_recurso.PIEDRA, 7, Tipo_recurso.ORO, 4, Tipo_recurso.AGUA, 2),
            Tipo_edificio.CLASE_BAJA, Map.of(Tipo_recurso.PIEDRA, 5, Tipo_recurso.ORO, 2, Tipo_recurso.AGUA, 1)
    );

    @Override
    public List<EdificioDto> obtenerTodosLosEdificios() {
        return edificioRepository.findAll().stream()
                .map(this::convertirAEdificioDto)
                .collect(Collectors.toList());
    }

    @Override
    public EdificioDto obtenerEdificioPorId(Long id) {
        Edificio edificio = edificioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edificio no encontrado"));
        return convertirAEdificioDto(edificio);
    }

    @Override
    public EdificioDto crearEdificio(EdificioDto edificioDto) {
        Ciudad ciudad = ciudadRepository.findById(edificioDto.getCiudadId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        Map<Tipo_recurso, Integer> costo = COSTO_EDIFICIOS.get(edificioDto.getTipoEdificio());

        if (!verificarRecursosParaConstruir(ciudad.getId(), costo)) {
            throw new RuntimeException("Recursos insuficientes para construir el edificio");
        }

        Edificio edificio = new Edificio();
        edificio.setNombre(edificioDto.getNombre());
        edificio.setTipoEdificio(edificioDto.getTipoEdificio());
        edificio.setCosto(costo);
        edificio.setConstruccionCompleta(false);
        edificio.setCiudad(ciudad);

        restarRecursosDeLaCiudad(ciudad, costo);

        return convertirAEdificioDto(edificioRepository.save(edificio));
    }

    @Override
    public EdificioDto actualizarEdificio(Long id, EdificioDto edificioDto) {
        Edificio edificio = edificioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edificio no encontrado"));

        edificio.setNombre(edificioDto.getNombre());
        edificio.setTipoEdificio(edificioDto.getTipoEdificio());
        edificio.setConstruccionCompleta(edificioDto.isConstruccionCompleta());

        return convertirAEdificioDto(edificioRepository.save(edificio));
    }

    @Override
    public void eliminarEdificio(Long id) {
        edificioRepository.deleteById(id);
    }

    @Override
    public boolean verificarRecursosParaConstruir(Long ciudadId, Map<Tipo_recurso, Integer> costo) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        for (Map.Entry<Tipo_recurso, Integer> entry : costo.entrySet()) {
            Tipo_recurso tipoRecursos = entry.getKey();
            Integer cantidadNecesaria = entry.getValue();

            Recurso recurso = recursoRepository.findByTipoRecursosAndCiudad(tipoRecursos, ciudad)
                    .stream().findFirst().orElseThrow(()-> new RuntimeException(("Recurso no encontrado para la ciudad")));

            if (recurso.getCantidad() < cantidadNecesaria) {
                return false;
            }
        }
        return true;
    }

    private void restarRecursosDeLaCiudad(Ciudad ciudad, Map<Tipo_recurso, Integer> costo) {
        for (Map.Entry<Tipo_recurso, Integer> entry : costo.entrySet()) {
            Tipo_recurso tipoRecursos = entry.getKey();
            Integer cantidad = entry.getValue();

            Recurso recurso = ciudad.getRecursos().stream()
                    .filter(r -> r.getTipoRecursos() == tipoRecursos)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Recurso no encontrado en la ciudad"));

            recurso.setCantidad(recurso.getCantidad() - cantidad);
            recursoRepository.save(recurso);
        }
    }

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
