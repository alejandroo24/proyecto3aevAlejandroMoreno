package controller;

import DAO.PedidoDAO;
import DataBase.ConnectionBD;
import model.DetallesPedido;
import model.EstadoPedido;
import model.Pedido;

import java.util.HashSet;

public class PedidoController {
    private static String rutaArchivo = "pedidos.xml";
    private static PedidoController instancia;
    private HashSet<Pedido> listaPedidos = new HashSet<>();
    private static UsuarioActivoController usuarioActivo = UsuarioActivoController.getInstancia();
    private static PedidoDAO pedidoDAO = new PedidoDAO(ConnectionBD.getConnection());

    private PedidoController() {
    }

    public static PedidoController getInstancia() {
        if (instancia == null) {
            instancia = new PedidoController();
            instancia.setListaPedidos(new HashSet<>(pedidoDAO.obtenerTodos()) );
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
            pedidoDAO.insertar(pedido);
            return true;
        }
        return false;
    }

    public boolean eliminarPedido(Pedido pedido) {
        if (pedido != null && listaPedidos.contains(pedido)) {
            pedido.cancelarPedido();
            listaPedidos.remove(pedido);
            pedidoDAO.eliminar(pedido.getId());
            return true;
        }
        return false;
    }

    public boolean modificarDetallesPedido(Pedido pedido, DetallesPedido nuevosDetalles) {
        if (pedido != null && listaPedidos.contains(pedido)) {
            pedidoDAO.actualizar(pedido);
            return pedido.modificarPedido(nuevosDetalles);
        }
        return false;
    }

    public boolean modificarEstadoPedido(Pedido pedido, EstadoPedido nuevoEstado) {
        if (pedido != null && listaPedidos.contains(pedido)) {
            pedidoDAO.actualizarEstado(pedido.getId(), nuevoEstado);
            return pedido.modificarEstadoPedido(nuevoEstado);
        }
        return false;
    }

    public boolean confirmarPedido(Pedido pedido) {
        if (pedido != null && listaPedidos.contains(pedido)) {
            return pedido.modificarEstadoPedido(EstadoPedido.PROCESADO);
        }
        return false;
    }


}
