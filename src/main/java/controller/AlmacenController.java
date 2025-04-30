package controller;

import dataAccess.XMLManager;
import model.Almacen;
import model.Producto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlmacenController {
    private final String rutaArchivo = "almacen.txt";
    private static AlmacenController instancia;
    private ArrayList<Almacen> almacenes;

    public static AlmacenController getInstancia() {
        if (instancia == null) {
            instancia = new AlmacenController(new ArrayList<>());
        }
        return instancia;
    }

    public AlmacenController(ArrayList<Almacen> almacenes) {
        this.almacenes = almacenes;
    }

    public boolean agregarAlmacen(Almacen almacen) {
        return almacenes.add(almacen);
    }

    public boolean eliminarAlmacen(Almacen almacen) {
        return almacenes.remove(almacen);
    }

    public Almacen buscarAlmacen(String nombre) {
        for (Almacen almacen : almacenes) {
            if (almacen.getNombre().equals(nombre)) {
                return almacen;
            }
        }
        return null;
    }

    public List<Almacen> getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(ArrayList<Almacen> almacenes) {
        this.almacenes = almacenes;
    }

    public boolean agregarProducto(Almacen almacen, Producto producto, int cantidad) {
        return almacen.addProducto(producto,cantidad);
    }

    public boolean eliminarProducto(Almacen almacen, Producto producto) {
        return almacen.removeProducto(producto);
    }

    public boolean modificarProducto(Almacen almacen, Producto producto, int cantidad) {
        if (almacen == null || producto == null || cantidad < 0) {
            return false;
        }
        boolean modificado = false;
        HashMap<Producto, Integer> productos = almacen.getProductos();
        if (productos.containsKey(producto)) {
            if (cantidad == 0) {
                return eliminarProducto(almacen, producto);
            } else {
                productos.put(producto, cantidad);
                modificado = true;
            }
        }
        return modificado;
    }

    public List<Producto> buscarProducto(Almacen almacen, String criterio) {
        List<Producto> productosEncontrados = new ArrayList<>();

        if (almacen == null || criterio == null || criterio.trim().isEmpty()) {
            return productosEncontrados;
        }

        String criterioModificado = criterio.trim().toLowerCase();

        for (Producto producto : almacen.getProductos().keySet()) {

            String descripcion = producto.getDescripcion().trim().toLowerCase();

            if (descripcion.contains(criterioModificado)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    public int cantidadProductos(Almacen almacen) {
        if (almacen == null) {
            return 0;
        }
        int total = 0;
        for (int cantidad : almacen.getProductos().values()) {
            total += cantidad;
        }
        return total;
    }

    public boolean guardarAlmacenes() {
        XMLManager.writeXML(almacenes, rutaArchivo);
        return true;
    }

    public boolean cargarAlmacenes() {
        almacenes = XMLManager.readXML(almacenes, rutaArchivo);
        return almacenes != null;
    }
}
