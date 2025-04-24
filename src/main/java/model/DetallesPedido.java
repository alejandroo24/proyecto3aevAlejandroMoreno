package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class DetallesPedido {

    private Carro productosPedido;
    private HashSet<Producto> precioUnitario;

    public Carro getProductosPedido() {
        return productosPedido;
    }

    public void setProductosPedido(Carro productosPedido) {
        this.productosPedido = productosPedido;
    }

    public HashSet<Producto> getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(HashSet<Producto> precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DetallesPedido that = (DetallesPedido) o;
        return Objects.equals(productosPedido, that.productosPedido) && Objects.equals(precioUnitario, that.precioUnitario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productosPedido, precioUnitario);
    }

    @Override
    public String toString() {
        return "DetallesPedido" +
                "productos Pedido:" + productosPedido +
                "precio Unitario:" + precioUnitario;
    }
}
