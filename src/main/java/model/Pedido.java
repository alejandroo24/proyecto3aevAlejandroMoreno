package model;

import java.time.LocalDate;
import java.util.Objects;

public class Pedido {
    private LocalDate fechaCreacion;
    private LocalDate fechaLimite;
    private LocalDate fechaEntrega;
    private float precioPedido;
    private EstadoPedido estadoPedido;
    private DetallesPedido detallesPedido;

    public Pedido(LocalDate fechaCreacion, EstadoPedido estadoPedido, DetallesPedido detallesPedido) {
        this.fechaCreacion = fechaCreacion;
        this.estadoPedido = estadoPedido;
        this.detallesPedido = detallesPedido;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public float getPrecioPedido() {
        return precioPedido;
    }

    public void setPrecioPedido(float precioPedido) {
        this.precioPedido = precioPedido;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public DetallesPedido getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(DetallesPedido detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Float.compare(precioPedido, pedido.precioPedido) == 0 && Objects.equals(fechaCreacion, pedido.fechaCreacion) && Objects.equals(fechaLimite, pedido.fechaLimite) && Objects.equals(fechaEntrega, pedido.fechaEntrega) && estadoPedido == pedido.estadoPedido && Objects.equals(detallesPedido, pedido.detallesPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaCreacion, fechaLimite, fechaEntrega, precioPedido, estadoPedido, detallesPedido);
    }

    @Override
    public String toString() {
        return "Pedido" +
                "fecha Creación:" + fechaCreacion +
                "fecha Limite:" + fechaLimite +
                "fecha Entrega:" + fechaEntrega +
                "precio Pedido:" + precioPedido +
                "estado del Pedido:" + estadoPedido +
                "detalles Pedido:" + detallesPedido ;
    }
}
