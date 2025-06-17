package DAO;

import interfaces.InterfazDAO;
import model.DetallesPedido;
import model.Pedido;
import model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetallesPedidoDAO implements InterfazDAO<DetallesPedido> {

    private static Connection con;

    public DetallesPedidoDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(DetallesPedido detallePedido) {
        int detallesPedidoAñadidos = 0;
            String sql = "INSERT INTO detallePedido (pedido_id, producto_id, precio,cantidad) VALUES (?, ?, ?,?)";
            try (var stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, detallePedido.getPedido().getId());
                stmt.setInt(2, detallePedido.getProducto().getId());
                stmt.setFloat(3, detallePedido.getPrecio());
                stmt.setInt(4,detallePedido.getCantidad());
                stmt.executeUpdate();
                detallesPedidoAñadidos++;
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void actualizar(DetallesPedido detallePedido) {
        String sql = "UPDATE detallePedido SET cantidad = ? WHERE pedido_id = ? AND producto_id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, detallePedido.getCantidad());
            stmt.setInt(2, detallePedido.getPedido().getId());
            stmt.setInt(3, detallePedido.getProducto().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(DetallesPedido detallePedido) {
    String sql = "DELETE FROM detallePedido WHERE producto_id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, detallePedido.getProducto().getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DetallesPedido obtenerPorId(int id) {
        String sql = "SELECT * FROM detallePedido WHERE pedido_id = ? AND producto_id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                DetallesPedido detallePedido = new DetallesPedido();
                PedidoDAO pedidoDAO = new PedidoDAO(con);
                detallePedido.setPedido(pedidoDAO.obtenerPorId(rs.getInt("pedido_id")));
                int productoId = rs.getInt("producto_id");
                detallePedido.setCantidad(rs.getInt("cantidad"));
                detallePedido.setPrecio(rs.getFloat("precio_unitario"));
                ProductoDAO productosDAO = new ProductoDAO(con);
                Producto producto = productosDAO.obtenerPorId(productoId);
                detallePedido.setProducto(producto);
                return detallePedido;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DetallesPedido> obtenerTodos() {
        String sql = "SELECT * FROM detallePedido";
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            List<DetallesPedido> detallesPedidos = new ArrayList<>();
            while (rs.next()) {
                DetallesPedido detallePedido = new DetallesPedido();

                Producto producto = new Producto();
                producto = new ProductoDAO(con).obtenerPorId(rs.getInt("producto_id"));
                detallePedido.setProducto(producto);

                Pedido pedido = new Pedido();
                pedido = new PedidoDAO(con).obtenerPorId(rs.getInt("pedido_id"));
                detallePedido.setPedido(pedido);
                detallePedido.setPrecio(rs.getFloat("precio"));
                detallePedido.setCantidad(rs.getInt("cantidad"));
            }
            return detallesPedidos;
        } catch (Exception e) {
            e.printStackTrace();
    }
        return null;
    }

    public List<DetallesPedido> obtenerPorPedido(Pedido pedido) {
        String sql = "SELECT * FROM detallePedido WHERE pedido_id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getId());
            var rs = stmt.executeQuery();
            List<DetallesPedido> detallesPedidos = new ArrayList<>();
            while (rs.next()) {
                DetallesPedido detallePedido = new DetallesPedido();
                PedidoDAO pedidoDAO = new PedidoDAO(con);
                detallePedido.setPedido(pedidoDAO.obtenerPorId(rs.getInt("pedido_id")));
                int productoId = rs.getInt("producto_id");
                detallePedido.setCantidad(rs.getInt("cantidad"));
                detallePedido.setPrecio(rs.getFloat("precio"));
                ProductoDAO productosDAO = new ProductoDAO(con);
                Producto producto = productosDAO.obtenerPorId(productoId);
                detallePedido.setProducto(producto);
                detallesPedidos.add(detallePedido);
            }
            return detallesPedidos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void eliminarPorPedido(Pedido pedido) {
        String sql = "DELETE FROM detallePedido WHERE pedido_id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float obtenerTotalPorPedido(Pedido pedido) {
        float total = 0f;
        String sql = "SELECT SUM(precio * cantidad) FROM detallePedido WHERE pedido_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getFloat(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}



