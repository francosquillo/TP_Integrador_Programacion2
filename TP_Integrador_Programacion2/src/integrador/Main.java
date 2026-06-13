import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import entities.Categoria;
import entities.DetallePedido;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import service.CategoriaService;
import service.PedidoService;
import service.ProductoService;
import service.UsuarioService;

public class Main {

    // # Instanciamos el servicio de categorías
    private static CategoriaService categoriaService = new CategoriaService();
    private static ProductoService productoService = new ProductoService();
    private static UsuarioService usuarioService = new UsuarioService();
    private static PedidoService pedidoService = new PedidoService();

    public static void main(String[] args) {
        // # Inicializamos el scanner
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        // # BUCLE DEL MENÚ PRINCIPAL
        do {
            System.out.println("\n▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
            System.out.println("█                                         █");
            System.out.println("█   S I S T E M A   D E   P E D I D O S   █");
            System.out.println("█            ( F O O D  S T O R E )       █");
            System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
            System.out.println("");
            System.out.println("         [ 1 ] Gestión de Categorías");
            System.out.println("         [ 2 ] Gestión de Productos");
            System.out.println("         [ 3 ] Gestión de Usuarios");
            System.out.println("         [ 4 ] Gestión de Pedidos");
            System.out.println("         ---------------------------");
            System.out.println("         [ 0 ] Salir del Sistema");
            System.out.println("");
            System.out.print("   ▶ Seleccione una opción: ");

            // # Validación para evitar que el programa se rompa si ingresan letras
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        menuCategorias(scanner);
                        break;
                    case 2:
                        menuProductos(scanner);
                        break;
                    case 3:
                        menuUsuarios(scanner);
                        break;
                    case 4:
                        menuPedidos(scanner);
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema... ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Error: Opción incorrecta. Elija un número del 0 al 4.");
                        presionarEnterParaContinuar(scanner);
                }
            } else {
                System.out.println("Error: Por favor, ingrese un número válido.");
                scanner.next();
                presionarEnterParaContinuar(scanner);
            }
        } while (opcion != 0);

