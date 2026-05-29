package entities;

import java.time.LocalDateTime;

public class DetallePedido extends Base{

    private int cantidad;
    private double subtotal;
    private Producto producto;

    public DetallePedido(){

    }

    public DetallePedido(Long id, boolean eliminado, LocalDateTime createdAt, int cantidad, double subtotal, Producto producto) {
        super(id, eliminado, createdAt);
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.producto = producto;
    }

    public int getCantidad() {return cantidad;}

    public void setCantidad(int cantidad) {this.cantidad = cantidad;}

    public double getSubtotal() {return subtotal;}

    public void setSubtotal(double subtotal) {this.subtotal = subtotal;}

    public Producto getProducto() {return producto;}

    public void setProducto(Producto producto) {this.producto = producto;}

}
