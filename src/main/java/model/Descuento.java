package model;
import java.time.LocalDate;
import java.util.Objects;


public class Descuento {

    private int id;
    private String descripcion;
    private int porcentaje;
    private LocalDate fechaCaducidad;

    public Descuento() {
    }
    public Descuento(String descripcion, int porcentaje, LocalDate fechaCaducidad) {
        this.descripcion = descripcion;
        this.porcentaje = porcentaje;
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Descuento descuento = (Descuento) o;
        return porcentaje == descuento.porcentaje && Objects.equals(id, descuento.id) && Objects.equals(descripcion, descuento.descripcion) && Objects.equals(fechaCaducidad, descuento.fechaCaducidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descripcion, porcentaje, fechaCaducidad);
    }

    @Override
    public String toString() {
        return "Descuento" +
                "descripcion:" + descripcion +
                "porcentaje" + porcentaje +
                "fechaCaducidad:" + fechaCaducidad;
    }



}
