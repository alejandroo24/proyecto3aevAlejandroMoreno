package model;

import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;



public class DetallesPedido {
    private Pedido pedido;
    private Producto producto;
    private int cantidad;
    private float precio;
    private int descuento;

    public DetallesPedido() {
        this.producto = new Producto();
        precio=0;
    }

    public DetallesPedido(Pedido pedido, Producto producto) {
        this.pedido = pedido;
        this.producto = producto;
        this.precio = producto.getPrecio();
        this.cantidad = 1;
    }

    public DetallesPedido(Pedido pedido,Producto producto, int cantidad) {
        this.producto = producto;
        this.precio = producto.getPrecio();
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }
}




