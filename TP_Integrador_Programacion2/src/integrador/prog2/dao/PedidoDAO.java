import java.util.ArrayList;
import java.util.List;

import entities.DetallePedido;
import entities.Pedido;

public class PedidoDAO implements BaseDAO<Pedido>{

    private List<Pedido> nombreBaseDB = new ArrayList<>();
    private Long idAutoIncrement = 1L;

    @Override
    public void create(Pedido entity) {
        entity.setId(idAutoIncrement++);
        nombreBaseDB.add(entity);
        System.out.println("Pedidp guardado junto con su descripcion");        
    }

    @Override
    public List<Pedido> readAll(){
        List<Pedido> pedidos = new ArrayList<>();
        for (Pedido p : nombreBaseDB) {
            if (!p.isEliminado()) {
                pedidos.add(p);
            }
        }
        return pedidos;
    }

    @Override
    public Pedido readByID(Long id){
        for (Pedido p : nombreBaseDB) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void update(Pedido entity){
        Pedido existente = readByID(entity.getId());
        if (existente != null) {
            existente.setEstado(entity.getEstado());
            existente.setPago(entity.getPago());
            System.out.println("Pedido actualizado");
        }else{
            System.out.println("No se encontro ningun pedido para actualizar");
        }
    }

    @Override
    public void delete(Long id){
        Pedido existetnte = readByID(id);
        if (existetnte != null) {
            existetnte.setEliminado(true);
            for (DetallePedido detalle : existetnte.getDetalles()) {
                detalle.setEliminado(true);
            }
            System.out.println("Pedido eliminado");
        }
    }
}
