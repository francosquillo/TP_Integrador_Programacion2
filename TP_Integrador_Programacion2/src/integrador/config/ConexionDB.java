package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    
    // Configuración de acceso: ajustá usuario y pass según tu instalación de MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/pedidos_db";
    private static final String USER = "root"; 
    private static final String PASS = ""; 
    
    private static Connection conexion = null;

public static Connection getConexion() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        // ------------------------

        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión exitosa a la base de datos.");
        }
    } catch (ClassNotFoundException e) {
        System.err.println("Error: No se encontró el driver (el archivo .jar no está en el classpath).");
    } catch (SQLException e) {
        System.err.println("Error de conexión: " + e.getMessage());
    }
    return conexion;
}
}
