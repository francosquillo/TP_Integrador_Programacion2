package service;

import java.util.List;
import entities.Categoria;
import dao.CategoriaDAO;

public class CategoriaService implements GenericService<Categoria> {

    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    public void create(Categoria entity) {

        if (entity.getNombre() == null || entity.getNombre().trim().isEmpty()) {
            System.out.println("Error: La categoria debe tener un nombre obligatorio.");
            return;
        }

        categoriaDAO.create(entity);
    }

    @Override
    public List<Categoria> readAll() {

        return categoriaDAO.readAll();
    }

    @Override
    public Categoria readByID(Long id) {
        return categoriaDAO.readByID(id);
    }

    @Override
    public void update(Categoria entity) {

        if (entity.getNombre() == null || entity.getNombre().trim().isEmpty()) {
            System.out.println("Error: El nombre de la categoria no puede quedar vacio.");
            return;
        }
        categoriaDAO.update(entity);
    }

    @Override
    public void delete(Long id) {
        categoriaDAO.delete(id);
    }
}