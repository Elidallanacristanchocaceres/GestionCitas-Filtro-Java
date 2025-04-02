package com.gestioncitas.infrastructure.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnMySql implements ConnectionDb {
    private static final String URL = "jdbc:mysql://localhost:3306/clinica_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "campus2023";

    private Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver JDBC de MySQL", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASS);
        }
        return connection;
    }

    @Override
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}