package model;
import java.util.Objects;

public class Almacen {
    private int id;
    private String nombre;
    private String localizacion;
    private String telefono;


    public Almacen() {
    }

    public Almacen(int id, String nombre, String localizacion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Almacen almacen = (Almacen) o;
        return id == almacen.id && Objects.equals(nombre, almacen.nombre) && Objects.equals(localizacion, almacen.localizacion) && Objects.equals(telefono, almacen.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, localizacion, telefono);
    }
}


