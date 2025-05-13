package model;

import utils.Utilidades;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Pedido {

    private int id;

    private Cliente cliente;

    private LocalDate fechaCreacion;

    private LocalDate fechaLimite;

    private LocalDate fechaEntrega;

    private float precioPedido;

    private EstadoPedido estadoPedido;

    private List<DetallesPedido> detallesPedido;

    public Pedido(){

    }

    public Pedido (Cliente cliente, EstadoPedido estadoPedido){
        this.cliente = cliente;
        this.fechaCreacion = LocalDate.now();
        this.fechaLimite = LocalDate.now().plusDays(3);
        this.fechaEntrega = null;
        this.precioPedido = 0;
        this.estadoPedido = estadoPedido;
        this.detallesPedido = null;
    }
    public Pedido(Cliente cliente, EstadoPedido estadoPedido, List<DetallesPedido> detallesPedido) {
        this.cliente = cliente;
        this.fechaCreacion = fechaCreacion;
        this.estadoPedido = estadoPedido;
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

    public List<DetallesPedido> getDetallesPedido() {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Float.compare(precioPedido, pedido.precioPedido) == 0 && Objects.equals(id, pedido.id) && Objects.equals(cliente, pedido.cliente) && Objects.equals(fechaCreacion, pedido.fechaCreacion) && Objects.equals(fechaLimite, pedido.fechaLimite) && Objects.equals(fechaEntrega, pedido.fechaEntrega) && estadoPedido == pedido.estadoPedido && Objects.equals(detallesPedido, pedido.detallesPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, fechaCreacion, fechaLimite, fechaEntrega, precioPedido, estadoPedido, detallesPedido);
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

    public boolean creaPedido(DetallesPedido detallesPedidoCrear) {
        if (detallesPedido != null) {
            this.detallesPedido.add(detallesPedidoCrear);
            this.fechaCreacion = LocalDate.now();
            this.fechaLimite = LocalDate.now().plusDays(3);
            this.fechaEntrega = null;
            this.estadoPedido = EstadoPedido.PENDIENTE;
            for (DetallesPedido detallesDelPedido : detallesPedido ) {
                this.precioPedido += detallesDelPedido.getPrecioUnitario() * detallesDelPedido.getCantidad();
            }
            return true;
        }
        return false;
    }

    public boolean cancelarPedido() {
        if (this.estadoPedido == EstadoPedido.PENDIENTE) {
            this.estadoPedido = EstadoPedido.CANCELADO;
            return true;
        }else{
            Utilidades.muestraMensaje("El pedido no se puede cancelar porque ya ha sido entregado o está en proceso de entrega.");
        }
        return false;
    }

    public boolean modificarPedido(DetallesPedido detallesPedidoModificar) {
       if (estadoPedido == EstadoPedido.PENDIENTE) {
            for (DetallesPedido detallesDelPedido : detallesPedido) {
                if (detallesDelPedido.equals(detallesPedidoModificar)) {
                    detallesDelPedido.setCantidad(detallesPedidoModificar.getCantidad());
                    detallesDelPedido.setPrecioUnitario(detallesPedidoModificar.getPrecioUnitario());
                    return true;
                }
            }
        }else{
           Utilidades.muestraMensaje("El pedido no se puede modificar porque ya ha sido entregado o está en proceso de entrega.");
       }
        return false;
    }

    public boolean modificarEstadoPedido(EstadoPedido estadoPedido) {
        if (this.estadoPedido == estadoPedido){
            Utilidades.muestraMensaje("El pedido ya se encuentra en el estado " + estadoPedido);
        }else {
            this.estadoPedido = estadoPedido;
            if (estadoPedido == EstadoPedido.ENTREGADO) {
                this.fechaEntrega = LocalDate.now();
            }
            return true;
        }
        return false;
    }

    public void restarPrecioEnvío(){
        this.precioPedido = this.precioPedido - 3;
    }
}



