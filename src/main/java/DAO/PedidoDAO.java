package DAO;

import interfaces.InterfazDAO;
import model.EstadoPedido;
import model.Pedido;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements InterfazDAO<Pedido> {

    private static Connection con;

    public PedidoDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Pedido pedido) {
        String sql = "INSERT INTO pedidos (cliente_id, estado, fecha_creacion, fecha_limite) VALUES (?, ?, ?, ?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getCliente().getId());
            stmt.setString(2, pedido.getEstadoPedido().name());
            stmt.setDate(3, java.sql.Date.valueOf(pedido.getFechaCreacion()));
            stmt.setDate(4, java.sql.Date.valueOf(pedido.getFechaLimite()));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Pedido pedido) {
    String sql ="UPDATE pedidos SET cliente_id = ?, estado = ?, fecha_creacion = ?, fecha_limite = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getCliente().getId());
            stmt.setString(2, pedido.getEstadoPedido().name());
            stmt.setDate(3, java.sql.Date.valueOf(pedido.getFechaCreacion()));
            stmt.setDate(4, java.sql.Date.valueOf(pedido.getFechaLimite()));
            stmt.setInt(5, pedido.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: terminar el método obtenerPorId
    @Override
    public Pedido obtenerPorId(int id) {
        String sql = "SELECT * FROM pedidos WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                Pedido pedido= new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setEstadoPedido(EstadoPedido.valueOf(rs.getString("estado")));
                pedido.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                pedido.setFechaLimite(rs.getDate("fecha_limite").toLocalDate());
                ;
                return pedido;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Pedido> obtenerTodos() {
    String sql = "SELECT * FROM pedidos";
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            List<Pedido> pedidos = new ArrayList<>();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setEstadoPedido(EstadoPedido.valueOf(rs.getString("estado")));
                pedido.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                pedido.setFechaLimite(rs.getDate("fecha_limite").toLocalDate());
                pedidos.add(pedido);
            }
            return pedidos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizarEstado(int id, EstadoPedido nuevoEstado) {
        String sql = "UPDATE pedidos SET estado = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado.name());
            stmt.setInt(2, id);
            if (nuevoEstado == EstadoPedido.ENTREGADO) {
                obtenerPorId(id).setFechaEntrega(LocalDate.now());
            }
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
