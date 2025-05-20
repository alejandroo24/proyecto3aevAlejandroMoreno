package model;

public class TrabajadorDescuento {
    private int id;
    private int TrabajadorId;
    private int DescuentoId;

    public TrabajadorDescuento() {
    }

    public TrabajadorDescuento(int trabajadorId, int descuentoId) {
        TrabajadorId = trabajadorId;
        DescuentoId = descuentoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrabajadorId() {
        return TrabajadorId;
    }

    public void setTrabajadorId(int trabajadorId) {
        TrabajadorId = trabajadorId;
    }

    public int getDescuentoId() {
        return DescuentoId;
    }

    public void setDescuentoId(int descuentoId) {
        DescuentoId = descuentoId;
    }
}
