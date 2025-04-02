package com.gestioncitas.domain.repository;

import com.gestioncitas.domain.entity.Medico;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface MedicoRepository {
    
    Medico guardar(Medico medico);
    Optional<Medico> buscarPorId(Long id);
    List<Medico> buscarPorEspecialidad(Long especialidadId);
    List<Medico> buscarTodos();
    boolean tieneCitasEntre(Long medicoId, LocalDateTime inicio, LocalDateTime fin);
}