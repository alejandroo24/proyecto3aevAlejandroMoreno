package model;

import exceptions.DatoIncorrectoException;
import utils.Utilidades;

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
    private float precio;
    @XmlElement
    private TipoProducto tipoProducto;
    @XmlElement
    private Descuento descuento;
    @XmlElement
    private ArrayList<Reseña> reseñas;

    public Producto() {

    }

    public Producto(String descripcion, int talla, String color, float precio, TipoProducto tipoProducto) {

        try {
            if (talla > 0  || precio > 0) {
                this.descripcion = descripcion;
                this.talla = talla;
                this.color = color;
                this.precio = precio;
                this.tipoProducto = tipoProducto;
                this.descuento = null;
                this.reseñas = new ArrayList<>();
            }
        }catch (DatoIncorrectoException e) {
            System.out.println(e.getMessage()+ "Ni la talla, ni la cantidad ni el precio pueden ser menores o iguales a 0");
        }

    }

    public Producto(String descripcion,int talla, float precio, TipoProducto tipoProducto, Descuento descuento) {
        try {
            if (precio > 0 || talla > 0 || descuento.getPorcentaje() > 0) {
                this.descripcion = descripcion;
                this.precio = precio;
                this.talla = talla;
                this.tipoProducto = tipoProducto;
                this.descuento = descuento;
                this.reseñas = new ArrayList<>();
                aplicarDescuento();
            }

        }catch (DatoIncorrectoException e){
            Utilidades.muestraMensaje(e.getMessage()+ "Ni el precio, ni la talla ni el descuento pueden ser menores o iguales a 0");
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTalla() {
        return talla;
    }

    public void setTalla(int talla) {
       if(talla > 0) {
           this.talla = talla;
       }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        if (precio > 0) {
            this.precio = precio;
        }
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        if( descuento.getPorcentaje() > 0) {
            this.descuento = descuento;
        }
    }

    public ArrayList<Reseña> getReseñas() {
        return reseñas;
    }

    public void setReseñas(ArrayList<Reseña> reseñas) {
        this.reseñas = reseñas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return talla == producto.talla && Float.compare(precio, producto.precio) == 0 && Objects.equals(descripcion, producto.descripcion) && Objects.equals(color, producto.color) && tipoProducto == producto.tipoProducto && Objects.equals(descuento, producto.descuento) && Objects.equals(reseñas, producto.reseñas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcion, talla, color, precio, tipoProducto, descuento, reseñas);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "descripcion='" + descripcion + '\'' +
                ", talla=" + talla +
                ", color='" + color + '\'' +
                ", precio=" + precio +
                ", tipoProducto=" + tipoProducto +
                ", descuento=" + descuento +
                ", reseñas=" + reseñas +
                '}';
    }

    public boolean addReseña(Reseña reseña) {
        if (reseñas.contains(reseña)) {
            return false;
        } else {
            reseñas.add(reseña);
            return true;
        }
    }

    public double calificacionPromedio() {
        if (reseñas.isEmpty()) {
            return 0.0;
        }
        return reseñas.stream()
                .mapToInt(Reseña::getCalificacion)
                .average()
                .orElse(0.0);
    }

    public String listarReseñas() {
        StringBuilder sb = new StringBuilder("Reseñas del producto:\n");
        for (Reseña reseña : reseñas) {
            sb.append(reseña.toString()).append("\n");
        }
        return sb.toString();
    }

    public boolean aplicarDescuento(){
        if (descuento != null && descuento.getFechaCaducidad().isAfter(java.time.LocalDate.now())) {
            this.precio = this.precio - (this.precio * this.descuento.getPorcentaje() / 100);
            return true;
        } else {
            return false;
        }
    }

    public boolean actualizarPrecio(){
        boolean actualizado = false;
        if (descuento != null){
             actualizado = aplicarDescuento();
        }
        return actualizado;
    }

}
