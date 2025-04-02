package com.gestioncitas.infrastructure.persistence;

import com.gestioncitas.domain.entity.Historial;
import com.gestioncitas.domain.entity.Paciente;
import com.gestioncitas.domain.repository.HistorialRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistorialRepositoryImpl implements HistorialRepository {
    private final Connection connection;

    public HistorialRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Historial guardar(Historial historial) {
        String sql = "INSERT INTO historiales (paciente_id) VALUES (?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, historial.getPaciente());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo crear el historial");
            }
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    historial.setId(rs.getLong(1));
                }
            }
            return historial;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar historial", e);
        }
    }

    @Override
    public Optional<Historial> buscarPorPaciente(Long pacienteId) {
        String sql = "SELECT * FROM historiales WHERE paciente_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, pacienteId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Historial historial = new Historial(
                    rs.getLong("id"),
                    obtenerPaciente(pacienteId)
                );
                
                historial.getRegistros().addAll(obtenerRegistros(historial.getId()));
                return Optional.of(historial);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar historial por paciente", e);
        }
    }

    private Paciente obtenerPaciente(Long pacienteId) {
        throw new UnsupportedOperationException("Unimplemented method 'obtenerPaciente'");
    }

    private List<Historial.RegistroHistorial> obtenerRegistros(Long historialId) {
        String sql = "SELECT * FROM registros_historial WHERE historial_id = ?";
        List<Historial.RegistroHistorial> registros = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, historialId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                registros.add(new Historial.RegistroHistorial(
                    rs.getTimestamp("fecha").toLocalDateTime(),
                    rs.getString("observaciones"),
                    rs.getString("tratamiento")
                ));
            }
            return registros;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener registros del historial", e);
        }
    }

    @Override
    public boolean agregarRegistro(Long pacienteId, String observaciones, String tratamiento) {
        throw new UnsupportedOperationException("Unimplemented method 'agregarRegistro'");
    }

    @Override
    public String generarReportePdf(Long pacienteId) {
        throw new UnsupportedOperationException("Unimplemented method 'generarReportePdf'");
    }
}