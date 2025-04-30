package model;

import controller.AlmacenController;
import controller.DescuentoController;
import controller.PedidoController;
import controller.ProductoController;
import exceptions.EstadoPedidoInvalidException;
import exceptions.TipoUsuarioException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Map;

@XmlRootElement(name = "trabajador")
@XmlAccessorType(XmlAccessType.FIELD)
public class Trabajador extends Usuario {
    @XmlElement
    private int salario;
    @XmlElement
    private ArrayList<Almacen> almacenesGestionados;
    @XmlElement
    private ArrayList<Descuento> descuentosCreados;
    @XmlElement
    private ArrayList<Pedido> pedidosCompletados;
    @XmlElement
    private ArrayList<Pedido> solicitudesPendientes = new ArrayList<>();

    AlmacenController almacenController = AlmacenController.getInstancia();
    ProductoController productoController = ProductoController.getInstancia();
    PedidoController pedidoController = PedidoController.getInstancia();
    DescuentoController descuentoController = DescuentoController.getInstancia();
    public Trabajador(){

    }

    public Trabajador(String nombre, String contraseña, String correo, String usuario,boolean isTrabajador) throws TipoUsuarioException {
            super(nombre, contraseña, correo, usuario, true);
            try {
                if (isTrabajador) {
                    almacenesGestionados = new ArrayList<>();
                    descuentosCreados = new ArrayList<>();
                    pedidosCompletados = new ArrayList<>();
                    this.salario = 0;
                    for (Pedido p : pedidoController.getListaPedidos()) {
                        if (p.getEstadoPedido() == EstadoPedido.PENDIENTE) {
                            this.solicitudesPendientes.add(p);

                        }
                    }
                }

            } catch (TipoUsuarioException e) {
            throw new TipoUsuarioException("El usuario no es un trabajador");
        }

    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public ArrayList<Almacen> getAlmacenesGestionados() {
        return almacenesGestionados;
    }

    public void setAlmacenesGestionados(ArrayList<Almacen> almacenesGestionados) {
        this.almacenesGestionados = almacenesGestionados;
    }

    public ArrayList<Descuento> getDescuentosCreados() {
        return descuentosCreados;
    }

    public void setDescuentosCreados(ArrayList<Descuento> descuentosCreados) {
        this.descuentosCreados = descuentosCreados;
    }

    public ArrayList<Pedido> getPedidosCompletados() {
        return pedidosCompletados;
    }

    public void setPedidosCompletados(ArrayList<Pedido> pedidosCompletados) {
        this.pedidosCompletados = pedidosCompletados;
    }

    @Override
    public String toString() {
        return super.toString() +
                "salario:" + salario +
                "almacenesGestionados: " + almacenesGestionados +
                "descuentosCreados: " + descuentosCreados +
                "pedidosCompletados: " + pedidosCompletados;
    }

    public void sincronizarConControladores(){
        ArrayList<Almacen> almacenesActualizados = new ArrayList<>();
        for (Almacen almacen : almacenController.getAlmacenes()){
            if (almacenesGestionados.contains(almacen)){
                almacenesActualizados.add(almacen);
            }
        }
        this.almacenesGestionados = almacenesActualizados;

        ArrayList<Pedido> solicitudesActualizadas = new ArrayList<>();
        for (Pedido pedido : pedidoController.getListaPedidos()){
            if (solicitudesPendientes.contains(pedido)){
                solicitudesActualizadas.add(pedido);
            }
        }
        this.solicitudesPendientes = solicitudesActualizadas;

        ArrayList<Pedido> pedidosCompletadosActualizados = new ArrayList<>();
        for (Pedido pedido : pedidoController.getListaPedidos()){
            if (pedidosCompletados.contains(pedido)){
                pedidosCompletadosActualizados.add(pedido);
            }
        }
        this.pedidosCompletados = pedidosCompletadosActualizados;

        ArrayList<Descuento> descuentosActualizados = new ArrayList<>();
        for (Descuento descuento : descuentoController.getDescuentos()){
            if (descuentosCreados.contains(descuento)){
                descuentosActualizados.add(descuento);
            }
        }
        this.descuentosCreados = descuentosActualizados;


    }

    public boolean addAlmacen(Almacen almacen) {
        boolean added = false;
        if (almacen != null && almacenController.getAlmacenes().contains(almacen) && !almacenesGestionados.contains(almacen)) {
            almacenesGestionados.add(almacen);
            added = true;
        }
        sincronizarConControladores();
        return added;
    }

    public boolean removeAlmacen(Almacen almacen) {
        boolean removed = false;
        if (almacenesGestionados.contains(almacen) && almacen !=null) {
           if (almacenesGestionados.contains(almacen)){
               almacenesGestionados.remove(almacen);
           }
            sincronizarConControladores();
            removed = true;
        }
        return removed;
    }

    public boolean crearDescuento(Descuento descuento) {
        boolean created = false;
        if (descuento !=null && !descuentosCreados.contains(descuento) && !descuentoController.getDescuentos().contains(descuento)) {
            descuentosCreados.add(descuento);
            descuentoController.añadeDescuento(descuento);
            created = true;
        }
        sincronizarConControladores();
        return created;
    }

    public boolean eliminarDescuento(Descuento descuento) {
        boolean removed = false;
        if (descuentosCreados.contains(descuento)) {
            descuentosCreados.remove(descuento);
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
        if (!almacenesGestionados.contains(almacen)){
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
        if (!almacenesGestionados.contains(almacen)){
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

        if (!almacenesGestionados.contains(almacen)){
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

        if (!almacenesGestionados.contains(almacen)){
            return false;
        }
        if (!almacen.containsProducto(producto)){
            return false;
        }
        sincronizarConControladores();
        return almacen.addCantidad(producto,cantidadAñadida);
    }

    public boolean creaProducto(String descripcion, int talla, String color,int cantidad, float precio, TipoProducto tipoProducto) {
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
        if (!pedidosCompletados.contains(pedido) && pedido.getEstadoPedido() == EstadoPedido.ENTREGADO) {
            pedidosCompletados.add(pedido);
            added = true;
        } else if (pedido.getEstadoPedido() != EstadoPedido.ENTREGADO) {
            System.out.println("El pedido no ha sido entregado, asegurate de modificar el estado antes de añadirlo a la lista");
        }
        sincronizarConControladores();
        return added;

    }

    public boolean creaPedido() {
        if (solicitudesPendientes.isEmpty() || almacenesGestionados.isEmpty()) {
            return false; // No hay solicitudes o almacenes disponibles
        }

        for (Pedido solicitud : solicitudesPendientes) {
            if (solicitud.getEstadoPedido() == EstadoPedido.PENDIENTE) {
                DetallesPedido detalles = solicitud.getDetallesPedido();
                boolean stockSuficiente = true;

                for (Map.Entry<Producto, Integer> entry : detalles.getProductosPedido().getProductosCarro().entrySet()) {
                    Producto producto = entry.getKey();
                    int cantidadSolicitada = entry.getValue();

                    if (!compruebaStock(producto, cantidadSolicitada)) {
                        stockSuficiente = false;
                        break; // No hay suficiente stock para este producto
                    }
                }

                if (stockSuficiente) {
                    solicitud.setEstadoPedido(EstadoPedido.PROCESADO);
                    pedidoController.confirmarPedido(solicitud);
                    pedidosCompletados.add(solicitud); // Añadir a la lista de pedidos completados
                    sincronizarConControladores();
                } else {
                    System.out.println("No hay suficiente stock para procesar el pedido: " + solicitud);
                }
            }
        }

        solicitudesPendientes.clear(); // Limpiar solicitudes pendientes
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
        for (Almacen almacen : almacenesGestionados) {
            if (almacen.containsProducto(producto) && almacen.getProductos().get(producto) >= cantidadSolicitada) {
                almacen.removeCantidad(producto, cantidadSolicitada);
                sincronizarConControladores();
                return true; // Stock suficiente y reducido
            }
        }

        return false; // No hay suficiente stock en ningún almacén
    }


}
