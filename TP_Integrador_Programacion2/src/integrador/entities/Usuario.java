package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import enums.Rol;

public class Usuario extends Base{

    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;
    private ArrayList<Pedido> pedidos;

    public Usuario(){
        
    }

    public Usuario(Long id, boolean eliminado, LocalDateTime createdAt, String nombre, String apellido, String mail,
            String celular, String contrasenia, Rol rol) {
        super(id, eliminado, createdAt);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.pedidos = new ArrayList<>();
    }

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() {return apellido;}

    public void setApellido(String apellido) {this.apellido = apellido;}

    public String getMail() {return mail;}

    public void setMail(String mail) {this.mail = mail;}

    public String getCelular() {return celular;}

    public void setCelular(String celular) {this.celular = celular;}

    public String getContrasenia() {return contrasenia;}

    public void setContrasenia(String contrasenia) {this.contrasenia = contrasenia;}

    public Rol getRol() {return rol;}

    public void setRol(Rol rol) {this.rol = rol;}

    public void addPedido(Pedido pedido) {this.pedidos.add(pedido);}

    public List<Pedido> getPedidos() {return pedidos;}
    
}
