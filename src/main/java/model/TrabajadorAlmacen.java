package model;

public class TrabajadorAlmacen {

    private int id;
    private int idTrabajador;
    private int idAlmacen;


    public TrabajadorAlmacen() {
    }

    public TrabajadorAlmacen(int idTrabajador, int idAlmacen) {
        this.idTrabajador = idTrabajador;
        this.idAlmacen = idAlmacen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }
}
