package model;

import java.util.ArrayList;
import java.util.Objects;

public class Producto {
    private String descripcion;
    private int cantidad;
    private float precio;
    private TipoProducto tipoProducto;
    private Descuento descuento;
    private ArrayList<Reseña> reseñas;
    public Producto(String descripcion, int cantidad, float precio, TipoProducto tipoProducto) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.tipoProducto = tipoProducto;
        this.descuento = null;
        this.reseñas = new ArrayList<>();
    }

    public Producto(String descripcion,int cantidad,float precio, TipoProducto tipoProducto, Descuento descuento){
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.tipoProducto = tipoProducto;
        this.descuento = descuento;
        this.reseñas = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return cantidad == producto.cantidad && Float.compare(precio, producto.precio) == 0 && Objects.equals(descripcion, producto.descripcion) && tipoProducto == producto.tipoProducto && Objects.equals(descuento, producto.descuento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcion, cantidad, precio, tipoProducto, descuento);
    }

    @Override
    public String toString() {
        return "Producto" +
                "descripción:" + descripcion + '\'' +
                "cantidad:" + cantidad +
                "precio:" + precio +
                "tipoProducto:" + tipoProducto +
                "descuento:" + descuento +
                "reseñas:" + reseñas;
    }

}
