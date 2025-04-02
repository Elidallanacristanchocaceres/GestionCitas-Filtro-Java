package com.gestioncitas.application.usecase.paciente;

import com.gestioncitas.domain.entity.Paciente;
import com.gestioncitas.domain.repository.PacienteRepository;

public class RegistrarPacienteUseCase {
    private final PacienteRepository pacienteRepository;

    public RegistrarPacienteUseCase(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente ejecutar(String nombre, String apellido, String telefono, 
                           String email, String direccion) {
        if (pacienteRepository.existePorTelefono(telefono)) {
            throw new IllegalArgumentException("Ya existe un paciente con este teléfono");
        }

        Paciente nuevoPaciente = new Paciente(
        );

        return pacienteRepository.guardar(nuevoPaciente);
    }
}