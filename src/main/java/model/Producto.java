package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Objects;
@XmlRootElement(name = "producto")
@XmlAccessorType(XmlAccessType.FIELD)

public class Producto {
    @XmlElement
    private String descripcion;
    @XmlElement
    private int talla;
    @XmlElement
    private String color;
    @XmlElement
    private int cantidad;
    @XmlElement
    private float precio;
    @XmlElement
    private TipoProducto tipoProducto;
    @XmlElement
    private Descuento descuento;
    @XmlElement
    private ArrayList<Reseña> reseñas;

    public Producto(){

    }
    public Producto(String descripcion,int talla,String color, int cantidad, float precio, TipoProducto tipoProducto) {
        this.descripcion = descripcion;
        this.talla = talla;
        this.color = color;
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
