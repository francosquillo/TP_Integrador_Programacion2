package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import config.ConexionDB;
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
        if (entity.getDescripcion() == null || entity.getDescripcion().trim().isEmpty()) {
            System.out.println("Error: La Descripcion debe tener un nombre obligatorio.");
            return;
        }
        List<Categoria> categorias = categoriaDAO.readAll();

        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equalsIgnoreCase(entity.getNombre())) {
                System.out.println("Error: Ya existe una categoría con ese nombre.");
                return;
            }
        }

        categoriaDAO.create(entity);
        System.out.println("Categoría creada correctamente.");
        
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
        Categoria categoriaExistente = categoriaDAO.readByID(entity.getId());

        if (entity.getNombre() == null || entity.getNombre().trim().isEmpty()) {
            System.out.println("Error: El nombre de la categoria no puede quedar vacio.");
            return;
        }
        if (categoriaExistente == null) {
            System.out.println("Error: No existe una categoría con ese ID.");
            return;
        }

        if (categoriaExistente.isEliminado()) {
            System.out.println("Error: La categoría está eliminada y no puede modificarse.");
            return;
        }
        categoriaDAO.update(entity);
    }

    @Override
public void delete(Long id){
    String sql = "UPDATE categoria SET eliminado = true WHERE id_categoria = ?";

    try (Connection conn = ConexionDB.getConexion();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setLong(1, id);

        int filas = pstmt.executeUpdate();

        System.out.println("Filas afectadas: " + filas);

        

    } catch(SQLException e){
        e.printStackTrace();
    }
}
}   