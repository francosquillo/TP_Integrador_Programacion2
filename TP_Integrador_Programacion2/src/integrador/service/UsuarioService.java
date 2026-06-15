package service;

import java.util.List;
import entities.Usuario;
import dao.UsuarioDAO;

public class UsuarioService implements GenericService<Usuario> {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void create(Usuario entity) {

        if (entity.getMail() == null || !entity.getMail().contains("@")) {
            System.out.println("Error: El correo electronico no es valido.");
            return;
        }
        if (entity.getNombre() == null || entity.getNombre().trim().isEmpty()) {
            System.out.println("Error: El nombre es obligatorio.");
        return;
        }

        if (!entity.getNombre().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("Error: El apellido no puede contener números ni caracteres especiales.");
        return;
        }
        if (entity.getApellido() == null || entity.getApellido().trim().isEmpty()) {
            System.out.println("Error: El apellido es obligatorio.");
        return;
        }

        if (!entity.getApellido().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("Error: El apellido no puede contener números ni caracteres especiales.");
        return;
        }

        if (entity.getContrasenia() == null || entity.getContrasenia().length() < 4) {
            System.out.println("Error: La contrasenia debe tener al menos 4 caracteres.");
            return;
        }

        for (Usuario u : usuarioDAO.readAll()) {
            if (u.getMail().equalsIgnoreCase(entity.getMail())) {
                System.out.println("Error: Ya existe un usuario registrado con ese mail.");
                return;
            }
        }

        usuarioDAO.create(entity);
    }

    @Override
    public List<Usuario> readAll() {
        return usuarioDAO.readAll();
    }

    @Override
    public Usuario readByID(Long id) {
        return usuarioDAO.readByID(id);
    }

    @Override
    public void update(Usuario entity) {

        if (entity.getMail() == null || !entity.getMail().contains("@")) {
            System.out.println("Error: El mail no es valido.");
            return;
        }
        usuarioDAO.update(entity);
    }

    @Override
    public void delete(Long id) {
        usuarioDAO.delete(id);
    }
}