package model;

import dataAccess.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Reseña {
    @XmlElement
    private Producto producto;
    @XmlElement
    private Cliente cliente;
    @XmlElement
    private int calificacion;
    @XmlElement
    private String comentario;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha;

    public Reseña(){

    }

    public Reseña(Producto producto, Cliente cliente, int calificación, String comentario, LocalDate fecha) {
        this.producto = producto;
        this.cliente = cliente;
        this.calificacion = calificación;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificación(int calificación) {
        this.calificacion = calificación;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reseña reseña = (Reseña) o;
        return calificacion == reseña.calificacion && Objects.equals(producto, reseña.producto) && Objects.equals(cliente, reseña.cliente) && Objects.equals(comentario, reseña.comentario) && Objects.equals(fecha, reseña.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto, cliente, calificacion, comentario, fecha);
    }

    @Override
    public String toString() {
        return "Reseña" +
                "producto:" + producto +
                "cliente:" + cliente +
                "calificación:" + calificacion +
                "comentario:" + comentario + '\'' +
                "fecha:" + fecha ;
    }
}
