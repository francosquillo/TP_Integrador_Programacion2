import config.ConexionDB;
import dao.UsuarioDAO;
import entities.Usuario;
import enums.Rol;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("Intentando conectar con la base de datos...");
        
        Connection miConexion = ConexionDB.getConexion();
        
        if(miConexion != null){
            System.out.println("¡Listo! El puente está construido. Podemos empezar a mandar SQL.");
        } else {
            System.out.println("Algo falló. Revisá los errores de arriba.");
        }

        System.out.println("--- INICIANDO PRUEBA DE CONEXIÓN ---");

        // 1. Instanciamos el DAO que vamos a probar
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // 2. Creamos un nuevo objeto Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("Augusto");
        nuevoUsuario.setApellido("Ingrassia");
        nuevoUsuario.setMail("admin@aethergames.com");
        nuevoUsuario.setCelular("2615555555");
        nuevoUsuario.setContrasenia("123456");
        nuevoUsuario.setRol(Rol.ADMIN); 

        // 3. Probamos el método CREATE
        System.out.println("\nIntentando guardar usuario en MySQL...");
        usuarioDAO.create(nuevoUsuario);

        // 4. Probamos el método READ ALL
        System.out.println("\nRecuperando lista de usuarios desde la base de datos:");
        List<Usuario> usuariosGuardados = usuarioDAO.readAll();

        for (Usuario u : usuariosGuardados) {
            System.out.println("ID: " + u.getId() + 
                               " | Nombre: " + u.getNombre() + " " + u.getApellido() + 
                               " | Mail: " + u.getMail() + 
                               " | Rol: " + u.getRol());
        }
        
        System.out.println("\n--- PRUEBA FINALIZADA ---");
    }
}
