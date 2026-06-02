import config.ConexionDB;
import dao.CategoriaDAO;
import dao.PedidoDAO;
import dao.ProductoDAO;
import dao.UsuarioDAO;
import entities.Categoria;
import entities.DetallePedido;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import enums.Rol;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
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

        System.out.println("--- INICIANDO PRUEBA DE CATEGORÍAS ---");

        // 1. Instanciamos el DAO de categorías
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        // 2. Creamos una nueva categoría
        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombre("Juegos de Rol (RPG)");
        nuevaCategoria.setDescripcion("Aventuras inmersivas con progresión de personajes");

        // 3. Probamos el método CREATE
        System.out.println("\nIntentando guardar categoría en MySQL...");
        categoriaDAO.create(nuevaCategoria);

        // 4. Probamos el método READ ALL
        System.out.println("\nRecuperando lista de categorías desde la base de datos:");
        List<Categoria> categoriasGuardadas = categoriaDAO.readAll();

        for (Categoria c : categoriasGuardadas) {
            System.out.println("ID: " + c.getId() + 
                               " | Nombre: " + c.getNombre() + 
                               " | Descripción: " + c.getDescripcion());
        }
        
        System.out.println("\n--- PRUEBA FINALIZADA ---");

        System.out.println("--- INICIANDO PRUEBA DE PRODUCTOS ---");

        // 1. Instanciamos el DAO de productos
        ProductoDAO productoDAO = new ProductoDAO();

        // 2. Preparamos la categoría a la que pertenece el juego
        // Le ponemos ID 1 asumiendo que la categoría que creaste en la prueba anterior (RPG) tiene ese ID en MySQL.
        Categoria categoriaJuego = new Categoria();
        categoriaJuego.setId(1L);

        // 3. Creamos un nuevo producto para el catálogo
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre("Pokémon Legends: Arceus");
        nuevoProducto.setPrecio(55000.00);
        nuevoProducto.setDescripcion("Aventura RPG de acción en la antigua región de Hisui");
        nuevoProducto.setStock(10);
        nuevoProducto.setImagen("pokemon_arceus_portada.png");
        nuevoProducto.setDisponible(true);
        
        // ¡El paso mágico! Conectamos el juego con su categoría
        nuevoProducto.setCategoria(categoriaJuego); 

        // 4. Probamos el método CREATE
        System.out.println("\nIntentando guardar producto en MySQL...");
        productoDAO.create(nuevoProducto);

        // 5. Probamos el método READ ALL
        System.out.println("\nRecuperando lista de productos desde la base de datos:");
        List<Producto> productosGuardados = productoDAO.readAll();

        for (Producto p : productosGuardados) {
            System.out.println("ID: " + p.getId() + 
                               " | Nombre: " + p.getNombre() + 
                               " | Precio: $" + p.getPrecio() + 
                               " | Stock: " + p.getStock() +
                               " | ID Categoría: " + p.getCategoria().getId());
        }
        
        System.out.println("\n--- PRUEBA FINALIZADA ---");

        System.out.println("--- INICIANDO PRUEBA DE PEDIDOS ---");

        PedidoDAO pedidoDAO = new PedidoDAO();

        // 1. Armamos el "cascarón" del comprador (Usamos el ID 1 que creamos ayer)
        Usuario comprador = new Usuario();
        comprador.setId(1L);

        // 2. Armamos el "cascarón" del juego que se va a llevar (El ID 1 que acabás de crear)
        Producto juegoAComprar = new Producto();
        juegoAComprar.setId(1L);

        // 3. Armamos el Detalle del Pedido (Es como la línea del carrito de compras)
        DetallePedido detalle1 = new DetallePedido();
        detalle1.setProducto(juegoAComprar);
        detalle1.setCantidad(2); // Se lleva 2 copias
        detalle1.setSubtotal(110000.00); // Suponiendo que el juego salía 55000

        // Metemos el detalle en una lista (porque un pedido puede tener muchos juegos distintos)
        List<DetallePedido> carrito = new ArrayList<>();
        carrito.add(detalle1);

        // 4. Armamos el Pedido Principal
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setUsuario(comprador);
        nuevoPedido.setFecha(LocalDate.now());
        nuevoPedido.setEstado(Estado.PENDIENTE); // Fijate que esta palabra exista en tu Enum
        nuevoPedido.setPago(FormaPago.EFECTIVO); // Fijate que esta palabra exista en tu Enum
        nuevoPedido.setTotal(110000.00);
        nuevoPedido.setDetalles(carrito); // Le inyectamos la lista de detalles

        // 5. ¡A ejecutar la transacción!
        System.out.println("\nIntentando guardar el pedido y sus detalles en MySQL...");
        pedidoDAO.create(nuevoPedido);

        // 6. Recuperamos para ver si se guardó
        System.out.println("\nRecuperando lista de pedidos:");
        List<Pedido> pedidosGuardados = pedidoDAO.readAll();

        for (Pedido p : pedidosGuardados) {
            System.out.println("Pedido N°: " + p.getId() + 
                               " | ID Comprador: " + p.getUsuario().getId() + 
                               " | Estado: " + p.getEstado() +
                               " | Total: $" + p.getTotal());
        }
        
        System.out.println("\n--- PRUEBA FINALIZADA ---");
    }
}
