package com.gestioncitas.domain.repository;

import com.gestioncitas.domain.entity.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository {
   
    Paciente guardar(Paciente paciente);
    Optional<Paciente> buscarPorId(Long id);
    List<Paciente> buscarPorNombre(String nombre);
    List<Paciente> buscarTodos();
    boolean existePorTelefono(String telefono);
    boolean eliminar(Long id);
}