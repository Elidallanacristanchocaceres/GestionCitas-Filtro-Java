package com.gestioncitas;

import com.gestioncitas.application.config.AppConfig;
import com.gestioncitas.infrastructure.database.ConnectionDb;
import com.gestioncitas.infrastructure.database.ConnectionFactory;
import com.gestioncitas.infrastructure.database.DatabaseConfig;
import com.gestioncitas.infrastructure.ui.console.MenuPrincipal;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            ConnectionDb connectionDb = ConnectionFactory.createConnection("mysql");
            connection = connectionDb.getConnection();
            
            System.out.println("Conexión a la base de datos establecida correctamente.");
            
            AppConfig config = new AppConfig(connection);
            MenuPrincipal menuPrincipal = config.menuPrincipal();
            
            mostrarBanner();
            menuPrincipal.mostrar();
            
        } catch (Exception e) {
            manejarError(e);
        } finally {
            cerrarRecursos(connection);
            System.out.println("\nAplicación terminada. ¡Hasta pronto!");
        }
    }
    
    private static void mostrarBanner() {
        System.out.println("\n====================================");
        System.out.println("   CLÍNICA MÉDICA - GESTIÓN DE CITAS  ");
        System.out.println("======================================");
        System.out.println("Sistema de gestión integral para citas");
        System.out.println("Versión 2.1.0 | Enero 2024");
        System.out.println("====================================\n");
    }
    
    private static void manejarError(Exception e) {
        System.err.println("\n[ERROR] Ha ocurrido un problema grave:");
        System.err.println("Mensaje: " + e.getMessage());
        System.err.println("\nDetalles técnicos:");
        e.printStackTrace();
        System.err.println("\nPor favor, contacte al soporte técnico.");
    }
    
    private static void cerrarRecursos(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("\nConexión a la base de datos cerrada.");
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}