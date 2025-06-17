package model;

import utils.Utilidades;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pedido {

    private int id;

    private Cliente cliente;

    private LocalDate fechaCreacion;

    private float precioTotal;

    private EstadoPedido estadoPedido;

    private List<DetallesPedido> detallesPedido;

    public Pedido() {

    }

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.fechaCreacion = LocalDate.now();
        this.precioTotal = 0;
        this.estadoPedido = estadoPedido.PENDIENTE;
        this.detallesPedido = null;
    }

    public Pedido(Cliente cliente, List<DetallesPedido> detallesPedido) {
        this.cliente = cliente;
        this.fechaCreacion = LocalDate.now();
        this.estadoPedido = estadoPedido.PENDIENTE;
        this.detallesPedido = detallesPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public List<DetallesPedido> getDetallesPedido() {
        if (detallesPedido == null) {
        detallesPedido = new ArrayList<>();
        }
        return detallesPedido;
    }

    public void setDetallesPedido(List<DetallesPedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrecioTotal() {
            float total = 0f;
            if (detallesPedido != null) {
                for (DetallesPedido detalle : detallesPedido) {
                    total += detalle.getPrecio() * detalle.getCantidad();
                }
            }
            return total;

    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return id == pedido.id && Float.compare(precioTotal, pedido.precioTotal) == 0 && Objects.equals(cliente, pedido.cliente) && Objects.equals(fechaCreacion, pedido.fechaCreacion) && estadoPedido == pedido.estadoPedido && Objects.equals(detallesPedido, pedido.detallesPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, fechaCreacion, precioTotal, estadoPedido, detallesPedido);
    }
}



