package model;

public class TrabajadorPedidoPendiente {
    private int id;
    private int idTrabajador;
    private int idPedido;


    public TrabajadorPedidoPendiente() {
    }

    public TrabajadorPedidoPendiente(int idTrabajador, int idPedido) {
        this.idTrabajador = idTrabajador;
        this.idPedido = idPedido;
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

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
}
