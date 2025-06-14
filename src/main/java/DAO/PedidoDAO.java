package DAO;

import interfaces.InterfazDAO;
import model.Cliente;
import model.EstadoPedido;
import model.Pedido;

import java.sql.Connection;
import java.sql.Date;
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
        String sql = "INSERT INTO pedido (id, fecha, Precio_total, estado, id_cliente) VALUES (?, ?, ?, ?, ?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getId());
            stmt.setDate(2,java.sql.Date.valueOf(pedido.getFechaCreacion()));
            stmt.setFloat(3, pedido.getPrecioTotal());
            stmt.setString(4, pedido.getEstadoPedido().toString());
            stmt.setInt(5, pedido.getCliente().getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Pedido pedido) {
    String sql = "UPDATE pedido SET fecha = ?, Precio_total = ?, estado = ?, id_cliente = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(pedido.getFechaCreacion()));
            stmt.setFloat(2, pedido.getPrecioTotal());
            stmt.setString(3, pedido.getEstadoPedido().toString());
            stmt.setInt(4, pedido.getCliente().getId());
            stmt.setInt(5, pedido.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Pedido pedido) {
    String sql = "DELETE FROM pedido WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pedido obtenerPorId(int id) {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                int pedidoId = rs.getInt("id");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                float precioTotal = rs.getFloat("Precio_total");
                EstadoPedido estado = EstadoPedido.valueOf(rs.getString("estado"));
                int clienteId = rs.getInt("id_cliente");

                ClienteDAO clienteDAO = new ClienteDAO(con);
                Cliente cliente = clienteDAO.obtenerPorId(clienteId);

                Pedido pedido = new Pedido();
                pedido.setId(pedidoId);
                pedido.setFechaCreacion(fecha);
                pedido.setPrecioTotal(precioTotal);
                pedido.setEstadoPedido(estado);
                pedido.setCliente(cliente);
                return pedido;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Pedido> obtenerTodos() {
        String sql = "SELECT * FROM pedido";
        List<Pedido> pedidos = new ArrayList<>();
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                int pedidoId = rs.getInt("id");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                float precioTotal = rs.getFloat("Precio_total");
                EstadoPedido estado = EstadoPedido.valueOf(rs.getString("estado"));
                int clienteId = rs.getInt("id_cliente");

                ClienteDAO clienteDAO = new ClienteDAO(con);
                Cliente cliente = clienteDAO.obtenerPorId(clienteId);

                Pedido pedido = new Pedido();
                pedido.setId(pedidoId);
                pedido.setFechaCreacion(fecha);
                pedido.setPrecioTotal(precioTotal);
                pedido.setEstadoPedido(estado);
                pedido.setCliente(cliente);

                pedidos.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidos;
    }
}
