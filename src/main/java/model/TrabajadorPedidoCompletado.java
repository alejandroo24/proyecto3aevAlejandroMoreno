package model;

public class TrabajadorPedidoCompletado {

    private int id;
    private int trabajadorId;
    private int pedidoId;

    public TrabajadorPedidoCompletado() {
    }

    public TrabajadorPedidoCompletado(int trabajadorId, int pedidoId) {
        this.trabajadorId = trabajadorId;
        this.pedidoId = pedidoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(int trabajadorId) {
        this.trabajadorId = trabajadorId;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }
}
