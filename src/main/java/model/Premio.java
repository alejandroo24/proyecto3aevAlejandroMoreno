package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Premio {
    @XmlElement
    private String descripcion;
    @XmlElement
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Premio premio = (Premio) o;
        return puntosNecesarios == premio.puntosNecesarios && Objects.equals(descripcion, premio.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcion, puntosNecesarios);
    }

    @Override
    public String toString() {
        return "Premio:" +
                "descripción'" + descripcion + '\'' +
                "puntosNecesarios" + puntosNecesarios;
    }


}
