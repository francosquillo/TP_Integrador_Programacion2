package service;

import java.util.List;

import dao.ProductoDAO;
import entities.Producto;

public class ProductoService implements GenericService<Producto> {

    private ProductoDAO ProductoDAO = new ProductoDAO();
@Override
public void create(Producto entity) {

    if (entity.getPrecio() < 0) {
        System.out.println("Error: El precio no puede ser menor a 0.");
        return;
    }
    if (entity.getStock() < 0) {
        System.out.println("Error: El stock no puede ser menor a 0.");
        return;
    }
    if (entity.getDescripcion() == null || entity.getDescripcion().trim().isEmpty()) {
        System.out.println("Error: El producto debe tener una descripcion.");
        return;
    }
    if (entity.getNombre() == null || entity.getNombre().trim().isEmpty()) {
        System.out.println("Error: El producto debe tener un nombre.");
        return;
    }

    ProductoDAO.create(entity);
}

    @Override
    public List<Producto> readAll() {
        return ProductoDAO.readAll();
    }

    @Override
    public Producto readByID(Long id) {
        return ProductoDAO.readByID(id);
    }

    @Override
    public void update(Producto entity) {

        if (entity.getPrecio() < 0) {
            System.out.println("Error: El precio no puede ser menor a 0.");
            return;
        }

        ProductoDAO.update(entity);
    }

    @Override
    public void delete(Long id) {
        ProductoDAO.delete(id);
    }

    public Producto[] ReadAll() {
        throw new UnsupportedOperationException("Unimplemented method 'ReadAll'");
    }
}