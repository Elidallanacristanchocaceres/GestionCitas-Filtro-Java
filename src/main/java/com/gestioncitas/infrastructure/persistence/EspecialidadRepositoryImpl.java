package com.gestioncitas.infrastructure.persistence;

import com.gestioncitas.domain.entity.Especialidad;
import com.gestioncitas.domain.repository.EspecialidadRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EspecialidadRepositoryImpl implements EspecialidadRepository {
    private final Connection connection;

    public EspecialidadRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Especialidad guardar(Especialidad especialidad) {
        String sql = especialidad.getId() == null ?
            "INSERT INTO especialidades (nombre, descripcion) VALUES (?, ?)" :
            "UPDATE especialidades SET nombre = ?, descripcion = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, especialidad.getNombre());
            stmt.setString(2, especialidad.getDescripcion());

            if (especialidad.getId() != null) {
                stmt.setLong(3, especialidad.getId());
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo guardar la especialidad");
            }

            if (especialidad.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        especialidad.setId(rs.getLong(1));
                    }
                }
            }
            return especialidad;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar especialidad", e);
        }
    }

    @Override
    public Optional<Especialidad> buscarPorId(Long id) {
        String sql = "SELECT * FROM especialidades WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new Especialidad(
                    rs.getLong("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar especialidad por ID", e);
        }
    }

    @Override
    public List<Especialidad> buscarTodas() {
        String sql = "SELECT * FROM especialidades";
        List<Especialidad> especialidades = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                especialidades.add(new Especialidad(
                    rs.getLong("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                ));
            }
            return especialidades;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todas las especialidades", e);
        }
    }

    @Override
    public List<Especialidad> buscarPorNombre(String nombre) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorNombre'");
    }

    @Override
    public boolean estaEnUso(Long especialidadId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estaEnUso'");
    }
}