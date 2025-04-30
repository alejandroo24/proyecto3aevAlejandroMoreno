package model;

import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(Carro.class)
public class DetallesPedido {
    @XmlElement
    private Carro productosPedido;
    @XmlElement
    private int precioTotal;

    public DetallesPedido() {
        this.productosPedido = new Carro();
        this.precioTotal = 0;
    }
    public DetallesPedido(Carro carroConProductos) {
        this.productosPedido = carroConProductos;
        this.precioTotal = carroConProductos.getPrecioTotal();
    }

    public Carro getProductosPedido() {
        return productosPedido;
    }

    public void setProductosPedido(Carro productosPedido) {
        this.productosPedido = productosPedido;
    }

    public int getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(int precioTotal) {
        this.precioTotal = precioTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DetallesPedido that = (DetallesPedido) o;
        return precioTotal == that.precioTotal && Objects.equals(productosPedido, that.productosPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productosPedido, precioTotal);
    }

    @Override
    public String toString() {
        return "DetallesPedido{" +
                "productosPedido=" + productosPedido +
                ", precioTotal=" + precioTotal +
                '}';
    }
}




