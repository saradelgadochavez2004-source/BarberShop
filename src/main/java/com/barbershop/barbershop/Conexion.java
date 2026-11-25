package com.barbershop.barbershop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://mysql-5a6a503-sara-66a5.k.aivencloud.com:24554/defaultdb?sslMode=REQUIRED&characterEncoding=UTF-8";
    private static final String USUARIO = "avnadmin";
    private static final String PASSWORD = "...";

    /**
     * Lanza SQLException si no hay conexión (así los DAO nunca reciben null).
     */
    public static Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el controlador MySQL.", e);
        }
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
