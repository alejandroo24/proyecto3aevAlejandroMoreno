package controller;

import exceptions.EstadoPedidoInvalidException;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrabajadorController {
    private static TrabajadorController instancia;
    private Trabajador trabajadorActivo = (Trabajador) UsuarioActivoController.getInstancia().getUsuarioActivo();
    private AlmacenController almacenController = AlmacenController.getInstancia();
    private ProductoController productoController = ProductoController.getInstancia();
    private PedidoController pedidoController = PedidoController.getInstancia();
    private DescuentoController descuentoController = DescuentoController.getInstancia();





    public static TrabajadorController getInstancia() {
        if (instancia == null) {
            instancia = new TrabajadorController();
        }
        return instancia;
    }
    public synchronized void sincronizarConControladores(){
        ArrayList<Almacen> almacenesActualizados = new ArrayList<>();
        for (Almacen almacen : almacenController.getAlmacenes()){
            if (trabajadorActivo.getAlmacenesGestionados().contains(almacen)){
                almacenesActualizados.add(almacen);
            }
        }
        trabajadorActivo.setAlmacenesGestionados(almacenesActualizados);

        ArrayList<Pedido> solicitudesActualizadas = new ArrayList<>();
        for (Pedido pedido : pedidoController.getListaPedidos()){
            if (trabajadorActivo.getSolicitudesPendientes().contains(pedido)){
                solicitudesActualizadas.add(pedido);
            }
        }
        trabajadorActivo.setSolicitudesPendientes(solicitudesActualizadas);

        ArrayList<Pedido> pedidosCompletadosActualizados = new ArrayList<>();
        for (Pedido pedido : pedidoController.getListaPedidos()){
            if (trabajadorActivo.getDescuentosCreados().contains(pedido)){
                pedidosCompletadosActualizados.add(pedido);
            }
        }
        trabajadorActivo.setPedidosCompletados(pedidosCompletadosActualizados);

        ArrayList<Descuento> descuentosActualizados = new ArrayList<>();
        for (Descuento descuento : descuentoController.getDescuentos()){
            if (trabajadorActivo.getPedidosCompletados().contains(descuento)){
                descuentosActualizados.add(descuento);
            }
        }
        trabajadorActivo.setDescuentosCreados(descuentosActualizados);


    }


    public boolean addAlmacen(Almacen almacen) {
        boolean added = false;
        if (almacen != null && almacenController.getAlmacenes().contains(almacen) && !trabajadorActivo.getAlmacenesGestionados().contains(almacen)) {
            trabajadorActivo.getAlmacenesGestionados().add(almacen);
            added = true;
        }
        sincronizarConControladores();
        return added;
    }

    public boolean removeAlmacen(Almacen almacen) {
        boolean removed = false;
        if (trabajadorActivo.getAlmacenesGestionados().contains(almacen) && almacen !=null) {
                trabajadorActivo.getAlmacenesGestionados().remove(almacen);
            sincronizarConControladores();
            removed = true;
        }
        return removed;
    }

    public boolean crearDescuento(Descuento descuento) {
        boolean created = false;
        if (descuento !=null && !trabajadorActivo.getDescuentosCreados().contains(descuento) && !descuentoController.getDescuentos().contains(descuento)) {
            trabajadorActivo.getDescuentosCreados().add(descuento);
            descuentoController.añadeDescuento(descuento);
            created = true;
        }
        sincronizarConControladores();
        return created;
    }

    public boolean eliminarDescuento(Descuento descuento) {
        boolean removed = false;
        if (trabajadorActivo.getDescuentosCreados().contains(descuento)) {
            trabajadorActivo.getDescuentosCreados().remove(descuento);
            removed = true;
        }
        sincronizarConControladores();
        return removed;
    }


    public boolean crearProductoAlmacen(Almacen almacen, Producto producto) {
        boolean added = false;

        if (almacen == null || producto == null) {
            return false;
        }
        if (!trabajadorActivo.getAlmacenesGestionados().contains(almacen)){
            return false;
        }
        if (almacen.getProductos().containsKey(producto)){
            return false;
        }
        sincronizarConControladores();
        return almacen.addProducto(producto);
    }

    public boolean crearProductoAlmacen(Almacen almacen, Producto producto,int cantidad) {

        if (almacen == null || producto == null || cantidad <= 0) {
            return false;
        }
        if (!trabajadorActivo.getAlmacenesGestionados().contains(almacen)){
            return false;
        }
        if (almacen.getProductos().containsKey(producto)){
            return false;
        }
        sincronizarConControladores();
        return almacen.addProducto(producto,cantidad);
    }

    public boolean eliminarProductoAlmacen(Almacen almacen, Producto producto) {
        if (almacen == null || producto == null) {
            return false;
        }

        if (!trabajadorActivo.getAlmacenesGestionados().contains(almacen)){
            return false;
        }

        if (!almacen.containsProducto(producto)){
            return false;
        }
        sincronizarConControladores();
        return almacen.removeProducto(producto);
    }

    public boolean modificarProductoAlmacen(Almacen almacen, Producto producto,int cantidadAñadida) {
        if ( almacen == null || producto == null || cantidadAñadida <= 0) {
            return false;
        }

        if (!trabajadorActivo.getAlmacenesGestionados().contains(almacen)){
            return false;
        }
        if (!almacen.containsProducto(producto)){
            return false;
        }
        sincronizarConControladores();
        return almacen.addCantidad(producto,cantidadAñadida);
    }

    public boolean creaProducto(String descripcion, TallasProducto talla, ColorProducto color,int cantidad, float precio, TipoProducto tipoProducto) {
        Producto productoNuevo= new Producto(descripcion, talla, color, precio, tipoProducto);
        return productoController.agregarProducto(productoNuevo);
    }

    public boolean modificaProducto(Producto producto) {
        return productoController.modificarProducto(producto);
    }

    public boolean eliminaProducto(Producto producto) {
        return productoController.eliminarProducto(producto);
    }

    public boolean completarPedido(Pedido pedido) {
        boolean added = false;
        if (!trabajadorActivo.getPedidosCompletados().contains(pedido) && pedido.getEstadoPedido() == EstadoPedido.ENTREGADO) {
            trabajadorActivo.getPedidosCompletados().add(pedido);
            added = true;
        } else if (pedido.getEstadoPedido() != EstadoPedido.ENTREGADO) {
            System.out.println("El pedido no ha sido entregado, asegurate de modificar el estado antes de añadirlo a la lista");
        }
        sincronizarConControladores();
        return added;

    }

    public boolean creaPedido() {
        if (trabajadorActivo.getSolicitudesPendientes().isEmpty() || trabajadorActivo.getAlmacenesGestionados().isEmpty()) {
            return false; // No hay solicitudes o almacenes disponibles
        }

        for (Pedido solicitud : trabajadorActivo.getSolicitudesPendientes()) {
            if (solicitud.getEstadoPedido() == EstadoPedido.PENDIENTE) {
                List<DetallesPedido> detalles = solicitud.getDetallesPedido();
                boolean stockSuficiente = true;

                for (DetallesPedido detallesPedido : detalles) {
                    Producto productoPedido = detallesPedido.GetProducto();
                    int cantidadSolicitada = detallesPedido.getCantidad();
                    if (!compruebaStock(productoPedido, cantidadSolicitada)) {
                        stockSuficiente = false;
                        break; // No hay suficiente stock para este producto
                    }
                }

                if (stockSuficiente) {
                    solicitud.setEstadoPedido(EstadoPedido.PROCESADO);
                    pedidoController.confirmarPedido(solicitud);
                    trabajadorActivo.getPedidosCompletados().add(solicitud); // Añadir a la lista de pedidos completados
                } else {
                    System.out.println("No hay suficiente stock para procesar el pedido: " + solicitud);
                }
            }
        }

        trabajadorActivo.getSolicitudesPendientes().clear();
        sincronizarConControladores();// Limpiar solicitudes pendientes
        return true; // Pedidos procesados correctamente
    }

    public boolean eliminarPedido(Pedido pedido) {
        if (pedidoController.getListaPedidos().contains(pedido)){
            pedidoController.eliminarPedido(pedido);
        }
        return true;
    }

    public boolean modificarEstadoPedido(Pedido pedido, EstadoPedido estado)throws EstadoPedidoInvalidException {

        return pedidoController.modificarEstadoPedido(pedido, estado);
    }

    private boolean compruebaStock(Producto producto, int cantidadSolicitada) {
        for (Almacen almacen : trabajadorActivo.getAlmacenesGestionados()) {
            if (almacen.containsProducto(producto) && almacen.getProductos().get(producto) >= cantidadSolicitada) {
                almacen.removeCantidad(producto, cantidadSolicitada);
                sincronizarConControladores();
                return true; // Stock suficiente y reducido
            }
        }

        return false; // No hay suficiente stock en ningún almacén
    }

}
