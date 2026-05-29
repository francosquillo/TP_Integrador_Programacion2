package service;

import java.util.List;
import entities.Pedido;
import dao.PedidoDAO;

public class PedidoService implements GenericService<Pedido> {

    private PedidoDAO pedidoDAO = new PedidoDAO();

    @Override
    public void create(Pedido entity) {

        if (entity.getDetalles() == null || entity.getDetalles().isEmpty()) {
            System.out.println("Error: No se puede crear un pedido sin productos.");
            return;
        }

        if (entity.getTotal() <= 0) {
            System.out.println("Error: El total del pedido debe ser mayor a 0.");
            return;
        }

        pedidoDAO.create(entity);
    }

    @Override
    public List<Pedido> readAll() {
        return pedidoDAO.readAll();
    }

    @Override
    public Pedido readByID(Long id) {
        return pedidoDAO.readByID(id);
    }

    @Override
    public void update(Pedido entity) {

        if (entity.getEstado() == null || entity.getPago() == null) {
            System.out.println("Error: El estado y la forma de pago son obligatorios.");
            return;
        }
        pedidoDAO.update(entity);
    }

    @Override
    public void delete(Long id) {
        pedidoDAO.delete(id);
    }
}