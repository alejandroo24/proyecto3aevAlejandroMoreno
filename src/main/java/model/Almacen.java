package model;

import java.util.HashSet;
import java.util.Objects;

public class Almacen {
    private String nombre;
    private HashSet<Producto> productos;
    private String localizacion;

    public Almacen(String localizacion, String nombre) {
        this.localizacion = localizacion;
        this.nombre = nombre;
        this.productos = null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashSet<Producto> getProductos() {
        return productos;
    }

    public void setProductos(HashSet<Producto> productos) {
        this.productos = productos;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Almacen almacen = (Almacen) o;
        return Objects.equals(nombre, almacen.nombre) && Objects.equals(localizacion, almacen.localizacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, localizacion);
    }

    @Override
    public String toString() {
        return "Almacen:" +
                "nombre:" + nombre + '\'' +
                "productos:" + productos +
                "localizacion:" + localizacion;
    }
}
