package model;

import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;



public class DetallesPedido {

    private int id;
    private int idPedido;
    private Producto producto;
    private int cantidad;
    private float precioUnitario;

    public DetallesPedido() {
        this.producto = new Producto();
        this.precioUnitario = 0;
    }

    public DetallesPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.precioUnitario = producto.getPrecio();
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }


    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public float getPrecioTotal() {
        return precioUnitario * cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DetallesPedido that = (DetallesPedido) o;
        return cantidad == that.cantidad && Float.compare(precioUnitario, that.precioUnitario) == 0 && Objects.equals(id, that.id) && Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, producto, cantidad, precioUnitario);
    }

    @Override
    public String toString() {
        return "DetallesPedido{" +
                "productosPedido=" + producto +
                ", precioTotal=" + precioUnitario +
                '}';
    }
}




