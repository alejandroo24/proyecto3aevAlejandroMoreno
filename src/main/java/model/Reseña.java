package model;

import java.time.LocalDate;
import java.util.Objects;

public class Reseña {
    private Producto producto;
    private Cliente cliente;
    private int calificación;
    private String comentario;
    private LocalDate fecha;

    public Reseña(Producto producto, Cliente cliente, int calificación, String comentario, LocalDate fecha) {
        this.producto = producto;
        this.cliente = cliente;
        this.calificación = calificación;
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

    public int getCalificación() {
        return calificación;
    }

    public void setCalificación(int calificación) {
        this.calificación = calificación;
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
        return calificación == reseña.calificación && Objects.equals(producto, reseña.producto) && Objects.equals(cliente, reseña.cliente) && Objects.equals(comentario, reseña.comentario) && Objects.equals(fecha, reseña.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto, cliente, calificación, comentario, fecha);
    }

    @Override
    public String toString() {
        return "Reseña" +
                "producto:" + producto +
                "cliente:" + cliente +
                "calificación:" + calificación +
                "comentario:" + comentario + '\'' +
                "fecha:" + fecha ;
    }
}
