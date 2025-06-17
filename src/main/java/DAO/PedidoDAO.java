package DAO;

import interfaces.InterfazDAO;
import model.Cliente;
import model.EstadoPedido;
import model.Pedido;
import model.Producto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String sql = "INSERT INTO pedido (fecha, PrecioTotal, estado, id_cliente) VALUES (?, ?, ?, ?)";
        try (var stmt = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, java.sql.Date.valueOf(pedido.getFechaCreacion()));
            stmt.setFloat(2, pedido.getPrecioTotal());
            stmt.setString(3, pedido.getEstadoPedido().toString());
            stmt.setInt(4, pedido.getCliente().getId());
            stmt.executeUpdate();
            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                pedido.setId(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Pedido pedido) {
    String sql = "UPDATE pedido SET fecha = ?, PrecioTotal = ?, estado = ?, id_cliente = ? WHERE id = ?";
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
                float precioTotal = rs.getFloat("PrecioTotal");
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
                float precioTotal = rs.getFloat("PrecioTotal");
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

    public List<Pedido> obtenerPedidosPorCliente(Cliente cliente) {
        String sql = "SELECT * FROM pedido WHERE id_cliente = ?";
        List<Pedido> pedidos = new ArrayList<>();
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            var rs = stmt.executeQuery();
            while (rs.next()) {
                int pedidoId = rs.getInt("id");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                float precioTotal = rs.getFloat("PrecioTotal");
                EstadoPedido estado = EstadoPedido.valueOf(rs.getString("estado"));
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

    public Pedido obtenerUltimoPedidoPorCliente(int idCliente) {
        String sql = "SELECT * FROM pedido WHERE id_cliente = ? ORDER BY id DESC LIMIT 1";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setFechaCreacion(rs.getDate("fecha").toLocalDate());
                pedido.setPrecioTotal(rs.getFloat("PrecioTotal"));
                pedido.setEstadoPedido(EstadoPedido.valueOf(rs.getString("estado")));
                ClienteDAO clienteDAO = new ClienteDAO(con);
                Cliente cliente = clienteDAO.obtenerPorId(idCliente);
                pedido.setCliente(cliente);
                return pedido;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Pedido obtenerPedidoPorRealizar(Cliente cliente) {
        String sql = "SELECT * FROM pedido WHERE id_cliente = ? AND estado = 'POR_REALIZAR' LIMIT 1";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            var rs = stmt.executeQuery();
            if (rs.next()) {
                int pedidoId = rs.getInt("id");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                float precioTotal = rs.getFloat("PrecioTotal");
                EstadoPedido estado = EstadoPedido.valueOf(rs.getString("estado"));
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

    public List<Pedido> obtenerPedidosPorEstado(EstadoPedido estado) {
        String sql = "SELECT * FROM pedido WHERE estado = ?";
        List<Pedido> pedidos = new ArrayList<>();
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setString(1, estado.toString());
            var rs = stmt.executeQuery();
            while (rs.next()) {
                int pedidoId = rs.getInt("id");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                float precioTotal = rs.getFloat("PrecioTotal");
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

    public void confirmarPedido(Pedido pedido){
        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)){
            stmt.setString(1, EstadoPedido.ENVIADO.toString());
            stmt.setInt(2, pedido.getId());
            stmt.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelarPedido(Pedido pedido){
        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)){
            stmt.setString(1, EstadoPedido.CANCELADO.toString());
            stmt.setInt(2, pedido.getId());
            stmt.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public List<Producto> obtenerProductosPorPedido(Pedido pedido) {
        String sql = "SELECT p.* FROM producto p " +
                     "JOIN detallePedido dp ON p.id = dp.producto_id " +
                     "WHERE dp.pedido_id = ?";
        List<Producto> productos = new ArrayList<>();
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getId());
            var rs = stmt.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setStock(rs.getInt("stock"));
                producto.setPrecio(rs.getFloat("precio"));
                // Asumiendo que la categoría y el almacén se manejan de manera similar
                // Aquí podrías agregar lógica para obtener la categoría y el almacén si es necesario
                productos.add(producto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }
}
