package com.gestioncitas.infrastructure.database;


public class ConnectionFactory {
    public static ConnectionDb createConnection(String dbType) {
        if ("mysql".equalsIgnoreCase(dbType)) {
            return new ConnMySql();
        }
        throw new IllegalArgumentException("Tipo de base de datos no soportado: " + dbType);
    }
}
    

