package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import config.ConexionDB;
import entities.Categoria;
import entities.Producto;

public class ProductoDAO implements BaseDAO<Producto> {

    @Override
    public void create(Producto entity){
        String sql = "INSERT INTO producto (eliminado, createdAt, nombre, precio, descripcion, stock, imagen, disponible, id_categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
                pstmt.setBoolean(1, false);
                LocalDateTime fechaCreacion = entity.getCreatedAt() != null ? entity.getCreatedAt() : LocalDateTime.now();
                pstmt.setTimestamp(2, Timestamp.valueOf(fechaCreacion));
                pstmt.setString(3, entity.getNombre());
                pstmt.setDouble(4, entity.getPrecio());
                pstmt.setString(5, entity.getDescripcion());
                pstmt.setInt(6, entity.getStock());
                pstmt.setString(7, entity.getImagen());
                pstmt.setBoolean(8, false);
                pstmt.setLong(9, entity.getCategoria().getId());

                pstmt.executeUpdate();
                System.out.println("El producto se guardo correctamente");
            } catch (SQLException e){
                System.out.println("Error al crear el producto");
                e.printStackTrace();
            }
    }

    @Override
    public List<Producto> readAll(){
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Producto p = new Producto();
                    p.setId(rs.getLong("id_producto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setStock(rs.getInt("stock"));
                    p.setImagen(rs.getString("imagen"));
                    p.setDisponible(rs.getBoolean("disponible"));
                    p.setEliminado(rs.getBoolean("eliminado"));
                    Timestamp ts = rs.getTimestamp("createdAt");
                    if (ts != null) p.setCreatedAt(ts.toLocalDateTime());
                    long idCat = rs.getLong("id_categoria");
                    Categoria categoriaProducto = new Categoria();
                    categoriaProducto.setId(idCat);
                    p.setCategoria(categoriaProducto);

                    lista.add(p);
                }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Producto readByID(Long id){
        Producto p = null;
        String sql = "SELECT * FROM producto WHERE id = ? AND eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setLong(1, id);
                try (ResultSet rs = pstmt.executeQuery()){
                    if (rs.next()) {
                        p = new Producto();
                        p.setId(rs.getLong("id_producto"));
                        p.setNombre(rs.getString("nombre"));
                        p.setPrecio(rs.getDouble("precio"));
                        p.setDescripcion(rs.getString("descripcion"));
                        p.setStock(rs.getInt("stock"));
                        p.setImagen(rs.getString("imagen"));
                        p.setDisponible(rs.getBoolean("disponible"));
                        p.setEliminado(rs.getBoolean("eliminado"));
                        Timestamp ts = rs.getTimestamp("createdAt");
                        if (ts != null) p.setCreatedAt(ts.toLocalDateTime());
                        long idCat = rs.getLong("id_categoria");
                        Categoria categoriaProducto = new Categoria();
                        categoriaProducto.setId(idCat);
                        p.setCategoria(categoriaProducto);
                    }
                }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public void update(Producto entity){
        String sql = "UPDATE producto SET nombre = ?, precio = ?, descripcion = ?, stock = ?, imagen = ?, disponible = ?, id_categoria = ? WHERE id = ? AND eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1, entity.getNombre());
                pstmt.setDouble(2, entity.getPrecio());
                pstmt.setString(3, entity.getDescripcion());
                pstmt.setInt(4, entity.getStock());
                pstmt.setLong(8, entity.getId());
                pstmt.setString(5, entity.getImagen());
                pstmt.setBoolean(6, entity.isDisponible());
                pstmt.setLong(7, entity.getCategoria().getId());
                int filas = pstmt.executeUpdate();
                if(filas > 0) System.out.println("Producto Actualizado");
            }catch (SQLException e){
                e.printStackTrace();
            }
    }

    @Override
    public void delete(Long id){
        String sql = "UPDATE producto SET eliminado = true WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setLong(1, id);
                int filas = pstmt.executeUpdate();
                if(filas > 0) System.out.println("Producto eliminado");
            }catch(SQLException e){
                e.printStackTrace();
            }
    }
}
