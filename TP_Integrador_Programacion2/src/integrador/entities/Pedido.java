package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import enums.Estado;
import enums.FormaPago;

public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago pago;
    private List<DetallePedido> detalles;
    private Usuario usuario;

    public Pedido() {
    }

    public Pedido(Long id, boolean eliminado, LocalDateTime createdAt, LocalDate fecha, Estado estado, double total,
            FormaPago pago, Usuario usuario) {
        super(id, eliminado, createdAt);
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.pago = pago;
        this.detalles = new ArrayList<>();
        this.usuario = usuario;
    }

    public void addDetallePedido(DetallePedido detalle) {

        double subtotalCalculado = detalle.getCantidad() * detalle.getProducto().getPrecio();

        detalle.setSubtotal(subtotalCalculado);

        this.detalles.add(detalle);

        this.total = calcularTotal();
    }

    @Override
    public double calcularTotal() {

        double suma = 0;

        for (DetallePedido dp : detalles) {

            suma += dp.getSubtotal();
        }

        return suma;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public FormaPago getPago() {
        return pago;
    }

    public void setPago(FormaPago pago) {
        this.pago = pago;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public Usuario getUsuario() {return usuario;}

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

}