package controller;
import DAO.DescuentoDAO;
import DataBase.ConnectionBD;
import com.sun.jdi.connect.spi.Connection;
import model.Descuento;
import utils.Utilidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DescuentoController {
    private List<Descuento> descuentos;
    private static DescuentoController instancia;
    private final String archivoDescuentos = "descuentos.xml";
    private static DescuentoDAO descuentoDAO= new DescuentoDAO(ConnectionBD.getConnection());


    public DescuentoController(){
        descuentos= new ArrayList<>();
    }

    public static DescuentoController getInstancia() {
        if (instancia == null) {
            instancia = new DescuentoController();
            instancia.setDescuentos(descuentoDAO.obtenerTodos());
        }
        return instancia;
    }

    public List<Descuento> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<Descuento> descuentos) {
        this.descuentos = descuentos;
    }

    public boolean creaDescuento(String descripcion, int porcentaje, LocalDate fechaCaducidad) {

        if (porcentaje < 0 || porcentaje > 100) {
            return false;
        }

        Descuento descuentoNuevo = new Descuento(descripcion, porcentaje, fechaCaducidad);

        if (descuentos.stream().anyMatch(descuento -> descuento.equals(descuentoNuevo))){
            Utilidades.muestraMensaje("El descuento ya existe");

        } else if (fechaCaducidad.isBefore(LocalDate.now())) {
            Utilidades.muestraMensaje("El descuento ha caducado");

        } else {
            Utilidades.muestraMensaje("Descuento creado correctamente");
            añadeDescuento(descuentoNuevo);
            descuentoDAO.insertar(descuentoNuevo);
            return true;
        }
        return false;
    }

    public boolean añadeDescuento (Descuento descuento){

        if (descuento == null ||descuentos.contains(descuento)) {
            return false;
        } else {
            descuentos.add(descuento);
            return true;
        }
    }
    public boolean eliminaDescuento(Descuento descuento) {
        if (descuentos.contains(descuento)) {
            descuentos.remove(descuento);
            descuentoDAO.eliminar(descuento.getId());
            Utilidades.muestraMensaje("Descuento eliminado correctamente");
            return true;
        } else {
            Utilidades.muestraMensaje("El descuento no existe");
            return false;
        }
    }

    public boolean modificaDescuento(Descuento descuento, String descripcion, int porcentaje, LocalDate fechaCaducidad) {
        if (descuentos.contains(descuento)) {
            boolean modificado = false;
        eliminaDescuento(descuento);
        creaDescuento(descripcion, porcentaje, fechaCaducidad);
        descuentoDAO.actualizar(descuento);
        modificado = true;
        } else {
            Utilidades.muestraMensaje("El descuento no existe");
            return false;
        }
        return true;
    }

    public List<Descuento> muestraDescuentos() {
        eliminarDescuentosCaducados();
        return descuentos;
    }

    public int cantidadDescuentos() {
        return descuentos.size();
    }

    public void eliminarDescuentosCaducados() {
        descuentos.removeIf(descuento -> descuento.getFechaCaducidad().isBefore(LocalDate.now()));
    }
}
