package com.gestioncitas.domain.repository;

import com.gestioncitas.domain.entity.Especialidad;
import java.util.List;
import java.util.Optional;

public interface EspecialidadRepository {
    
    Especialidad guardar(Especialidad especialidad);
    Optional<Especialidad> buscarPorId(Long id);
    List<Especialidad> buscarPorNombre(String nombre);
    List<Especialidad> buscarTodas();
    boolean estaEnUso(Long especialidadId);
}