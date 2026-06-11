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
                        while (nombre.trim().isEmpty()) {
                            System.out.print("Ingrese el nombre de la categoría: ");
                            nombre = scanner.nextLine();
                        }

                        System.out.print("Ingrese la descripción de la categoría: ");
                        String descripcion = scanner.nextLine();

                        Categoria nuevaCategoria = new Categoria();
                        nuevaCategoria.setNombre(nombre);
                        nuevaCategoria.setDescripcion(descripcion);

                        categoriaService.create(nuevaCategoria);
                        System.out.println("Categoría enviada para su creación.");
                        break;

                    case 3:
                        System.out.println("\n--- EDITAR CATEGORÍA ---");
                        System.out.print("Ingrese el ID de la categoría a editar: ");   

                        if (scanner.hasNextLong()) {
                            Long idEditar = scanner.nextLong();
                            scanner.nextLine();

                            Categoria catEditar = categoriaService.readByID(idEditar);

                            if (catEditar == null) {
                                System.out.println("Error: No se encontró una categoría con el ID " + idEditar);
                            } else {
                                System.out.println("Editando la categoría: " + catEditar.getNombre());

                                String nuevoNombre = "";
                                while (nuevoNombre.trim().isEmpty()) {
                                    System.out.print("Ingrese el NUEVO nombre: ");
                                    nuevoNombre = scanner.nextLine();
                                }

                                System.out.print("Ingrese la NUEVA descripción: ");
                                String nuevaDesc = scanner.nextLine();

                                catEditar.setNombre(nuevoNombre);
                                catEditar.setDescripcion(nuevaDesc);

                                categoriaService.update(catEditar);
                                System.out.println("Categoría editada exitosamente.");
                            }
                        } else {
                            System.out.println("Error: Ingrese un ID numérico válido.");
                            scanner.next();
                        }
                        break;

                    case 4:
                        System.out.println("\n--- ELIMINAR CATEGORÍA ---");
                        System.out.print("Ingrese el ID de la categoría a eliminar: ");

                        if (scanner.hasNextLong()) {
                            Long idEliminar = scanner.nextLong();
                            scanner.nextLine();

                            Categoria catEliminar = categoriaService.readByID(idEliminar);

                            if (catEliminar == null) {
                                System.out.println("Error: No se encontró la categoría con el ID " + idEliminar);
                            } else {
                                System.out.println(
                                        "Atención: Está por eliminar la categoría '" + catEliminar.getNombre() + "'");
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
                        } else {
                            System.out.println("Error: Ingrese un ID numérico válido.");
                            scanner.next();
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

                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Descripción: ");
                        String descripcion = scanner.nextLine();

                        System.out.print("Precio: ");
                        double precio = scanner.nextDouble();

                        System.out.print("Stock: ");
                        int stock = scanner.nextInt();
                        scanner.nextLine();

                        // Pedir categoría
                        System.out.print("ID de categoría: ");
                        Long idCategoria = scanner.nextLong();
                        scanner.nextLine();

                        Categoria categoria = categoriaService.readByID(idCategoria);

                        if (categoria == null) {
                            System.out.println("Categoría no encontrada.");
                            break;
                        }

                        Producto nuevoProducto = new Producto();

                        nuevoProducto.setNombre(nombre);
                        nuevoProducto.setDescripcion(descripcion);
                        nuevoProducto.setPrecio(precio);
                        nuevoProducto.setStock(stock);

                        // Asignar categoría al producto
                        nuevoProducto.setCategoria(categoria);

                        productoService.create(nuevoProducto);

                        System.out.println("Producto creado correctamente.");
                        break;

                    case 3:

                        System.out.print("Ingrese ID del producto: ");

                        if (scanner.hasNextLong()) {

                            Long idEditar = scanner.nextLong();
                            scanner.nextLine();

                            Producto producto = productoService.readByID(idEditar);

                            if (producto == null) {
                                System.out.println("Producto no encontrado.");
                            } else {

                                System.out.print("Nuevo nombre: ");
                                producto.setNombre(scanner.nextLine());

                                System.out.print("Nueva descripción: ");
                                producto.setDescripcion(scanner.nextLine());

                                System.out.print("Nuevo precio: ");
                                producto.setPrecio(scanner.nextDouble());

                                System.out.print("Nuevo stock: ");
                                producto.setStock(scanner.nextInt());
                                scanner.nextLine();

                                productoService.update(producto);

                                System.out.println("Producto actualizado.");
                            }
                        }
                        break;

                    case 4:

                        System.out.print("Ingrese ID del producto: ");

                        if (scanner.hasNextLong()) {

                            Long idEliminar = scanner.nextLong();
                            scanner.nextLine();

                            productoService.delete(idEliminar);

                            System.out.println("Producto eliminado.");
                        }

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
                        scanner.nextLine();

                        System.out.print("Nombre: ");
                        String nom = scanner.nextLine();

                        System.out.print("Apellido: ");
                        String ape = scanner.nextLine();

                        System.out.print("Mail: ");
                        String correo = scanner.nextLine();

                        System.out.print("Celular: ");
                        String cel = scanner.nextLine();

                        System.out.print("Contraseña: ");
                        String contra = scanner.nextLine();

                        // Creamos el objeto y le pasamos todas las variables
                        Usuario nuevoUsuario = new Usuario();
                        nuevoUsuario.setNombre(nom);
                        nuevoUsuario.setApellido(ape);
                        nuevoUsuario.setMail(correo);
                        nuevoUsuario.setCelular(cel);
                        nuevoUsuario.setContrasenia(contra);

                        usuarioService.create(nuevoUsuario);
                        break;
                    case 3:
                        System.out.println("\n--- MODIFICAR USUARIO ---");
                        System.out.print("Ingrese el ID del usuario a modificar: ");
                        long idModificar = scanner.nextLong();
                        scanner.nextLine(); // Limpia el buffer

                        Usuario usuarioEditar = usuarioService.readByID(idModificar);

                        if (usuarioEditar != null) {
                            System.out.println("Usuario encontrado: " + usuarioEditar.getNombre() + " "
                                    + usuarioEditar.getApellido());
                            System.out.println("(Deje en blanco y presione Enter para mantener el dato actual)");

                            System.out.print("Nuevo Nombre [" + usuarioEditar.getNombre() + "]: ");
                            String nuevoNom = scanner.nextLine();
                            if (!nuevoNom.trim().isEmpty())
                                usuarioEditar.setNombre(nuevoNom);

                            System.out.print("Nuevo Apellido [" + usuarioEditar.getApellido() + "]: ");
                            String nuevoApe = scanner.nextLine();
                            if (!nuevoApe.trim().isEmpty())
                                usuarioEditar.setApellido(nuevoApe);

                            System.out.print("Nuevo Celular [" + usuarioEditar.getCelular() + "]: ");
                            String nuevoCel = scanner.nextLine();
                            if (!nuevoCel.trim().isEmpty())
                                usuarioEditar.setCelular(nuevoCel);

                            System.out.print("Nueva Contraseña: ");
                            String nuevaContra = scanner.nextLine();
                            if (!nuevaContra.trim().isEmpty())
                                usuarioEditar.setContrasenia(nuevaContra);

                            usuarioService.update(usuarioEditar);
                        } else {
                            System.out.println("No se encontró ningún usuario activo con el ID: " + idModificar);
                        }
                        break;

                    case 4:
                        System.out.println("\n--- ELIMINAR USUARIO ---");
                        System.out.print("Ingrese el ID del usuario a eliminar: ");
                        long idEliminar = scanner.nextLong();
                        scanner.nextLine(); // Limpia el buffer

                        // Verificamos si existe antes de borrar
                        Usuario usuarioEliminar = usuarioService.readByID(idEliminar);

                        if (usuarioEliminar != null) {
                            System.out.print("¿Seguro que desea eliminar a " + usuarioEliminar.getNombre() + " "
                                    + usuarioEliminar.getApellido() + "? (S/N): ");
                            String confirma = scanner.nextLine();

                            if (confirma.equalsIgnoreCase("S")) {
                                usuarioService.delete(idEliminar);

                            } else {
                                System.out.println("Eliminación cancelada.");
                            }
                        } else {
                            System.out.println("No se encontró ningún usuario activo con el ID: " + idEliminar);
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
                    case 1: // LISTAR PEDIDOS
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

                        // 1. Datos básicos
                        System.out.print("Ingrese el ID del Usuario: ");
                        long idUsu = scanner.nextLong();
                        scanner.nextLine();

                        Usuario usu = new Usuario();
                        usu.setId(idUsu);

                        Pedido nuevoPedido = new Pedido();
                        nuevoPedido.setUsuario(usu);
                        nuevoPedido.setFecha(java.time.LocalDate.now());
                        nuevoPedido.setEstado(Estado.PENDIENTE); // O el estado inicial que usen

                        // 2. Selección de Forma de Pago
                        System.out.println("Forma de pago: 1. EFECTIVO, 2. TARJETA, 3. TRANSFERENCIA");
                        int opcPago = scanner.nextInt();
                        scanner.nextLine();
                        nuevoPedido.setPago(opcPago == 1 ? FormaPago.EFECTIVO
                                : (opcPago == 2 ? FormaPago.TARJETA : FormaPago.TRANSFERENCIA));

                        // 3. Carga de productos
                        List<DetallePedido> detalles = new ArrayList<>();
                        double totalPedido = 0;
                        boolean agregar = true;

                        while (agregar) {
                            System.out.print("Ingrese ID del producto: ");
                            long idProd = scanner.nextLong();
                            System.out.print("Cantidad: ");
                            int cant = scanner.nextInt();
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

                        nuevoPedido.setDetalles(detalles);
                        nuevoPedido.setTotal(totalPedido);

                        // 4. Guardar
                        pedidoService.create(nuevoPedido);
                        System.out.println("Pedido guardado con éxito por un total de: $" + totalPedido);
                        break;

                    case 3:
                        System.out.println("\n--- MODIFICAR ESTADO/PAGO DE PEDIDO ---");
                        System.out.print("Ingrese el ID del pedido a modificar: ");
                        long idModificar = scanner.nextLong();
                        scanner.nextLine();

                        // 1. Buscamos el pedido en la BD
                        Pedido pedidoEditar = pedidoService.readByID(idModificar);

                        if (pedidoEditar != null) {
                            System.out.println("Pedido encontrado. Estado actual: " + pedidoEditar.getEstado() +
                                    " | Pago actual: " + pedidoEditar.getPago());

                            // 2. Modificar Estado
                            System.out.println(
                                    "Seleccione nuevo Estado: 1. PENDIENTE, 2. CONFIRMADO, 3. TERMINADO, 4. CANCELADO");
                            System.out.print("Opción (0 para mantener actual): ");
                            int opcEstado = scanner.nextInt();
                            scanner.nextLine();

                            if (opcEstado >= 1 && opcEstado <= 4) {
                                Estado[] estados = Estado.values();
                                pedidoEditar.setEstado(estados[opcEstado - 1]);
                            }

                            // 3. Modificar Forma de Pago
                            System.out.println(
                                    "Seleccione nueva Forma de Pago: 1. EFECTIVO, 2. TARJETA, 3. TRANSFERENCIA");
                            System.out.print("Opción (0 para mantener actual): ");
                             opcPago = scanner.nextInt();
                            scanner.nextLine();

                            if (opcPago >= 1 && opcPago <= 3) {
                                FormaPago[] pagos = FormaPago.values();
                                pedidoEditar.setPago(pagos[opcPago - 1]);
                            }

                            // 4. Guardamos los cambios
                            pedidoService.update(pedidoEditar);
                            System.out.println("¡Pedido actualizado correctamente!");
                        } else {
                            System.out.println("Error: No se encontró un pedido activo con el ID: " + idModificar);
                        }

                        break;

                    case 4:
                        System.out.println("\n--- CANCELAR/ELIMINAR PEDIDO ---");
                        System.out.print("Ingrese el ID del pedido a eliminar: ");
                        long idEliminar = scanner.nextLong();
                        scanner.nextLine();

                        Pedido pedidoEliminar = pedidoService.readByID(idEliminar);

                        if (pedidoEliminar != null) {
                            System.out.print("¿Seguro que desea eliminar el pedido #" + pedidoEliminar.getId()
                                    + " del usuario " + pedidoEliminar.getUsuario().getId() + "? (S/N): ");
                            String confirma = scanner.nextLine();

                            if (confirma.equalsIgnoreCase("S")) {
                                pedidoService.delete(idEliminar);
                            } else {
                                System.out.println("Operación cancelada.");
                            }
                        } else {
                            System.out.println("No se encontró un pedido activo con el ID: " + idEliminar);
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
