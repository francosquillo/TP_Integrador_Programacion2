import java.util.ArrayList;
import java.util.List;

import entities.Producto;

public class ProductoDAO implements BaseDAO<Producto>{

    private List<Producto> nombreBaseDB = new ArrayList<>();
    private Long idAutoIncrement = 1L;

    @Override
    public void create(Producto entity) {
        entity.setId(idAutoIncrement++);
        nombreBaseDB.add(entity);
        System.out.println("Producto guardado");        
    }

    @Override
    public List<Producto> readAll(){
        List<Producto> productos = new ArrayList<>();
        for (Producto p : nombreBaseDB) {
            if (!p.isEliminado()) {
                productos.add(p);
            }
        }
        return productos;
    }

    @Override
    public Producto readByID(Long id){
        for (Producto p : nombreBaseDB) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void update(Producto entity){
        Producto existente = readByID(entity.getId());
        if (existente != null) {
            existente.setNombre(entity.getNombre());
            existente.setDescripcion(entity.getDescripcion());
            existente.setPrecio(entity.getPrecio());
            existente.setStock(entity.getStock());
            existente.setImagen(entity.getImagen());
            existente.setDisponible(entity.isDisponible());
            System.out.println("Producto actualizado");
        }
    }

    @Override
    public void delete(Long id){
        Producto existetnte = readByID(id);
        if (existetnte != null) {
            existetnte.setEliminado(true);
            System.out.println("Producto eliminada");
        }
    }
}
