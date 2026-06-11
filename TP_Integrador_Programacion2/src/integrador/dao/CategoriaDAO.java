package dao;

import config.ConexionDB;
import entities.Categoria;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements BaseDAO<Categoria> {

    @Override
    public void create(Categoria entity){
        String sql = "INSERT INTO categoria (eliminado, createdAt, nombre, descripcion) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
                pstmt.setBoolean(1, false);
                LocalDateTime fechaCreacion = entity.getCreatedAt() != null ? entity.getCreatedAt() : LocalDateTime.now();
                pstmt.setTimestamp(2, Timestamp.valueOf(fechaCreacion));
                pstmt.setString(3, entity.getNombre());
                pstmt.setString(4, entity.getDescripcion());

                pstmt.executeUpdate();
                System.out.println("La categoria se guardo correctamente");
            } catch (SQLException e){
                System.out.println("Error al crear el producto");
                e.printStackTrace();
            }
    }

    @Override
    public List<Categoria> readAll(){
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria WHERE eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Categoria c = new Categoria();
                    c.setId(rs.getLong("id_categoria"));
                    c.setNombre(rs.getString("nombre"));
                    c.setDescripcion(rs.getString("descripcion"));
                    c.setEliminado(rs.getBoolean("eliminado"));
                    Timestamp ts = rs.getTimestamp("createdAt");
                    if (ts != null) c.setCreatedAt(ts.toLocalDateTime());

                    lista.add(c);
                }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Categoria readByID(Long id){
        Categoria c = null;
        String sql = "SELECT * FROM categoria WHERE id_categoria = ? AND eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setLong(1, id);
                try (ResultSet rs = pstmt.executeQuery()){
                    if (rs.next()) {
                        c = new Categoria();
                        c.setId(rs.getLong("id_categoria"));
                        c.setNombre(rs.getString("nombre"));
                        c.setDescripcion(rs.getString("descripcion"));
                        c.setEliminado(rs.getBoolean("eliminado"));
                        Timestamp ts = rs.getTimestamp("createdAt");
                        if (ts != null) c.setCreatedAt(ts.toLocalDateTime());
                    }
                }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public void update(Categoria entity){
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id_categoria = ? AND eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1, entity.getNombre());
                pstmt.setString(2, entity.getDescripcion());
                pstmt.setLong(3, entity.getId());

                int filas = pstmt.executeUpdate();
                if(filas > 0) System.out.println("Categoria Actualizada");
            }catch (SQLException e){
                e.printStackTrace();
            }
    }

    @Override
    public void delete(Long id){
        String sql = "UPDATE categoria SET eliminado = true WHERE id_categoria = ?";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setLong(1, id);
                int filas = pstmt.executeUpdate();
                if(filas > 0) System.out.println("Categoria eliminada");
            }catch(SQLException e){
                e.printStackTrace();
            }
    }
}