        // # Cerramos el scanner al salir del sistema
        scanner.close();
    }

    // ==========================================================
    // # SUBMENÚ: CATEGORÍAS
    // ==========================================================
    public static void menuCategorias(Scanner scanner) {
        int opcionCat = -1;
        do {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║        MENÚ DE CATEGORÍAS        ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  [1] Listar Categorías           ║");
            System.out.println("║  [2] Crear Categoría             ║");
            System.out.println("║  [3] Editar Categoría            ║");
            System.out.println("║  [4] Eliminar Categoría          ║");
            System.out.println("║  [0] Volver al Menú Principal    ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcionCat = scanner.nextInt();
                scanner.nextLine();

                switch (opcionCat) {
                    case 1:
                        System.out.println("\n=======================================================================");
                        System.out.println("                          LISTA DE CATEGORÍAS");
                        System.out.println("=======================================================================");
                        List<Categoria> lista = categoriaService.readAll();
                        if (lista == null || lista.isEmpty()) {
                            System.out.println("No hay categorías cargadas.");
                        } else {
                            System.out.printf("%-6s | %-20s | %-35s\n", "ID", "NOMBRE", "DESCRIPCIÓN");
                            System.out.println("-----------------------------------------------------------------------");
                            for (Categoria cat : lista) {
                                System.out.printf("%-6d | %-20s | %-35s\n", cat.getId(), cat.getNombre(), cat.getDescripcion());
                            }
                        }
                        System.out.println("=======================================================================\n");
                        break;

                    case 2:
                        System.out.println("\n--- CREAR NUEVA CATEGORÍA ---");
                        String nombre = "";
                        
                        while (nombre.trim().isEmpty() || nombre.matches(".*\\d.*")) {
                            System.out.print("Ingrese el nombre de la categoría: ");
                            nombre = scanner.nextLine();
                            if (nombre.trim().isEmpty() || nombre.matches(".*\\d.*")) {
                                System.out.println("Error: El nombre no puede contener números ni estar vacío.");
                            }
                        }

                        String descripcion = "";
                        while (descripcion.trim().isEmpty()) {
                            System.out.print("Ingrese la descripción de la categoría: ");
                            descripcion = scanner.nextLine();
                            if (descripcion.trim().isEmpty()) {
                                System.out.println("Error: La descripción no puede estar vacía.");
                            }
                        }

                        Categoria nuevaCategoria = new Categoria();
                        nuevaCategoria.setNombre(nombre.trim());
                        nuevaCategoria.setDescripcion(descripcion.trim());

                        categoriaService.create(nuevaCategoria);
                        System.out.println("Categoría enviada para su creación.");
                        break;

                    case 3:
                        System.out.println("\n--- EDITAR CATEGORÍA ---");
                        long idEditar = -1;
                        while (idEditar < 0) {
                            System.out.print("Ingrese el ID de la categoría a editar: ");
                            if (scanner.hasNextLong()) {
                                idEditar = scanner.nextLong();
                                if (idEditar < 0) System.out.println("Error: El ID no puede ser negativo.");
                            } else {
                                System.out.println("Error: Ingrese un ID numérico válido.");
                                scanner.next();
                            }
                        }
                        scanner.nextLine(); 

                        Categoria catEditar = categoriaService.readByID(idEditar);

                        if (catEditar == null) {
                            System.out.println("Error: No se encontró una categoría con el ID " + idEditar);
                        } else {
                            System.out.println("Editando la categoría: " + catEditar.getNombre());

                            String nuevoNombre = "";
                            while (nuevoNombre.trim().isEmpty() || nuevoNombre.matches(".*\\d.*")) {
                                System.out.print("Ingrese el NUEVO nombre: ");
                                nuevoNombre = scanner.nextLine();
                                if (nuevoNombre.trim().isEmpty() || nuevoNombre.matches(".*\\d.*")) {
                                    System.out.println("Error: El nombre no puede contener números ni estar vacío.");
                                }
                            }

                            String nuevaDesc = "";
                            while (nuevaDesc.trim().isEmpty()) {
                                System.out.print("Ingrese la NUEVA descripción: ");
                                nuevaDesc = scanner.nextLine();
                                if (nuevaDesc.trim().isEmpty()) {
                                    System.out.println("Error: La descripción no puede estar vacía.");
                                }
                            }

                            catEditar.setNombre(nuevoNombre.trim());
                            catEditar.setDescripcion(nuevaDesc.trim());

                            categoriaService.update(catEditar);
                            System.out.println("Categoría editada exitosamente.");
                        }
                        break;

                    case 4:
                        System.out.println("\n--- ELIMINAR CATEGORÍA ---");
                        long idEliminar = -1;
                        while (idEliminar < 0) {
                            System.out.print("Ingrese el ID de la categoría a eliminar: ");
                            if (scanner.hasNextLong()) {
                                idEliminar = scanner.nextLong();
                                if (idEliminar < 0) System.out.println("Error: El ID no puede ser negativo.");
                            } else {
                                System.out.println("Error: Ingrese un ID numérico válido.");
                                scanner.next();
                            }
                        }
                        scanner.nextLine();

                        Categoria catEliminar = categoriaService.readByID(idEliminar);

                        if (catEliminar == null) {
                            System.out.println("Error: No se encontró la categoría con el ID " + idEliminar);
                        } else {
                            System.out.println("Atención: Está por eliminar la categoría '" + catEliminar.getNombre() + "'");
                            System.out.print("¿Está seguro? (1 para SÍ, 0 para NO): ");

                            if (scanner.hasNextInt()) {
                                int confirmacion = scanner.nextInt();
                                scanner.nextLine();

                                if (confirmacion == 1) {
                                    categoriaService.delete(idEliminar);
                                    System.out.println("Categoría eliminada del sistema.");
                                } else {
                                    System.out.println("Operación cancelada. No se borró nada.");
                                }
                            } else {
                                System.out.println("Entrada no válida. Operación cancelada por seguridad.");
                                scanner.next();
                            }
                        }
                        break;

                    case 0:
                        System.out.println("Volviendo al menú principal...");
                        break;

                    default:
                        System.out.println("Error: Opción incorrecta.");
                }

                if (opcionCat != 0) {
                    presionarEnterParaContinuar(scanner);
                }
            } else {
                System.out.println("Error: Ingrese un número.");
                scanner.next();
                presionarEnterParaContinuar(scanner);
            }
        } while (opcionCat != 0);
    }

    // ==========================================================
    // # SUBMENÚ: PRODUCTOS
    // ==========================================================

    public static void menuProductos(Scanner scanner) {
        int opcionProd = -1;

        do {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║         MENÚ DE PRODUCTOS        ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  [1] Listar Productos            ║");
            System.out.println("║  [2] Crear Producto              ║");
            System.out.println("║  [3] Editar Producto             ║");
            System.out.println("║  [4] Eliminar Producto           ║");
            System.out.println("║  [0] Volver al Menú Principal    ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");
            if (scanner.hasNextInt()) {
                opcionProd = scanner.nextInt();
                scanner.nextLine();

                switch (opcionProd) {

                    case 1:
                        System.out.println("\n=======================================================================");
                        System.out.println("                           LISTA DE PRODUCTOS");
                        System.out.println("=======================================================================");
                        List<Producto> productos = productoService.readAll();
                        if (productos == null || productos.isEmpty()) {
                            System.out.println("No hay productos cargados.");
                        } else {
                            System.out.printf("%-6s | %-30s | %-12s | %-8s\n", "ID", "NOMBRE", "PRECIO", "STOCK");
                            System.out.println("-----------------------------------------------------------------------");
                            for (Producto p : productos) {
                                System.out.printf("%-6d | %-30s | $%-11.2f | %-8d\n", p.getId(), p.getNombre(), p.getPrecio(), p.getStock());
                            }
                        }
                        System.out.println("=======================================================================\n");
                        break;

                    case 2:
                        System.out.println("\n--- CREAR PRODUCTO ---");

                        String nombre = "";
                        while (nombre.trim().isEmpty() || nombre.matches(".*\\d.*")) {
                            System.out.print("Nombre: ");
                            nombre = scanner.nextLine();
                            if (nombre.trim().isEmpty() || nombre.matches(".*\\d.*")) {
                                System.out.println("Error: El nombre no puede contener números ni estar vacío.");
                            }
                        }

                        String descripcion = "";
                        while (descripcion.trim().isEmpty()) {
                            System.out.print("Descripción: ");
                            descripcion = scanner.nextLine();
                            if (descripcion.trim().isEmpty()) {
                                System.out.println("Error: La descripción no puede estar vacía.");
                            }
                        }

                        double precio = -1;
                        while (precio < 0) {
                            System.out.print("Precio: ");
                            if (scanner.hasNextDouble()) {
                                precio = scanner.nextDouble();
                                if (precio < 0) System.out.println("Error: El precio no puede ser negativo.");
                            } else {
                                System.out.println("Error: Tiene que ingresar un número válido (ej: 1500 o 1500.50).");
                                scanner.next();
                            }
                        }

                        int stock = -1;
                        while (stock < 0) {
                            System.out.print("Stock: ");
                            if (scanner.hasNextInt()) {
                                stock = scanner.nextInt();
                                if (stock < 0) System.out.println("Error: El stock no puede ser negativo.");
                            } else {
                                System.out.println("Error: Tiene que ingresar un número válido.");
                                scanner.next();
                            }
                        }
                        scanner.nextLine(); // Limpiar buffer

                        long idCategoria = -1;
                        while (idCategoria < 0) {
                            System.out.print("ID de categoría: ");
                            if (scanner.hasNextLong()) {
                                idCategoria = scanner.nextLong();
                                if (idCategoria < 0) System.out.println("Error: El ID no puede ser negativo.");
                            } else {
                                System.out.println("Error: Ingrese un ID numérico válido.");
                                scanner.next();
                            }
                        }
                        scanner.nextLine();

                        Categoria categoria = categoriaService.readByID(idCategoria);

                        if (categoria == null) {
                            System.out.println("Error: Categoría no encontrada.");
                            break;
                        }

                        Producto nuevoProducto = new Producto();
                        nuevoProducto.setNombre(nombre.trim());
                        nuevoProducto.setDescripcion(descripcion.trim());
                        nuevoProducto.setPrecio(precio);
                        nuevoProducto.setStock(stock);
                        nuevoProducto.setCategoria(categoria);

                        productoService.create(nuevoProducto);
                        System.out.println("Producto creado correctamente.");
                        break;

                    case 3:
                        System.out.println("\n--- EDITAR PRODUCTO ---");
                        long idProdEditar = -1;
                        while (idProdEditar < 0) {
                            System.out.print("Ingrese ID del producto a editar: ");
                            if (scanner.hasNextLong()) {
                                idProdEditar = scanner.nextLong();
                                if (idProdEditar < 0) System.out.println("Error: El ID no puede ser negativo.");
                            } else {
                                System.out.println("Error: Ingrese un ID numérico válido.");
                                scanner.next();
                            }
                        }
                        scanner.nextLine();

                        Producto producto = productoService.readByID(idProdEditar);

                        if (producto == null) {
                            System.out.println("Error: Producto no encontrado.");
                        } else {
                            String nuevoNombre = "";
                            while (nuevoNombre.trim().isEmpty() || nuevoNombre.matches(".*\\d.*")) {
                                System.out.print("Nuevo nombre: ");
                                nuevoNombre = scanner.nextLine();
                                if (nuevoNombre.trim().isEmpty() || nuevoNombre.matches(".*\\d.*")) {
                                    System.out.println("Error: El nombre no puede contener números ni estar vacío.");
                                }
                            }
                            producto.setNombre(nuevoNombre.trim());

                            String nuevaDesc = "";
                            while (nuevaDesc.trim().isEmpty()) {
                                System.out.print("Nueva descripción: ");
                                nuevaDesc = scanner.nextLine();
                                if (nuevaDesc.trim().isEmpty()) {
                                    System.out.println("Error: La descripción no puede estar vacía.");
                                }
                            }
                            producto.setDescripcion(nuevaDesc.trim());

                            double nuevoPrecio = -1;
                            while (nuevoPrecio < 0) {
                                System.out.print("Nuevo precio: ");
                                if (scanner.hasNextDouble()) {
                                    nuevoPrecio = scanner.nextDouble();
                                    if (nuevoPrecio < 0) System.out.println("Error: El precio no puede ser negativo.");
                                } else {
                                    System.out.println("Error: Tiene que ingresar un número válido.");
                                    scanner.next();
                                }
                            }
                            producto.setPrecio(nuevoPrecio);

                            int nuevoStock = -1;
                            while (nuevoStock < 0) {
                                System.out.print("Nuevo stock: ");
                                if (scanner.hasNextInt()) {
                                    nuevoStock = scanner.nextInt();
                                    if (nuevoStock < 0) System.out.println("Error: El stock no puede ser negativo.");
                                } else {
                                    System.out.println("Error: Tiene que ingresar un número válido.");
                                    scanner.next();
                                }
                            }
                            scanner.nextLine(); // Limpiar buffer
                            producto.setStock(nuevoStock);

                            productoService.update(producto);
                            System.out.println("Producto actualizado exitosamente.");
                        }
                        break;

                    case 4:
                        System.out.println("\n--- ELIMINAR PRODUCTO ---");
                        long idProdEliminar = -1;
                        while (idProdEliminar < 0) {
                            System.out.print("Ingrese ID del producto a eliminar: ");
                            if (scanner.hasNextLong()) {
                                idProdEliminar = scanner.nextLong();
                                if (idProdEliminar < 0) System.out.println("Error: El ID no puede ser negativo.");
                            } else {
                                System.out.println("Error: Ingrese un ID numérico válido.");
                                scanner.next();
                            }
                        }
                        scanner.nextLine();
                        
                        productoService.delete(idProdEliminar);
                        System.out.println("Producto eliminado.");
                        break;

                    case 0:
                        System.out.println("Volviendo...");
                        break;

                    default:
                        System.out.println("Opción incorrecta.");
                }

                if (opcionProd != 0) {
                    presionarEnterParaContinuar(scanner);
                }

            } else {
                System.out.println("Ingrese un número válido.");
                scanner.next();
                presionarEnterParaContinuar(scanner);
            }

        } while (opcionProd != 0);
    }

    // ==========================================================
    // # SUBMENÚ: USUARIOS
    // ==========================================================
    public static void menuUsuarios(Scanner scanner) {
        int opcionUsu = -1;
        do {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║         MENÚ DE USUARIOS         ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  [1] Listar Usuarios             ║");
            System.out.println("║  [2] Crear Usuario               ║");
            System.out.println("║  [3] Editar Usuario              ║");
            System.out.println("║  [4] Eliminar Usuario            ║");
            System.out.println("║  [0] Volver al Menú Principal    ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcionUsu = scanner.nextInt();
                scanner.nextLine();

                switch (opcionUsu) {
                    case 1:
                        System.out.println("\n=======================================================================");
                        System.out.println("                           LISTA DE USUARIOS");
                        System.out.println("=======================================================================");
                        List<Usuario> listaUsuarios = usuarioService.readAll();
                        if (listaUsuarios == null || listaUsuarios.isEmpty()) {
                            System.out.println("No hay usuarios cargados en el sistema.");
                        } else {
                            System.out.printf("%-6s | %-25s | %-30s\n", "ID", "NOMBRE", "MAIL");
                            System.out.println("-----------------------------------------------------------------------");
                            for (Usuario u : listaUsuarios) {
                                System.out.printf("%-6d | %-25s | %-30s\n", u.getId(), u.getNombre(), u.getMail());
                            }
                        }
                        System.out.println("=======================================================================\n");
                        break;

                    case 2:
                        System.out.println("\n--- REGISTRAR NUEVO USUARIO ---");
                        String nom = "";
                        while (!nom.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$") || nom.trim().isEmpty()) {
                            System.out.print("Nombre: ");
                            nom = scanner.nextLine();
                            if (!nom.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$") || nom.trim().isEmpty()) {
                                System.out.println("Error: Tiene que ingresar solo letras y no puede dejarlo vacío.");
                            }
                        }

                        String ape = "";
                        while (!ape.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$") || ape.trim().isEmpty()) {
                            System.out.print("Apellido: ");
                            ape = scanner.nextLine();
                            if (!ape.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$") || ape.trim().isEmpty()) {
                                System.out.println("Error: Tiene que ingresar solo letras y no puede dejarlo vacío.");
                            }
                        }

                        String correo = "";
                        while (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                            System.out.print("Mail: ");
                            correo = scanner.nextLine();
                            if (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                                System.out.println("Error: Por favor, ingrese un correo válido que contenga '@' y un dominio.(ejemplo@correo.com)");
                            }
                        }

                        String cel = "";
                        while (!cel.matches("\\d+")) {
                            System.out.print("Celular: ");
                            cel = scanner.nextLine();
                            if (!cel.matches("\\d+")) {
                                System.out.println("Error: Tiene que ingresar solo números para el celular.");
                            }
                        }

                        String contra = "";
                        while (contra.trim().isEmpty()) {
                            System.out.print("Contraseña: ");
                            contra = scanner.nextLine();
                            if (contra.trim().isEmpty()) {
                                System.out.println("Error: La contraseña no puede estar vacía.");
                            }
                        }

                        Usuario nuevoUsuario = new Usuario();
                        nuevoUsuario.setNombre(nom.trim());
                        nuevoUsuario.setApellido(ape.trim());
                        nuevoUsuario.setMail(correo);
                        nuevoUsuario.setCelular(cel);
                        nuevoUsuario.setContrasenia(contra);

                        usuarioService.create(nuevoUsuario);
                        System.out.println("Usuario registrado correctamente.");
                        break;

                    case 3:
                        System.out.println("\n--- MODIFICAR USUARIO ---");
                        long idUsuModificar = -1;
                        while (idUsuModificar < 0) {
                            System.out.print("Ingrese el ID del usuario a modificar: ");
                            if (scanner.hasNextLong()) {
                                idUsuModificar = scanner.nextLong();
                                if (idUsuModificar < 0) System.out.println("Error: El ID no puede ser negativo.");
                            } else {
                                System.out.println("Error: Ingrese un ID numérico válido.");
                                scanner.next();
                            }
                        }
                        scanner.nextLine();

                        Usuario usuarioEditar = usuarioService.readByID(idUsuModificar);

                        if (usuarioEditar != null) {
                            System.out.println("Usuario encontrado: " + usuarioEditar.getNombre() + " "
                                    + usuarioEditar.getApellido());
                            System.out.println("(Deje en blanco y presione Enter para mantener el dato actual)");

                            boolean validNom = false;
                            while(!validNom) {
                                System.out.print("Nuevo Nombre [" + usuarioEditar.getNombre() + "]: ");
                                String nuevoNom = scanner.nextLine();
                                if(nuevoNom.trim().isEmpty()) {
                                    validNom = true;
                                } else if(nuevoNom.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
                                    usuarioEditar.setNombre(nuevoNom);
                                    validNom = true;
                                } else {
                                    System.out.println("Error: El nombre solo puede contener letras.");
                                }
                            }

                            boolean validApe = false;
                            while(!validApe) {
                                System.out.print("Nuevo Apellido [" + usuarioEditar.getApellido() + "]: ");
                                String nuevoApe = scanner.nextLine();
                                if(nuevoApe.trim().isEmpty()) {
                                    validApe = true;
                                } else if(nuevoApe.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
                                    usuarioEditar.setApellido(nuevoApe);
                                    validApe = true;
                                } else {
                                    System.out.println("Error: El apellido solo puede contener letras.");
                                }
                            }

                            boolean validCel = false;
                            while(!validCel) {
                                System.out.print("Nuevo Celular [" + usuarioEditar.getCelular() + "]: ");
                                String nuevoCel = scanner.nextLine();
                                if(nuevoCel.trim().isEmpty()) {
                                    validCel = true;
                                } else if(nuevoCel.matches("\\d+")) {
                                    usuarioEditar.setCelular(nuevoCel);
                                    validCel = true;
                                } else {
                                    System.out.println("Error: El celular solo puede contener números.");
                                }
                            }

                            System.out.print("Nueva Contraseña: ");
                            String nuevaContra = scanner.nextLine();
                            if (!nuevaContra.trim().isEmpty()){
                                usuarioEditar.setContrasenia(nuevaContra);
                            }

                            usuarioService.update(usuarioEditar);
                            System.out.println("Usuario modificado exitosamente.");
                        } else {
                            System.out.println("No se encontró ningún usuario activo con el ID: " + idUsuModificar);
                        }
                        break;

                    case 4:
                        System.out.println("\n--- ELIMINAR USUARIO ---");
                        long idUsuEliminar = -1;
                        while (idUsuEliminar < 0) {
                            System.out.print("Ingrese el ID del usuario a eliminar: ");
                            if (scanner.hasNextLong()) {
                                idUsuEliminar = scanner.nextLong();
                                if (idUsuEliminar < 0) System.out.println("Error: El ID no puede ser negativo.");
                            } else {
                                System.out.println("Error: Ingrese un ID numérico válido.");
                                scanner.next();
                            }
                        }
                        scanner.nextLine(); 

                        Usuario usuarioEliminar = usuarioService.readByID(idUsuEliminar);

                        if (usuarioEliminar != null) {
                            System.out.print("¿Seguro que desea eliminar a " + usuarioEliminar.getNombre() + " "
                                    + usuarioEliminar.getApellido() + "? (S/N): ");
                            String confirma = scanner.nextLine();

                            if (confirma.equalsIgnoreCase("S")) {
                                usuarioService.delete(idUsuEliminar);
                                System.out.println("Usuario eliminado.");
                            } else {
                                System.out.println("Eliminación cancelada.");
                            }
                        } else {
                            System.out.println("No se encontró ningún usuario activo con el ID: " + idUsuEliminar);
                        }
                        break;
                        
                    case 0:
                        System.out.println("Saliendo del menu Usuarios...");
                        break;
                    default:
                        System.out.println("Error: Opcion no valida, Intente de nuevo");
                }

                if (opcionUsu != 0) {
                    presionarEnterParaContinuar(scanner);
                }
            } else {
                System.out.println("Error: Ingrese un número.");
                scanner.next();
                presionarEnterParaContinuar(scanner);
            }
        } while (opcionUsu != 0);
    }

    // ==========================================================
    // # SUBMENÚ: PEDIDOS
    // ==========================================================
    public static void menuPedidos(Scanner scanner) {
        int opcionPed = -1;
        do {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║          MENÚ DE PEDIDOS         ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  [1] Listar Pedidos              ║");
            System.out.println("║  [2] Crear Pedido                ║");
            System.out.println("║  [3] Editar Pedido               ║");
            System.out.println("║  [4] Eliminar Pedido             ║");
            System.out.println("║  [0] Volver al Menú Principal    ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcionPed = scanner.nextInt();
                scanner.nextLine();

                switch (opcionPed) {
                    case 1: 
                        System.out.println("\n==========================================================================================");
                        System.out.println("                                     LISTA DE PEDIDOS");
                        System.out.println("==========================================================================================");
                        List<Pedido> listaPedidos = pedidoService.readAll();
                        if (listaPedidos.isEmpty()) {
                            System.out.println("No hay pedidos registrados en el sistema.");
                        } else {
                            System.out.printf("%-8s | %-12s | %-12s | %-12s | %-15s | %-8s\n", "ID PED.", "FECHA", "ESTADO", "TOTAL", "PAGO", "ID USU.");
                            System.out.println("------------------------------------------------------------------------------------------");
                            for (Pedido p : listaPedidos) {
                                System.out.printf("%-8d | %-12s | %-12s | $%-10.2f | %-15s | %-8d\n", 
                                    p.getId(), p.getFecha().toString(), p.getEstado().toString(), p.getTotal(), p.getPago().toString(), p.getUsuario().getId());
                            }
                        }
                        System.out.println("==========================================================================================\n");
                        break;
                        
                    case 2:
                        System.out.println("\n--- CREAR NUEVO PEDIDO ---");

                        // 1. Datos basicos
                        long idUsu = -1;
                        while (idUsu < 0) {
                            System.out.print("Ingrese el ID del Usuario: ");
                            if (scanner.hasNextLong()) {
                                idUsu = scanner.nextLong();
                                if (idUsu < 0) System.out.println("Error: El ID no puede ser negativo.");
                            } else {
                                System.out.println("Error: Tiene que ingresar un número válido.");
                                scanner.next(); 
                            }
                        }
                        scanner.nextLine();

                        Usuario usu = new Usuario();
                        usu.setId(idUsu);

                        Pedido nuevoPedido = new Pedido();
                        nuevoPedido.setUsuario(usu);
                        nuevoPedido.setFecha(java.time.LocalDate.now());
                        nuevoPedido.setEstado(Estado.PENDIENTE); 
                        
                        // 2. Seleccion de Forma de Pago
                        int opcPago = 0;
                        while (opcPago < 1 || opcPago > 3) {
                            System.out.println("Forma de pago: 1. EFECTIVO, 2. TARJETA, 3. TRANSFERENCIA");
                            System.out.print("Opción: ");
                            if (scanner.hasNextInt()) {
                                opcPago = scanner.nextInt();
                                if (opcPago < 1 || opcPago > 3) {
                                    System.out.println("Error: Seleccione una opción válida (1, 2 o 3).");
                                }
                            } else {
                                System.out.println("Error: Tiene que ingresar un número válido.");
                                scanner.next(); 
                            }
                        }
                        scanner.nextLine(); 

                        nuevoPedido.setPago(opcPago == 1 ? FormaPago.EFECTIVO
                                : (opcPago == 2 ? FormaPago.TARJETA : FormaPago.TRANSFERENCIA));
                        
                        // 3. Carga de productos
                        List<DetallePedido> detalles = new ArrayList<>();
                        double totalPedido = 0;
                        boolean agregar = true;

                        while (agregar) {
                            long idProd = -1;
                            while (idProd < 0) {
                                System.out.print("Ingrese ID del producto: ");
                                if (scanner.hasNextLong()) {
                                    idProd = scanner.nextLong();
                                    if (idProd < 0) System.out.println("Error: El ID no puede ser negativo.");
                                } else {
                                    System.out.println("Error: El ID del producto debe ser un número.");
                                    scanner.next(); 
                                }
                            }
                                
                            int cant = 0;
                            while (cant <= 0) {
                                System.out.print("Cantidad: ");
                                if (scanner.hasNextInt()) {
                                    cant = scanner.nextInt();
                                    if (cant <= 0) System.out.println("Error: La cantidad debe ser mayor a cero.");
                                } else {
                                    System.out.println("Error: Tiene que ingresar un número válido.");
                                    scanner.next();
                                }
                            }
                            scanner.nextLine(); 

                            Producto prod = productoService.readByID(idProd);

                            if (prod != null) {
                                // CALCULAMOS EL SUBTOTAL USANDO EL PRECIO DEL PRODUCTO
                                double subtotal = prod.getPrecio() * cant;

                                DetallePedido det = new DetallePedido();
                                det.setProducto(prod);
                                det.setCantidad(cant);
                                det.setSubtotal(subtotal);

                                detalles.add(det);
                                totalPedido += subtotal;

                                System.out.println(
                                        "Producto agregado: " + prod.getNombre() + " | Subtotal: $" + subtotal);
                            } else {
                                System.out.println("Error: Producto no encontrado. No se agregó al pedido.");
                            }

                            System.out.print("¿Agregar otro producto? (S/N): ");
                            agregar = scanner.nextLine().equalsIgnoreCase("S");
                        }

                        if (!detalles.isEmpty()) {
                            nuevoPedido.setDetalles(detalles);
                            nuevoPedido.setTotal(totalPedido);

                            // 4: Guardar
                            pedidoService.create(nuevoPedido);
                            System.out.println("Pedido guardado con éxito por un total de: $" + totalPedido);
                        } else {
                            System.out.println("Cancelado: El pedido no se guardó porque no se agregaron productos.");
                        }
                        break;
                    
                    case 3:
                        System.out.println("\n--- MODIFICAR ESTADO/PAGO DE PEDIDO ---");
                        long idPedModificar = -1;
                        while (idPedModificar < 0) {
                            System.out.print("Ingrese el ID del pedido a modificar: ");
                            if (scanner.hasNextLong()) {
                                idPedModificar = scanner.nextLong();
                                if (idPedModificar < 0) System.out.println("Error: El ID no puede ser negativo.");
                            } else {
                                System.out.println("Error: Ingrese un ID numérico válido.");
                                scanner.next();
                            }
                        }
                            scanner.nextLine();

                        // 1. Buscamos el pedido en la BD
                        Pedido pedidoEditar = pedidoService.readByID(idPedModificar);

                        if (pedidoEditar != null) {
                            System.out.println("Pedido encontrado. Estado actual: " + pedidoEditar.getEstado() +
                                    " | Pago actual: " + pedidoEditar.getPago());
                            
                             // 2. Modificar Estado
                            int opcEstado = -1;
                            while(opcEstado < 0 || opcEstado > 4){
                                System.out.println("Seleccione nuevo Estado: 1. PENDIENTE, 2. CONFIRMADO, 3. TERMINADO, 4. CANCELADO");
                                System.out.print("Opción (0 para mantener actual): ");
                                if(scanner.hasNextInt()){
                                    opcEstado = scanner.nextInt();
                                    if(opcEstado < 0 || opcEstado > 4) System.out.println("Error: Seleccione entre 0 y 4.");
                                } else {
                                    System.out.println("Error: Tiene que ingresar un número válido.");
                                    scanner.next();
                                }
                            }
                            scanner.nextLine();

                            if (opcEstado >= 1 && opcEstado <= 4) {
                                Estado[] estados = Estado.values();
                                pedidoEditar.setEstado(estados[opcEstado - 1]);
                            }
                            
                            // 3. Modificar Forma de Pago
                            opcPago = -1;
                            while(opcPago < 0 || opcPago > 3){
                                System.out.println("Seleccione nueva Forma de Pago: 1. EFECTIVO, 2. TARJETA, 3. TRANSFERENCIA");
                                System.out.print("Opción (0 para mantener actual): ");
                                if(scanner.hasNextInt()){
                                    opcPago = scanner.nextInt();
                                    if(opcPago < 0 || opcPago > 3) System.out.println("Error: Seleccione entre 0 y 3.");
                                } else {
                                    System.out.println("Error: Tiene que ingresar un número válido.");
                                    scanner.next();
                                }
                            }
                            scanner.nextLine();

                            if (opcPago >= 1 && opcPago <= 3) {
                                FormaPago[] pagos = FormaPago.values();
                                pedidoEditar.setPago(pagos[opcPago - 1]);
                            }
                            
                            // 4. Guardamos los cambios
                            pedidoService.update(pedidoEditar);
                            System.out.println("¡Pedido actualizado correctamente!");
                        } else {
                            System.out.println("Error: No se encontró un pedido activo con el ID: " + idPedModificar);
                        }
                            break;

                    case 4:
                        System.out.println("\n--- CANCELAR/ELIMINAR PEDIDO ---");
                        long idPedEliminar = -1;
                        while (idPedEliminar < 0) {
                            System.out.print("Ingrese el ID del pedido a eliminar: ");
                            if (scanner.hasNextLong()) {
                                idPedEliminar = scanner.nextLong();
                                if (idPedEliminar < 0) System.out.println("Error: El ID no puede ser negativo.");
                            } else {
                                System.out.println("Error: Ingrese un ID numérico válido.");
                                scanner.next();
                            }
                        }
                        scanner.nextLine();

                        Pedido pedidoEliminar = pedidoService.readByID(idPedEliminar);

                        if (pedidoEliminar != null) {
                            System.out.print("¿Seguro que desea eliminar el pedido #" + pedidoEliminar.getId()
                                    + " del usuario " + pedidoEliminar.getUsuario().getId() + "? (S/N): ");
                            String confirma = scanner.nextLine();

                            if (confirma.equalsIgnoreCase("S")) {
                                pedidoService.delete(idPedEliminar);
                                System.out.println("Pedido eliminado.");
                            } else {
                                System.out.println("Operación cancelada.");
                            }
                        } else {
                            System.out.println("No se encontró un pedido activo con el ID: " + idPedEliminar);
                        }
                        break;

                    case 0:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    default:
                        System.out.println("Error: Opción incorrecta.");
                }

                if (opcionPed != 0) {
                    presionarEnterParaContinuar(scanner);
                }
            } else {
                System.out.println("Error: Ingrese un número.");
                scanner.next();
                presionarEnterParaContinuar(scanner);
            }
        } while (opcionPed != 0);
    }

    // ==========================================================
    // # MÉTODO AUXILIAR PARA PAUSAS
    // ==========================================================
    public static void presionarEnterParaContinuar(Scanner scanner) {
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}