import java.util.ArrayList;
import java.util.List;

import entities.Usuario;

public class UsuarioDAO implements BaseDAO<Usuario>{

    private List<Usuario> nombreBaseDB = new ArrayList<>();
    private Long idAutoIncrement = 1L;

    @Override
    public void create(Usuario entity) {
        for (Usuario u : nombreBaseDB) {
            if (u.getMail().equalsIgnoreCase(entity.getMail()) && !u.isEliminado()) {
                System.out.println("Error, ya existe un usuario registrado con ese mail");
                return;
            }
        }

        entity.setId(idAutoIncrement++);
        nombreBaseDB.add(entity);
        System.out.println("Usuario guardado");        
    }

    @Override
    public List<Usuario> readAll(){
        List<Usuario> usuarios = new ArrayList<>();
        for (Usuario u : nombreBaseDB) {
            if (!u.isEliminado()) {
                usuarios.add(u);
            }
        }
        return usuarios;
    }

    @Override
    public Usuario readByID(Long id){
        for (Usuario u : nombreBaseDB) {
            if (u.getId().equals(id) && !u.isEliminado()) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void update(Usuario entity){
        Usuario existente = readByID(entity.getId());
        if (existente != null) {
            existente.setNombre(entity.getNombre());
            existente.setApellido(entity.getApellido());
            existente.setMail(entity.getMail());
            existente.setCelular(entity.getCelular());
            existente.setContrasenia(entity.getContrasenia());
            existente.setRol(entity.getRol());
            System.out.println("Usuario actualizado");
        }
    }

    @Override
    public void delete(Long id){
        Usuario existetnte = readByID(id);
        if (existetnte != null) {
            existetnte.setEliminado(true);
            System.out.println("Usuario eliminado");
        }
    }
}
