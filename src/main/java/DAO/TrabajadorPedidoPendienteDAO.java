package DAO;

import DataBase.ConnectionBD;
import interfaces.InterfazDAO;
import model.EstadoPedido;
import model.Pedido;
import model.Trabajador;
import model.TrabajadorPedidoPendiente;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrabajadorPedidoPendienteDAO implements InterfazDAO<TrabajadorPedidoPendiente> {

    private static Connection con;

    public TrabajadorPedidoPendienteDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(TrabajadorPedidoPendiente objeto) {
        String sql = "INSERT INTO trabajadores_pedidos_pendientes (trabajador_id, pedido_id) VALUES (?, ?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getIdTrabajador());
            stmt.setInt(2, objeto.getIdPedido());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(TrabajadorPedidoPendiente objeto) {
        String sql = "UPDATE trabajadores_pedidos_pendientes SET trabajador_id = ?, pedido_id = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getIdTrabajador());
            stmt.setInt(2, objeto.getIdPedido());
            stmt.setInt(3, objeto.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void eliminar(int id) {
    String sql = "DELETE FROM trabajadores_pedidos_pendientes WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TrabajadorPedidoPendiente obtenerPorId(int id) {
        String sql = "SELECT * FROM trabajadores_pedidos_pendientes WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                TrabajadorPedidoPendiente trabajadorPedidoPendiente = new TrabajadorPedidoPendiente();
                trabajadorPedidoPendiente.setId(rs.getInt("id"));
                trabajadorPedidoPendiente.setIdTrabajador(rs.getInt("trabajador_id"));
                trabajadorPedidoPendiente.setIdPedido(rs.getInt("pedido_id"));
                return trabajadorPedidoPendiente;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TrabajadorPedidoPendiente> obtenerTodos() {
        String sql= "SELECT * FROM trabajadores_pedidos_pendientes";
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            List<TrabajadorPedidoPendiente> lista = new ArrayList<>();
            while (rs.next()) {
                TrabajadorPedidoPendiente trabajadorPedidoPendiente = new TrabajadorPedidoPendiente();
                trabajadorPedidoPendiente.setId(rs.getInt("id"));
                trabajadorPedidoPendiente.setIdTrabajador(rs.getInt("trabajador_id"));
                trabajadorPedidoPendiente.setIdPedido(rs.getInt("pedido_id"));
                lista.add(trabajadorPedidoPendiente);
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Pedido> obtenerPedidosTrabajador(int idTrabajador) {
        String sql = "SELECT pedido_id FROM trabajadores_pedidos_pendientes WHERE trabajador_id = ?";
        List<Pedido> listaPedidos = new ArrayList<>();
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idTrabajador);
            var rs = stmt.executeQuery();
            PedidoDAO pedidoDAO = new PedidoDAO(con);
            while (rs.next()) {
                int pedidoId = rs.getInt("pedido_id");
                Pedido pedido = pedidoDAO.obtenerPorId(pedidoId);
                if (pedido != null) {
                    listaPedidos.add(pedido);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPedidos;
    }

    public List<Pedido> obtenerPedidosTrabajadorEager(int idTrabajador) {
        String sql = "SELECT p.* FROM trabajadores_pedidos_pendientes tpp JOIN pedidos p ON tpp.pedido_id = p.id WHERE tpp.trabajador_id = ?";
        List<Pedido> listaPedidos = new ArrayList<>();
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idTrabajador);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                ClienteDAO clienteDAO = new ClienteDAO(con);
                pedido.setCliente(clienteDAO.obtenerPorId(rs.getInt("cliente_id")));
                pedido.setEstadoPedido(EstadoPedido.valueOf(rs.getString("estado_pedido")));
                java.sql.Date sqlDate = rs.getDate("fecha_creacion");
                LocalDate fechaCreacion = sqlDate != null ? sqlDate.toLocalDate() : null;
                pedido.setFechaCreacion(fechaCreacion);
                pedido.setFechaEntrega(rs.getDate("fecha_entrega") != null ? rs.getDate("fecha_entrega").toLocalDate() : null);
                listaPedidos.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPedidos;
    }
}
