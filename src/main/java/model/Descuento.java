package model;

import java.time.LocalDate;
import java.util.Objects;


public class Descuento {
    private int descripcion;
    private int porcentaje;
    private LocalDate fechaCaducidad;

    public Descuento(int descripcion, int porcentaje, LocalDate fechaCaducidad) {
        this.descripcion = descripcion;
        this.porcentaje = porcentaje;
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(int descripcion) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Descuento descuento = (Descuento) o;
        return descripcion == descuento.descripcion && porcentaje == descuento.porcentaje && Objects.equals(fechaCaducidad, descuento.fechaCaducidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcion, porcentaje, fechaCaducidad);
    }

    @Override
    public String toString() {
        return "Descuento" +
                "descripcion:" + descripcion +
                "porcentaje" + porcentaje +
                "fechaCaducidad:" + fechaCaducidad;
    }
}
