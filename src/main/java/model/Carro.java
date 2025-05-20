package model;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;
import java.util.Objects;

public class Carro {

    private int id;
    private int idCliente;
    private HashMap<Producto, Integer> productosCarro;
    private double precioTotal;


    public Carro() {
        this.productosCarro = new HashMap<>();
    }

    public HashMap<Producto, Integer> getProductosCarro() {
        return productosCarro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setProductosCarro(HashMap<Producto, Integer> productosCarro) {
        this.productosCarro = productosCarro;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Carro carro = (Carro) o;
        return Objects.equals(id, carro.id) && Objects.equals(productosCarro, carro.productosCarro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productosCarro);
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
