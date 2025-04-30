package controller;

import dataAccess.XMLManager;
import model.DetallesPedido;
import model.EstadoPedido;
import model.Pedido;

import java.util.HashSet;

public class PedidoController {
    private static String rutaArchivo = "pedidos.xml";
    private static PedidoController instancia;
    private HashSet<Pedido> listaPedidos;

    private PedidoController() {
        // Constructor privado para evitar instanciación externa
    }

    public static PedidoController getInstance() {
        if (instancia == null) {
            instancia = new PedidoController();
        }
        return instancia;
    }
    public HashSet<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(HashSet<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    public boolean agregarPedido(Pedido pedido) {
        if (pedido != null) {
            listaPedidos.add(pedido);
            return true;
        }
        return false;
    }

    public boolean eliminarPedido(Pedido pedido) {
        if (pedido != null && listaPedidos.contains(pedido)) {
            pedido.cancelarPedido();
            listaPedidos.remove(pedido);
            return true;
        }
        return false;
    }

    public boolean modificarDetallesPedido(Pedido pedido, DetallesPedido nuevosDetalles) {
        if (pedido != null && listaPedidos.contains(pedido)) {
            return pedido.modificarPedido(nuevosDetalles);
        }
        return false;
    }

    public boolean modificarEstadoPedido(Pedido pedido, EstadoPedido nuevoEstado) {
        if (pedido != null && listaPedidos.contains(pedido)) {
            return pedido.modificarEstadoPedido(nuevoEstado);
        }
        return false;
    }

    public boolean guardarPedidos() {
        XMLManager.writeXML(listaPedidos, rutaArchivo);
        return true;
    }

    public boolean cargarPedidos() {
        HashSet<Pedido> pedidosCargados = XMLManager.readXML(listaPedidos, rutaArchivo);
        if (pedidosCargados != null) {
            this.listaPedidos = pedidosCargados;
            return true;
        }
        return false;
    }

}
