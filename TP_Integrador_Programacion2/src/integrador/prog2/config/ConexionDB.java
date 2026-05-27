package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/pedidos_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root"; 
    private static final String PASS = "";

    private static Connection conexion = null;

    public static Connection getConexion() {
        if (conexion == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("LOG: ¡Conexión a la base de datos exitosa!");
                
            } catch (ClassNotFoundException e) {
                System.out.println("ERROR: No se encontró el Driver. ¿Agregaste el .jar al proyecto?");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("ERROR: No se pudo conectar a MySQL. Revisá usuario, contraseña y que el servicio de MySQL esté encendido.");
                e.printStackTrace();
            }
        }
        return conexion;
    }
}
