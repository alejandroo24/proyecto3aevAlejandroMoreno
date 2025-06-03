package model;

import exceptions.DatoIncorrectoException;
import utils.Utilidades;
import java.util.ArrayList;
import java.util.Objects;

public class Producto {
    private int id;
    private String descripcion;
    private int stock;
    private float precio;
    private Categoria categoria;
    private Almacen almacen;


    public Producto() {

    }

    public Producto(String descripcion, Categoria categoria) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
    }

    public Producto(String descripcion, float precio, Categoria categoria) {
        if (precio > 0) {
            this.descripcion = descripcion;
            this.precio = precio;
            this.categoria = categoria;
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return id == producto.id && stock == producto.stock && Float.compare(precio, producto.precio) == 0 && Objects.equals(descripcion, producto.descripcion) && categoria == producto.categoria && Objects.equals(almacen, producto.almacen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descripcion, stock, precio, categoria, almacen);
    }
}


