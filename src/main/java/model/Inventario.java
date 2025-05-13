package model;

import java.util.Objects;

public class Inventario {
        private int id;
        private int idAlmacen;
        private int idProducto;
        private int cantidad;


        public Inventario(){

        }

        public Inventario(int idAlmacen, int idProducto, int cantidad) {
            this.idAlmacen = idAlmacen;
            this.idProducto = idProducto;
            this.cantidad = cantidad;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlmacen() {
            return idAlmacen;
        }

        public void setIdAlmacen(int idAlmacen) {
            this.idAlmacen = idAlmacen;
        }

        public int getIdProducto() {
            return idProducto;
        }

        public void setIdProducto(int idProducto) {
            this.idProducto = idProducto;
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
        return idAlmacen == that.idAlmacen && idProducto == that.idProducto && cantidad == that.cantidad;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAlmacen, idProducto, cantidad);
    }
}

