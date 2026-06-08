import java.util.List;
import java.util.Scanner;
import entities.Categoria;
import service.CategoriaService;

public class Main {
    
// # Instanciamos el servicio de categorías
    private static CategoriaService categoriaService = new CategoriaService();

    public static void main(String[] args) {
// # Inicializamos el scanner
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

// # BUCLE DEL MENÚ PRINCIPAL
        do {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Gestión de Categorías");
            System.out.println("2. Gestión de Productos");
            System.out.println("3. Gestión de Usuarios");
            System.out.println("4. Gestión de Pedidos");
            System.out.println("0. Salir del Sistema");
            System.out.print("Seleccione una opción: ");
            
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
            System.out.println("\n--- MENÚ CATEGORÍAS ---");
            System.out.println("1. Listar Categorías");
            System.out.println("2. Crear Categoría");
            System.out.println("3. Editar Categoría");
            System.out.println("4. Eliminar Categoría");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcionCat = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcionCat) {
                    case 1:
                        System.out.println("\n--- LISTA DE CATEGORÍAS ---");
                        List<Categoria> lista = categoriaService.readAll();
                        if (lista == null || lista.isEmpty()) {
                            System.out.println("No hay categorías cargadas.");
                        } else {
                            for (Categoria cat : lista) {
                                System.out.println("ID: " + cat.getId() + " | Nombre: " + cat.getNombre());
                            }
                        }
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
                        
                        // # Validamos que el ID ingresado sea un número (Long)
                        if (scanner.hasNextLong()) {
                            Long idEditar = scanner.nextLong();
                            scanner.nextLine(); 

                            // # Buscamos la categoría usando tu método readByID
                            Categoria catEditar = categoriaService.readByID(idEditar);

                            // # Si es null, significa que no existe o fue eliminada
                            if (catEditar == null) {
                                System.out.println("Error: No se encontró una categoría con el ID " + idEditar);
                            } else {
                                // # Mostramos el nombre actual para que el usuario sepa qué está editando
                                System.out.println("Editando la categoría: " + catEditar.getNombre());
                                
                                // # Pedimos el nuevo nombre y evitamos que lo deje vacío
                                String nuevoNombre = "";
                                while (nuevoNombre.trim().isEmpty()) {
                                    System.out.print("Ingrese el NUEVO nombre: ");
                                    nuevoNombre = scanner.nextLine();
                                }
                                
                                System.out.print("Ingrese la NUEVA descripción: ");
                                String nuevaDesc = scanner.nextLine();

                                // # Actualizamos los datos del objeto que trajimos de la base
                                catEditar.setNombre(nuevoNombre);
                                catEditar.setDescripcion(nuevaDesc);

                                // # Lo mandamos al service para que ejecute el update
                                categoriaService.update(catEditar);
                                System.out.println("Categoría editada exitosamente.");
                            }
                        } else {
                            System.out.println("Error: Ingrese un ID numérico válido.");
                            scanner.next(); // # Limpiamos la basura ingresada
                        }
                        break;

                    case 4:
                        System.out.println("\n--- ELIMINAR CATEGORÍA ---");
                        System.out.print("Ingrese el ID de la categoría a eliminar: ");
                        
                        // # Volvemos a validar que ingrese un número
                        if (scanner.hasNextLong()) {
                            Long idEliminar = scanner.nextLong();
                            scanner.nextLine(); 

                            // # Buscamos la categoría primero para asegurarnos de que exista
                            Categoria catEliminar = categoriaService.readByID(idEliminar);

                            if (catEliminar == null) {
                                System.out.println("Error: No se encontró la categoría con el ID " + idEliminar);
                            } else {
                                // # Cumplimos con la consigna: Pedimos confirmación antes de borrar
                                System.out.println("Atención: Está por eliminar la categoría '" + catEliminar.getNombre() + "'");
                                System.out.print("¿Está seguro? (1 para SÍ, 0 para NO): ");
                                
                                if (scanner.hasNextInt()) {
                                    int confirmacion = scanner.nextInt();
                                    scanner.nextLine();
                                    
                                    if (confirmacion == 1) {
                                        // # Si confirma, usamos tu método delete (acordate que la baja lógica la hace el DAO)
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
            System.out.println("\n--- MENÚ PRODUCTOS ---");
            System.out.println("1. Listar Productos");
            System.out.println("2. Crear Producto");
            System.out.println("3. Editar Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcionProd = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcionProd) {
                    case 1:
                        System.out.println("Listando productos... (Función en construcción)");
                        break;
                    case 2:
                        System.out.println("Creando producto... (Función en construcción)");
                        break;
                    case 3:
                        System.out.println("Editando producto... (Función en construcción)");
                        break;
                    case 4:
                        System.out.println("Eliminando producto... (Función en construcción)");
                        break;
                    case 0:
                        System.out.println("Volviendo...");
                        break;
                    default:
                        System.out.println("Error: Opción incorrecta.");
                }

                if (opcionProd != 0) {
                    presionarEnterParaContinuar(scanner);
                }
            } else {
                System.out.println("Error: Ingrese un número.");
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
            System.out.println("\n--- MENÚ USUARIOS ---");
            System.out.println("1. Listar Usuarios");
            System.out.println("2. Crear Usuario");
            System.out.println("3. Editar Usuario");
            System.out.println("4. Eliminar Usuario");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcionUsu = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcionUsu) {
                    case 1:
                        System.out.println("Listando usuarios... (Función en construcción)");
                        break;
                    case 2:
                        System.out.println("Creando usuario... (Función en construcción)");
                        break;
                    case 3:
                        System.out.println("Editando usuario... (Función en construcción)");
                        break;
                    case 4:
                        System.out.println("Eliminando usuario... (Función en construcción)");
                        break;
                    case 0:
                        System.out.println("Volviendo...");
                        break;
                    default:
                        System.out.println("Error: Opción incorrecta.");
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
            System.out.println("\n--- MENÚ PEDIDOS ---");
            System.out.println("1. Listar Pedidos");
            System.out.println("2. Crear Pedido");
            System.out.println("3. Editar Pedido");
            System.out.println("4. Eliminar Pedido");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcionPed = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcionPed) {
                    case 1:
                        System.out.println("Listando pedidos... (Función en construcción)");
                        break;
                    case 2:
                        System.out.println("Creando pedido... (Función en construcción)");
                        break;
                    case 3:
                        System.out.println("Editando pedido... (Función en construcción)");
                        break;
                    case 4:
                        System.out.println("Eliminando pedido... (Función en construcción)");
                        break;
                    case 0:
                        System.out.println("Volviendo...");
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
