package controller;

import model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoController {
    private static String rutaArchivo = "productos.xml";
    private static ProductoController instancia;
    private List<Producto> listaProductos = new ArrayList<>();

    public static ProductoController getInstancia() {
        if (instancia == null) {
            instancia = new ProductoController();
        }
        return instancia;
    }
    public ProductoController() {
        this.listaProductos = new ArrayList<>();
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public boolean agregarProducto(Producto producto) {
        if (producto != null) {
            listaProductos.add(producto);
            return true;
        }
        return false;
    }

    public boolean eliminarProducto(Producto producto) {
        if (producto != null && listaProductos.contains(producto)) {
            listaProductos.remove(producto);
            return true;
        }
        return false;
    }

    public boolean modificarProducto(Producto producto) {
        if (producto != null && listaProductos.contains(producto)) {
            int index = listaProductos.indexOf(producto);
            listaProductos.set(index, producto);
            return true;
        }
        return false;
    }

    public List<Producto> buscarProducto(String criterio) {
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : listaProductos) {
            if (producto.getDescripcion().contains(criterio)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }
}
