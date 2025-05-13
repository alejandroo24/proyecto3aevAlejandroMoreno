package controller;
import model.Descuento;
import utils.Utilidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DescuentoController {
    private List<Descuento> descuentos;
    private static DescuentoController instancia;
    private final String archivoDescuentos = "descuentos.xml";
    public DescuentoController(){
        descuentos= new ArrayList<>();
    }

    public static DescuentoController getInstancia() {
        if (instancia == null) {
            instancia = new DescuentoController();
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
            Utilidades.muestraMensaje("La fecha de caducidad no puede ser anterior a la fecha actual");

        } else {
            Utilidades.muestraMensaje("Descuento creado correctamente");
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
            Utilidades.muestraMensaje("Descuento eliminado correctamente");
            return true;
        } else {
            Utilidades.muestraMensaje("El descuento no existe");
            return false;
        }
    }

    public boolean modificaDescuento(Descuento descuento, String descripcion, int porcentaje, LocalDate fechaCaducidad) {
        if (descuentos.contains(descuento)) {
        eliminaDescuento(descuento);
        return creaDescuento(descripcion, porcentaje, fechaCaducidad);
        } else {
            Utilidades.muestraMensaje("El descuento no existe");
            return false;
        }
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
