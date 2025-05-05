package model;

import dataAccess.HashMapAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;

@XmlRootElement(name = "carro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Carro {
    @XmlJavaTypeAdapter(HashMapAdapter.class)
    private HashMap<Producto, Integer> productosCarro;

    public Carro() {
        this.productosCarro = new HashMap<>();
    }

    public HashMap<Producto, Integer> getProductosCarro() {
        return productosCarro;
    }

    public double precioTotal;

    public void setProductosCarro(HashMap<Producto, Integer> productosCarro) {
        this.productosCarro = productosCarro;
    }

    @Override
    public String toString() {
        return "Carro" +
                "productos en el Carro" + productosCarro;
    }
public void agregarProducto(Producto producto, int cantidad) {
        if (productosCarro.containsKey(producto)) {
            productosCarro.put(producto, productosCarro.get(producto) + cantidad);
        } else {
            productosCarro.put(producto, cantidad);
        }
    }

    public void eliminarProducto(Producto producto) {
        productosCarro.remove(producto);
    }

    public void vaciarCarro() {
        productosCarro.clear();
    }

    public int cantidadProductos() {
        int total = 0;
        for (int cantidad : productosCarro.values()) {
            total += cantidad;
        }
        return total;
    }

    public double getPrecioTotal() {
        int total = 0;
        for (HashMap.Entry<Producto, Integer> entry : productosCarro.entrySet()) {
            Producto producto = entry.getKey();
            double cantidad = entry.getValue();
            total += producto.getPrecio() * cantidad;
        }
        this.precioTotal = total;
        return precioTotal;
    }

    public void SetPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

}
