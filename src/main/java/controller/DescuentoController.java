package controller;
import dataAccess.XMLManager;
import model.Descuento;
import utils.Utilidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DescuentoController {
    private List<Descuento> descuentos;
    private final String archivoDescuentos = "descuentos.xml";
    public DescuentoController(){
        descuentos= new ArrayList<>();
    }

    public boolean creaDescuento(String descripcion,int porcentaje, LocalDate fechaCaducidad) {
        if (porcentaje < 0 || porcentaje > 100) {
            return false;
        }
        Descuento descuentoNuevo = new Descuento(descripcion, porcentaje, fechaCaducidad);

        for (Descuento descuento : descuentos) {
            if (descuento.equals(descuentoNuevo)) {
                Utilidades.muestraMensaje("El descuento ya existe");
                return false;
            }else if (descuentoNuevo.getFechaCaducidad().isBefore(LocalDate.now())) {
                Utilidades.muestraMensaje("La fecha de caducidad no puede ser anterior a la fecha actual");
                return false;
            } else if (descuentoNuevo.getFechaCaducidad().isEqual(LocalDate.now())) {
                Utilidades.muestraMensaje("La fecha de caducidad no puede ser igual a la fecha actual");
                return false;
            } else if (descuentoNuevo.getFechaCaducidad().isAfter(LocalDate.now().plusYears(1))) {
                Utilidades.muestraMensaje("La fecha de caducidad no puede ser superior a un año");
                return false;
            }else {
                descuentos.add(descuentoNuevo);
                Utilidades.muestraMensaje("Descuento creado correctamente");
                return true;
            }
        }
        return true;
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

    public void guardarDescuentos(){
        XMLManager.writeXML(descuentos,archivoDescuentos);
    }

    public void cargarDescuentos(){
        List<Descuento> descuentosCargados = XMLManager.readXML(descuentos,archivoDescuentos);
        if (descuentosCargados != null) {
            this.descuentos = descuentosCargados;
            eliminarDescuentosCaducados();
        } else {
            Utilidades.muestraMensaje("No se han podido cargar los descuentos");
        }
    }
}
