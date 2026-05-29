import java.util.Scanner;
import entities.Producto;
import service.ProductoService;

public class AppMenu {

    // DECLARACIÓN CORRECTA: Como campo de la clase
    private Scanner scanner = new Scanner(System.in);
    private ProductoService productoService = new ProductoService();

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- BIENVENIDO A FOOD STORE ---");
            System.out.println("1. Gestionar Productos");
            System.out.println("2. Gestionar Pedidos (En construccion)");
            System.out.println("3. Salir");
            System.out.print("Elegí una opcion: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    menuProductos();
                    break;
                case "2":
                    System.out.println("Pronto armaremos este menu...");
                    break;
                case "3":
                    System.out.println("¡Gracias por usar Food Store! Saliendo...");
                    salir = true;
                    break;
                default:
                    System.out.println("Error: Opcion no valida. Intenta de nuevo.");
            }
        }
    }

    private void menuProductos() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTION DE PRODUCTOS ---");
            System.out.println("1. Crear Producto nuevo");
            System.out.println("2. Ver todos los productos");
            System.out.println("3. Volver al menu principal");
            System.out.print("Elegí una opcion: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Ingresa el nombre del producto: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Ingresa el precio: ");
                    double precio = Double.parseDouble(scanner.nextLine());

                    Producto nuevoProducto = new Producto();
                    nuevoProducto.setNombre(nombre);
                    nuevoProducto.setPrecio(precio);
                    nuevoProducto.setDisponible(true);

                    // USO DE LA VARIABLE DE CLASE
                    productoService.create(nuevoProducto);
                    System.out.println("¡Producto mandado a guardar!");
                    break;

                case "2":
                    System.out.println("\nLista de Productos:");

                    // USO DE LA VARIABLE DE CLASE
                    for (Producto p : productoService.readAll()) {
                        System.out.println(
                                "ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Precio: $" + p.getPrecio());
                    }
                    break;

                case "3":
                    volver = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }
}