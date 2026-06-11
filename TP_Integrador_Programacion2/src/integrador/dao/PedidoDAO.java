package dao;

import entities.DetallePedido;
import entities.Pedido;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import config.ConexionDB;

public class PedidoDAO implements BaseDAO<Pedido> {

    @Override
    public void create(Pedido entity){
        String sqlPedido = "INSERT INTO pedido (eliminado, createdAt, fecha, estado, total, forma_Pago, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_pedido (eliminado, createdAt, cantidad, subtotal, id_pedido, id_producto) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;

        try{
            conn = ConexionDB.getConexion();
            conn.setAutoCommit(false);

            try(PreparedStatement pstmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)){

                pstmtPedido.setBoolean(1, false);
                LocalDateTime fechaCreacion = entity.getCreatedAt() != null ? entity.getCreatedAt() : LocalDateTime.now();
                pstmtPedido.setTimestamp(2, Timestamp.valueOf(fechaCreacion));
                pstmtPedido.setDate(3, java.sql.Date.valueOf(entity.getFecha()));
                pstmtPedido.setString(4, entity.getEstado().name());
                pstmtPedido.setDouble(5, entity.getTotal());
                pstmtPedido.setString(6, entity.getPago().name());
                pstmtPedido.setLong(7, entity.getUsuario().getId());

                pstmtPedido.executeUpdate();

                try(ResultSet rs = pstmtPedido.getGeneratedKeys()){
                    if (rs.next()) {
                        long idPedidoGenerado = rs.getLong(1);
                        entity.setId(idPedidoGenerado);
                    }
                }
            }

            try(PreparedStatement pstmtDetalle = conn.prepareStatement(sqlDetalle)){
                for (DetallePedido detalle : entity.getDetalles()) {
                    pstmtDetalle.setBoolean(1, false);
                    pstmtDetalle.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                    pstmtDetalle.setInt(3, detalle.getCantidad());
                    pstmtDetalle.setDouble(4, detalle.getSubtotal());
                    pstmtDetalle.setLong(5, entity.getId());
                    pstmtDetalle.setLong(6, detalle.getProducto().getId());

                    pstmtDetalle.executeUpdate();
                }
            }

            conn.commit();
            System.out.println("El pedido y sus detalles de guardaron correctamente");
        }catch (SQLException e){
            System.out.println("Error al crear el pedido");
            if (conn != null) {
                try{
                    conn.rollback();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally{
            if (conn != null) {
                try{
                    conn.setAutoCommit(true);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Pedido> readAll(){
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Pedido p = new Pedido();
                    p.setId(rs.getLong("id_pedido"));
                    java.sql.Date fechaSQL = rs.getDate("fecha");
                    if (fechaSQL != null) {
                        p.setFecha(fechaSQL.toLocalDate());
                    }
                    String estadoSQL = rs.getString("estado");
                    if (estadoSQL != null) {
                        p.setEstado(Estado.valueOf(estadoSQL));
                    }
                    p.setTotal(rs.getDouble("total"));
                    String pagoSQL = rs.getString("forma_Pago");
                    if (pagoSQL != null) {
                        p.setPago(FormaPago.valueOf(pagoSQL));
                    }
                    long idUsuario = rs.getLong("id_usuario");
                    Usuario usuarioPedido = new Usuario();
                    usuarioPedido.setId(idUsuario);
                    p.setUsuario(usuarioPedido);
                    p.setEliminado(rs.getBoolean("eliminado"));
                    Timestamp ts = rs.getTimestamp("createdAt");
                    if (ts != null) p.setCreatedAt(ts.toLocalDateTime());

                    lista.add(p);
                }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Pedido readByID(Long id){
        Pedido p = null;
        String sql = "SELECT * FROM Pedido WHERE id_pedido = ? AND eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setLong(1, id);
                try (ResultSet rs = pstmt.executeQuery()){
                    if (rs.next()) {
                        p = new Pedido();
                        p.setId(rs.getLong("id_pedido"));
                    java.sql.Date fechaSQL = rs.getDate("fecha");
                    if (fechaSQL != null) {
                        p.setFecha(fechaSQL.toLocalDate());
                    }
                    String estadoSQL = rs.getString("estado");
                    if (estadoSQL != null) {
                        p.setEstado(Estado.valueOf(estadoSQL));
                    }
                    p.setTotal(rs.getDouble("total"));
                    String pagoSQL = rs.getString("forma_Pago");
                    if (pagoSQL != null) {
                        p.setPago(FormaPago.valueOf(pagoSQL));
                    }
                    long idUsuario = rs.getLong("id_usuario");
                    Usuario usuarioPedido = new Usuario();
                    usuarioPedido.setId(idUsuario);
                    p.setUsuario(usuarioPedido);
                    p.setEliminado(rs.getBoolean("eliminado"));
                    Timestamp ts = rs.getTimestamp("createdAt");
                    if (ts != null) p.setCreatedAt(ts.toLocalDateTime());
                    }
                }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public void update(Pedido entity){
        String sql = "UPDATE pedido SET estado = ?, forma_Pago = ? WHERE id_pedido = ? AND eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1, entity.getEstado().name());
                pstmt.setString(2, entity.getPago().name());
                pstmt.setLong(3, entity.getId());

                int filas = pstmt.executeUpdate();
                if(filas > 0) System.out.println("Pedido Actualizado");
            }catch (SQLException e){
                e.printStackTrace();
            }
    }

    @Override
    public void delete(Long id){
        String sql = "UPDATE pedido SET eliminado = true WHERE id_pedido = ?";

        try (Connection conn = ConexionDB.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setLong(1, id);
                int filas = pstmt.executeUpdate();
                if(filas > 0) System.out.println("Pedido cancelado");
            }catch(SQLException e){
                e.printStackTrace();
            }
    }
}
