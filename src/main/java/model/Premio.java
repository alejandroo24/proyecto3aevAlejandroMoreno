package model;

import utils.Utilidades;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;


public class Premio {

    private int id;

    private String descripcion;

    private int puntosNecesarios;

    public Premio(){

    }

    public Premio(String descripcion, int puntosNecesarios) {
        this.descripcion = descripcion;
        this.puntosNecesarios = puntosNecesarios;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPuntosNecesarios() {
        return puntosNecesarios;
    }

    public void setPuntosNecesarios(int puntosNecesarios) {
        this.puntosNecesarios = puntosNecesarios;
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
        Premio premio = (Premio) o;
        return puntosNecesarios == premio.puntosNecesarios && Objects.equals(id, premio.id) && Objects.equals(descripcion, premio.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descripcion, puntosNecesarios);
    }

    @Override
    public String toString() {
        return "Premio:" +
                "descripción'" + descripcion + '\'' +
                "puntosNecesarios" + puntosNecesarios;
    }

}
