package dao;

import java.util.ArrayList;
import java.util.List;

import entities.Categoria;

public class CategoriaDAO implements BaseDAO<Categoria> {

    private List<Categoria> nombreBaseDB = new ArrayList<>();
    private Long idAutoIncrement = 1L;

    @Override
    public void create(Categoria entity) {
        entity.setId(idAutoIncrement++);
        nombreBaseDB.add(entity);
        System.out.println("Categoria guardada");
    }

    @Override
    public List<Categoria> readAll() {
        List<Categoria> categorias = new ArrayList<>();
        for (Categoria c : nombreBaseDB) {
            if (!c.isEliminado()) {
                categorias.add(c);
            }
        }
        return categorias;
    }

    @Override
    public Categoria readByID(Long id) {
        for (Categoria c : nombreBaseDB) {
            if (c.getId().equals(id) && !c.isEliminado()) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void update(Categoria entity) {
        Categoria existente = readByID(entity.getId());
        if (existente != null) {
            existente.setNombre(entity.getNombre());
            existente.setDescripcion(entity.getDescripcion());
            System.out.println("Categoria actualizada");
        }
    }

    @Override
    public void delete(Long id) {
        Categoria existetnte = readByID(id);
        if (existetnte != null) {
            existetnte.setEliminado(true);
            System.out.println("Categoria eliminada");
        }
    }
}
