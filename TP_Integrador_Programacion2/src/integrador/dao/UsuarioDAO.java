package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import config.ConexionDB;
import entities.Usuario;
import enums.Rol;

public class UsuarioDAO implements BaseDAO<Usuario> {

    @Override
    public void create(Usuario entity){
        String sql = "INSERT INTO usuario (eliminado, createdAt, nombre, apellido, mail, celular, contrasenia, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
                pstmt.setBoolean(1, false);
                LocalDateTime fechaCreacion = entity.getCreatedAt() != null ? entity.getCreatedAt() : LocalDateTime.now();
                pstmt.setTimestamp(2, Timestamp.valueOf(fechaCreacion));
                pstmt.setString(3, entity.getNombre());
                pstmt.setString(4, entity.getApellido());
                pstmt.setString(5, entity.getMail());
                pstmt.setString(6, entity.getCelular());
                pstmt.setString(7, entity.getContrasenia());
                String rolStr = entity.getRol() != null ? entity.getRol().name() : Rol.USUARIO.name();
                pstmt.setString(8,rolStr);

                pstmt.executeUpdate();
                
                try(ResultSet rs = pstmt.getGeneratedKeys()){
                    if (rs.next()) {
                        entity.setId(rs.getLong(1));
                        System.out.println("Usuario registrado correctamente");
                    }
                }

            }catch (SQLIntegrityConstraintViolationException e){
                System.out.println("Error, ya existe un usuario con ese mail");
            } catch (SQLException e){
                System.out.println("Error al crear el producto");
                e.printStackTrace();
            }
    }

    @Override
    public List<Usuario> readAll(){
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getLong("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setMail(rs.getString("mail"));
                    u.setCelular(rs.getString("celular"));
                    u.setContrasenia(rs.getString("contrasenia"));
                    String rolSQL = rs.getString("rol");
                    if (rolSQL != null) {
                        u.setRol(Rol.valueOf(rolSQL));
                    }
                    u.setEliminado(rs.getBoolean("eliminado"));
                    Timestamp ts = rs.getTimestamp("createdAt");
                    if (ts != null) u.setCreatedAt(ts.toLocalDateTime());

                    lista.add(u);
                }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Usuario readByID(Long id){
        Usuario u = null;
        String sql = "SELECT * FROM usuario WHERE id = ? AND eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setLong(1, id);
                try (ResultSet rs = pstmt.executeQuery()){
                    if (rs.next()) {
                        u = new Usuario();
                        u.setId(rs.getLong("id_usuario"));
                        u.setNombre(rs.getString("nombre"));
                        u.setApellido(rs.getString("apellido"));
                        u.setMail(rs.getString("mail"));
                        u.setCelular(rs.getString("celular"));
                        u.setContrasenia(rs.getString("contrasenia"));
                        String rolSQL = rs.getString("rol");
                        if (rolSQL != null) {
                            u.setRol(Rol.valueOf(rolSQL));
                        }
                        u.setEliminado(rs.getBoolean("eliminado"));
                        Timestamp ts = rs.getTimestamp("createdAt");
                        if (ts != null) u.setCreatedAt(ts.toLocalDateTime());
                    }
                }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public void update(Usuario entity){
        String sql = "UPDATE usuario SET nombre = ?, apellido = ?, celular = ?, contrasenia = ?, rol = ? WHERE id = ? AND eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1, entity.getNombre());
                pstmt.setString(2, entity.getApellido());
                pstmt.setString(3, entity.getCelular());
                pstmt.setString(4, entity.getContrasenia());
                pstmt.setString(5, entity.getRol().name());
                pstmt.setLong(6, entity.getId());

                int filas = pstmt.executeUpdate();
                if(filas > 0) System.out.println("Usuario Actualizado");
            }catch (SQLException e){
                e.printStackTrace();
            }
    }

    @Override
    public void delete(Long id){
        String sql = "UPDATE usuario SET eliminado = true WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setLong(1, id);
                int filas = pstmt.executeUpdate();
                if(filas > 0) System.out.println("Usuario eliminado");
            }catch(SQLException e){
                e.printStackTrace();
            }
    }
}
