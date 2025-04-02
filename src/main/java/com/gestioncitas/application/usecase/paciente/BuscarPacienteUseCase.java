package com.gestioncitas.application.usecase.paciente;

import com.gestioncitas.domain.entity.Paciente;
import com.gestioncitas.domain.repository.PacienteRepository;
import java.util.List;
import java.util.Optional;

public class BuscarPacienteUseCase {
    private final PacienteRepository pacienteRepository;

    public BuscarPacienteUseCase(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Optional<Paciente> porId(Long id) {
        return pacienteRepository.buscarPorId(id);
    }

    public List<Paciente> porNombre(String nombre) {
        return pacienteRepository.buscarPorNombre(nombre);
    }

    public List<Paciente> todos() {
        return pacienteRepository.buscarTodos();
    }
}