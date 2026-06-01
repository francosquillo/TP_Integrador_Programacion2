import config.ConexionDB;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("Intentando conectar con la base de datos...");
        
        Connection miConexion = ConexionDB.getConexion();
        
        if(miConexion != null){
            System.out.println("¡Listo! El puente está construido. Podemos empezar a mandar SQL.");
        } else {
            System.out.println("Algo falló. Revisá los errores de arriba.");
        }
    }
}
