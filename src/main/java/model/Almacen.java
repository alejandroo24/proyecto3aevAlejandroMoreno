package model;

import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

@XmlRootElement(name = "almacen")
@XmlAccessorType(XmlAccessType.FIELD)
public class Almacen {
    @XmlElement
    private String nombre;
    @XmlElement
    private HashMap<Producto,Integer> productos;
    @XmlElement
    private String localizacion;

    public Almacen(){

    }
    public Almacen(String localizacion, String nombre) {
        this.localizacion = localizacion;
        this.nombre = nombre;
        this.productos = new HashMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashMap<Producto,Integer> getProductos() {
        return productos;
    }

    public void setProductos(HashMap<Producto,Integer> productos) {
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

    public boolean addProducto(Producto producto) {
        boolean added = false;
        this.productos.put(producto,1);
        if (this.productos.containsKey(producto)) {
            added = true;
        }
        return added;
    }

    public boolean addProducto(Producto producto, int cantidad){
        boolean added = false;
        if (this.productos.containsKey(producto)) {
            this.productos.put(producto, this.productos.get(producto) + cantidad);
            added = true;
        } else {
            this.productos.put(producto, cantidad);
            added = true;
        }
        return added;
    }

    public boolean removeProducto(Producto producto) {
        boolean removed = false;
        if (this.productos.containsKey(producto)) {
            this.productos.remove(producto);
            removed = true;
        }
    return removed;
    }

    public boolean containsProducto(Producto producto) {
        boolean contains = false;
        if (this.productos.containsKey(producto)) {
            contains = true;
        }
        return contains;
    }

    public boolean addCantidad(Producto producto, int cantidad) {
        boolean added = false;
        if (this.productos.containsKey(producto)) {
            this.productos.put(producto, this.productos.get(producto) + cantidad);
            added = true;
        }
        return added;
    }


    public boolean removeCantidad(Producto producto, int cantidad) {
        boolean removed = false;
        if (this.productos.containsKey(producto)) {
            if (this.productos.get(producto) >= cantidad) {
                this.productos.put(producto, this.productos.get(producto) - cantidad);
                removed = true;
            }
        }
        return removed;
    }

    public int productosAlmacenados() {
        return this.productos.size();
    }
}
