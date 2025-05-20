package model;

import java.util.Objects;

public class Inventario {
        private int id;
        private Almacen almacen;
        private Producto producto;
        private int cantidad;


        public Inventario(){

        }

        public Inventario(Almacen almacen, Producto producto, int cantidad) {
            this.almacen = almacen;
            this.producto = producto;
            this.cantidad = cantidad;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Almacen getAlmacen() {
            return almacen;
        }

        public void setAlmacen(Almacen almacen) {
            this.almacen = almacen;
        }

        public Producto getProducto() {
            return producto;
        }

        public void setProducto(Producto producto) {
            this.producto = producto;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Inventario that = (Inventario) o;
        return id == that.id && cantidad == that.cantidad && Objects.equals(almacen, that.almacen) && Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, almacen, producto, cantidad);
    }
}

