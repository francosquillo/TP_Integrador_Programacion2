package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import enums.Estado;

import enums.FormaPago;

public class Pedido extends Base{

    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago pago;
    private List<DetallePedido> detalles;

    public Pedido() {
    }

    public Pedido(Long id, boolean eliminado, LocalDateTime createdAt, LocalDate fecha, Estado estado, double total,
            FormaPago pago) {
        super(id, eliminado, createdAt);
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.pago = pago;
        this.detalles = new ArrayList<>();
    }

    public LocalDate getFecha() {return fecha;}

    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public Estado getEstado() {return estado;}

    public void setEstado(Estado estado) {this.estado = estado;}

    public double getTotal() {return total;}

    public void setTotal(double total) {this.total = total;}

    public FormaPago getPago() {return pago;}

    public void setPago(FormaPago pago) {this.pago = pago;}

}
